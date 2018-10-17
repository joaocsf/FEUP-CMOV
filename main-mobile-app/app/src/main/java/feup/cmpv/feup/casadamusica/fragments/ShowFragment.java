package feup.cmpv.feup.casadamusica.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ShowFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Show> adapter;

    public static Fragment getInstance(String value) {
        Fragment fragment = new ShowFragment();
        Bundle b = new Bundle();
        b.putString("value", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Show> showList = new ArrayList<>();
        Show show = new Show("Show Name", "2010/01/03", 27.30f);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        showList.add(show);
        adapter = new ShowListAdapter(view.getContext(), showList);

        listView.setAdapter(adapter);
    }
}
