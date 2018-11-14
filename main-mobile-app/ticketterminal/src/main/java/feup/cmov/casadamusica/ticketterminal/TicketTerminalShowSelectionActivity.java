package feup.cmov.casadamusica.ticketterminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.services.ShowServices;
import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Utils;

public class TicketTerminalShowSelectionActivity extends AppCompatActivity {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list_terminal);
        setSupportActionBar(findViewById(R.id.toolbar));
        Button btn = findViewById(R.id.ticket_terminal_confirm);
        ListView listView = findViewById(R.id.show_list);

        adapter = new ShowListAdapter(this, new ArrayList<>(), R.layout.show_select_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();

        ShowServices.GetShows(this::parseShows,
            (failure) -> {
                Toast.makeText(getApplicationContext(), "Connection Problem", Toast.LENGTH_LONG).show();
            });

        btn.setOnClickListener((v)-> {
            HashSet<Integer> acceptedShows = getAcceptedShows();
            Intent intent = new Intent(this, TicketTerminalActivity.class);
            Archive.setAcceptedShows(acceptedShows);
            startActivity(intent);
        });
    }

    private void parseShows(JSONObject obj){
        try {
            ArrayList<Show> shows = new ArrayList<>();
            JSONArray array = obj.getJSONArray("shows");
            for(int i = 0; i < array.length(); i++){
                JSONObject show = array.getJSONObject(i);
                Show s = new Show(show);
                shows.add(s);
            }
            adapter.clear();
            adapter.addAll(shows);
        } catch (Exception e) {

        }
    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuItem itemLogout = menu.add(0, 0, Menu.NONE, "Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 0){
            Archive.setToken(null);
            Intent intent;
            intent = new Intent(this, TicketFirstTerminalActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private HashSet<Integer> getAcceptedShows(){
        HashSet<Integer> acceptedShows = new HashSet<>();
        for(int i = 0; i < adapter.getCount(); i++){
            Show show = (Show) adapter.getItem(i);

            if(show.isSelected()){
                acceptedShows.add(show.getId());
            }
        }
        return acceptedShows;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode + " " + data);
        if(result != null) {
            verifyDataTickets(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifyDataTickets(String info){
        try {
            JSONObject object = new JSONObject(info);

            TerminalServices.ValidateTicket(object,
                    this::parseTickets,
                    this::handleError);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {

    }

    private void parseTickets(JSONObject obj) {
        try {
            Intent intent = Utils.OpenTicketActivity(obj);
            startActivity(intent);
        } catch (Exception e) {

        }
    }
}
