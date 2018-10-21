package feup.cmpv.feup.casadamusica.fragments.show;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ShowFragment extends Fragment {

    private ArrayAdapter<Show> adapter;

    public static Fragment getInstance() {
        Fragment fragment = new ShowFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container,false);
        initializeView(view);
        return view;
    }

    private void setupPager(ViewPager pager){
        ArrayList<Fragment> shows = new ArrayList<>();
        ViewPagerAdapter pvadapter = new ViewPagerAdapter(getChildFragmentManager());
        pvadapter.addFragment(
                ShowTopicFragment.getInstance(true), "Newest");
        pvadapter.addFragment(
                ShowTopicFragment.getInstance(false), "Popular");
        pager.setAdapter(pvadapter);
    }

    private void initializeView(View view){
        ArrayList<Show> showList = new ArrayList<>();
        adapter = new ShowListAdapter(view.getContext(), showList);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        setupPager(viewPager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setTitle(null);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
