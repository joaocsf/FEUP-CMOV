package feup.cmpv.feup.casadamusica.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;

public class EmptyTabFragment extends TabFragment {

    public static Fragment getInstance(){
        Fragment fragment = new EmptyTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_1);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        // pvadapter.addFragment(null, "Test");
    }
}
