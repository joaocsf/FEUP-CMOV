package feup.cmov.casadamusica.ticketterminal;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Utils;
import feup.cmpv.feup.casadamusica.view.TerminalLoginActivity;

public class TicketTerminalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_terminal);
        setSupportActionBar(findViewById(R.id.toolbar));

        Button btn = findViewById(R.id.ticket_terminal_read_qr);
        btn.setOnClickListener((v)-> {
            IntentIntegrator intentIntegrator =  new IntentIntegrator(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setPrompt("Read QR_code");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.initiateScan();
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode + " " + data);
        if(result != null && resultCode==RESULT_OK) {
            verifyDataTickets(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifyDataTickets(String info){
        try {
            JSONObject object = new JSONObject(info);

            if(!Archive.checkTicketValidation(object)){
                runOnUiThread(()->
                Toast.makeText(getApplicationContext(), "Invalid Show", Toast.LENGTH_LONG).show());
                return;
            }

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
