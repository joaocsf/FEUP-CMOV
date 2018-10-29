package feup.cmpv.feup.casadamusica.fragments.show;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import feup.cmpv.feup.casadamusica.fragments.TabFragment;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ShowTabFragment extends TabFragment {

    private ArrayAdapter<Show> adapter;

    public static Fragment getInstance(){
        Fragment fragment = new ShowTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_0);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        pvadapter.addFragment(
                ShowTopicFragment.getInstance(true), "Newest");
        pvadapter.addFragment(
                ShowTopicFragment.getInstance(false), "Popular");
    }
}
