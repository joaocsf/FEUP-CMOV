package feup.cmpv.feup.casadamusica.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.structures.Show;

public abstract class TabFragment extends Fragment {

    private ArrayAdapter<Show> adapter;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container,false);
        initializeView(view);
        return view;
    }

    public void onSelected(){}
    public void onDeselected(){}



    protected abstract void setupViewPagerAdapter(ViewPagerAdapter pvadapter);

    protected ViewPager getViewPager(){
        return viewPager;
    }

    private void setupPager(ViewPager pager){
        ViewPagerAdapter pvadapter = new ViewPagerAdapter(getChildFragmentManager());

        setupViewPagerAdapter(pvadapter);

        pager.setAdapter(pvadapter);
    }

    private void initializeView(View view){
        ArrayList<Show> showList = new ArrayList<>();
        adapter = new ShowListAdapter(view.getContext(), showList);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        this.viewPager = viewPager;
        setupPager(viewPager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);


        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setTitle(null);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        int color = getArguments().getInt("color");
        tabLayout.setBackgroundResource(color);


    }
}
