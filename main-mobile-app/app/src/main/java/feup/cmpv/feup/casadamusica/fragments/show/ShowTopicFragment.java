package feup.cmpv.feup.casadamusica.fragments.show;

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
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.services.ShowServices;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ShowTopicFragment extends Fragment {

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
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                Show newShow = new Show(
                        obj.getString("name"),
                        obj.getString("date"),
                        (float)obj.getDouble("price"),
                        obj.getInt("atendees")
                );
                adapter.add(newShow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RequestError(VolleyError error){

    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Show> showList = new ArrayList<>();
        adapter = new ShowListAdapter(view.getContext(), showList);
        listView.setAdapter(adapter);
        listView.computeScroll();

        if(getArguments().getBoolean("newest")) {
            ShowServices.GetShows(
                    this::ParseShows,
                    this::RequestError);
        }else {
            ShowServices.GetPopularShows(
                this::ParseShows,
                this::RequestError);
        }

    }
}
