package feup.cmpv.feup.casadamusica.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.fragments.EmptyTabFragment;
import feup.cmpv.feup.casadamusica.fragments.show.ShowTabFragment;

public class BottomFragmentPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public BottomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(ShowTabFragment.getInstance());
        fragments.add(EmptyTabFragment.getInstance());
        fragments.add(EmptyTabFragment.getInstance());
        fragments.add(EmptyTabFragment.getInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(getCurrentFragment() != object){
            currentFragment = ((Fragment)object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public Fragment getCurrentFragment(){
        return currentFragment;
    }
}
