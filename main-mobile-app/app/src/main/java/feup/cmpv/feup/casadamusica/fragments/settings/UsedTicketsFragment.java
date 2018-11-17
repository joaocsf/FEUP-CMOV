package feup.cmpv.feup.casadamusica.fragments.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class UsedTicketsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ShowTickets> adapter;

    public static Fragment getInstance() {
        return new UsedTicketsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.ticket_list_view);

        ArrayList<ShowTickets> ticketList = new ArrayList<>();

        adapter = new TicketListAdapter(view.getContext(), ticketList, R.layout.ticket_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();
        fetchTickets();
    }

    public void fetchTickets() {
        Set<ShowTickets> usedShowTickets = Archive.getAllUsedShowTickets();
        adapter.clear();
        adapter.addAll(usedShowTickets);
    }
}
