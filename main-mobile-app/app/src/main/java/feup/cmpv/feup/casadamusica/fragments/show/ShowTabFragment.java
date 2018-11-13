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
    ShowTopicFragment showTopic;
    ShowTopicFragment showTopicPopular;

    public static Fragment getInstance(){
        Fragment fragment = new ShowTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_0);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onDoubleSelect() {
        if(showTopicPopular != null && showTopic != null) {
            showTopic.updateShows(true);
            showTopicPopular.updateShows(true);
        }
    }

    @Override
    public void onSelected() {
        if(showTopicPopular != null && showTopic != null) {
            showTopic.updateShows(false);
            showTopicPopular.updateShows(false);
        }
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        showTopic = (ShowTopicFragment) ShowTopicFragment.getInstance(true);
        showTopicPopular = (ShowTopicFragment) ShowTopicFragment.getInstance(false);

        pvadapter.addFragment(showTopic, "Newest");
        pvadapter.addFragment(showTopicPopular, "Popular");
    }
}
