package feup.cmpv.feup.casadamusica.fragments.bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.fragments.TabFragment;

public class BarTabFragment extends TabFragment {

    public static Fragment getInstance(){
        Fragment fragment = new BarTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_2);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        pvadapter.addFragment(
                BarProductsFragment.getInstance(), "Bar");
        pvadapter.addFragment(
                BarProductsFragment.getInstance(), "Purchases");
    }
}
