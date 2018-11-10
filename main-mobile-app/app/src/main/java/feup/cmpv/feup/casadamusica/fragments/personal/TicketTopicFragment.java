package feup.cmpv.feup.casadamusica.fragments.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.services.TicketServices;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.structures.Ticket;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class TicketTopicFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Ticket> adapter;

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

    private void ParseTickets(JSONObject shows){
        try {
            JSONArray array = shows.getJSONArray("shows");
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                Ticket newTicket = new Ticket(
                        obj.getString("uuid"),
                        false,
                        25,
                        new Show(obj.getString("show_id"),
                                null,
                                obj.getString("show_date"),
                                -1,
                                -1,
                                obj.getInt("show_duration")
                        )
                );

                adapter.add(newTicket);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RequestError(VolleyError error){

    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.ticket_list_view);

        ArrayList<Ticket> ticketList = new ArrayList<>();
        adapter = new TicketListAdapter(view.getContext(), ticketList);
        listView.setAdapter(adapter);
        listView.computeScroll();

        TicketServices.GetTickets(
                Archive.getUuid(),
                this::ParseTickets,
                this::RequestError);

    }
}
