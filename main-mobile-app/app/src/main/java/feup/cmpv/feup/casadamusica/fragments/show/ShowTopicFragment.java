package feup.cmpv.feup.casadamusica.fragments.show;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.fragments.tickets.BuyTicketsDialogFragment;
import feup.cmpv.feup.casadamusica.services.ShowServices;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.MainBody;

public class ShowTopicFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<Show> adapter;

    public static Fragment getInstance(boolean newest) {
        Fragment fragment = new ShowTopicFragment();
        Bundle b = new Bundle();
        b.putBoolean("newest", newest);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void ParseShows(JSONObject shows){

        try {
            JSONArray array = shows.getJSONArray("shows");
            Archive.deleteAllShows();
            Archive.addShows(array);

            List<Show> showSet = null;
            if(getArguments().getBoolean("newest"))
                showSet = Archive.getAllShows();
            else
                showSet = Archive.getAllPopularShows();

            adapter.clear();
            adapter.addAll(showSet);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateShows(boolean force){

        if(force) {
            fetchShows();
            return;
        }

        adapter.clear();
        List<Show> showsSet = null;
        if(getArguments().getBoolean("newest")) {
            showsSet = Archive.getAllShows();
        }else {
            showsSet = Archive.getAllPopularShows();
        }

        adapter.addAll(showsSet);
    }

    private void RequestError(VolleyError error){
        adapter.clear();
        List<Show> showsSet = null;
        if(getArguments().getBoolean("newest")) {
            showsSet = Archive.getAllShows();
        }else {
            showsSet = Archive.getAllPopularShows();
        }

        adapter.addAll(showsSet);

    }



    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Show> showList = new ArrayList<>();
        adapter = new ShowListAdapter(view.getContext(), showList, R.layout.show_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();
        listView.setOnItemClickListener(this);

        fetchShows();
    }

    private void fetchShows(){
        ShowServices.GetShows(
                this::ParseShows,
                this::RequestError);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuyTicketsDialogFragment buyTicketsDialogFragment = (BuyTicketsDialogFragment) BuyTicketsDialogFragment.getInstance(adapter.getItem(position));
        buyTicketsDialogFragment.show(((MainBody)Objects.requireNonNull(getContext())).getSupportFragmentManager(), "Buy Ticket");
    }
}
