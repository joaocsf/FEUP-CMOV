package feup.cmpv.feup.casadamusica.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.fragments.TabFragment;
import feup.cmpv.feup.casadamusica.fragments.bar.BarTabFragment;
import feup.cmpv.feup.casadamusica.fragments.personal.PersonalTabFragment;
import feup.cmpv.feup.casadamusica.fragments.settings.SettingsTabFragment;
import feup.cmpv.feup.casadamusica.fragments.show.ShowTabFragment;

public class BottomFragmentPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<TabFragment> fragments = new ArrayList<>();
    private Fragment currentFragment;
    private int lastSelected = 0;

    public BottomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add((TabFragment)ShowTabFragment.getInstance());
        fragments.add((TabFragment)PersonalTabFragment.getInstance());
        fragments.add((TabFragment)BarTabFragment.getInstance());
        fragments.add((TabFragment)SettingsTabFragment.getInstance());
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

    public  void setSelected(int selected){
        if(selected == lastSelected) {
            fragments.get(selected).onDoubleSelect();
            return;
        }
        fragments.get(lastSelected).onDeselected();
        fragments.get(selected).onSelected();
        lastSelected = selected;
    }

    public Fragment getCurrentFragment(){
        return currentFragment;
    }
}
