package feup.cmpv.feup.casadamusica.fragments.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.fragments.TabFragment;

public class SettingsTabFragment extends TabFragment {

    private UsedTicketsFragment usedTicketsFragment;

    public static Fragment getInstance(){
        Fragment fragment = new SettingsTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_3);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onSelected() {
        usedTicketsFragment.fetchTickets();
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        pvadapter.addFragment(
                MainSettingsFragment.getInstance(), "Settings");

        usedTicketsFragment = (UsedTicketsFragment) UsedTicketsFragment.getInstance();
        pvadapter.addFragment(
               usedTicketsFragment , "Used tickets");
    }
}
