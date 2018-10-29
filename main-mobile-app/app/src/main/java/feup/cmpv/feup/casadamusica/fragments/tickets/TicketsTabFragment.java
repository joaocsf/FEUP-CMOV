package feup.cmpv.feup.casadamusica.fragments.tickets;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.fragments.TabFragment;

public class TicketsTabFragment extends TabFragment {

    public static Fragment getInstance(){
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_1);
        Fragment fragment = new TicketsTabFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {

    }
}
