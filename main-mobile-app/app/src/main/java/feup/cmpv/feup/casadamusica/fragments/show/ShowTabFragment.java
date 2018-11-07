package feup.cmpv.feup.casadamusica.fragments.show;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
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
