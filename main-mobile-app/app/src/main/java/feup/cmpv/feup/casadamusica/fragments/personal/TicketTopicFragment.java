package feup.cmpv.feup.casadamusica.fragments.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.services.TicketServices;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.structures.Ticket;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.MainBody;

public class TicketTopicFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<ShowTickets> adapter;

    public static Fragment getInstance(){
        return new TicketTopicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void ParseTickets(JSONObject tickets){

        try {
            JSONArray array = tickets.getJSONArray("tickets");
            Archive.deleteAllTickets();
            Archive.addTickets(array);

            Set<ShowTickets> showTickets = Archive.getShowTickets();
            adapter.clear();
            adapter.addAll(showTickets);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RequestError(VolleyError error){

    }

    public void updateTickets(Boolean force){

        if(force){
            fetchTickets();
            listView.smoothScrollToPosition(0);
            return;
        }

        adapter.clear();
        Set<ShowTickets> showTickets = Archive.getShowTickets();

        adapter.addAll(showTickets);
    }

    private void fetchTickets(){
        TicketServices.GetAllTickets(
                this::ParseTickets,
                this::RequestError);
    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.ticket_list_view);

        ArrayList<ShowTickets> ticketList = new ArrayList<>();
        adapter = new TicketListAdapter(view.getContext(), ticketList, R.layout.ticket_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();
        listView.setOnItemClickListener(this);

        fetchTickets();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ValidateTicketDialogFragment validateTicketDialogFragment = (ValidateTicketDialogFragment) ValidateTicketDialogFragment.getInstance(adapter.getItem(position));
        validateTicketDialogFragment.show(getFragmentManager(), "Validate Ticket");
        validateTicketDialogFragment.setTargetFragment(this, 25);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateTickets(false);
    }
}
