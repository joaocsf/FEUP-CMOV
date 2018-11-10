package feup.cmpv.feup.casadamusica.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.BottomFragmentPagerAdapter;

public class MainBody extends AppCompatActivity {


    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2, R.color.bottomtab_3};
    private AHBottomNavigationItem item1, item2, item3, item4;
    private AHBottomNavigation bottomNavigation;
    private FloatingActionButton floatingActionButton;
    private BottomFragmentPagerAdapter adapter;
    private AHBottomNavigationViewPager pager;



    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public FloatingActionButton getFloatingActionButton(){
        return floatingActionButton;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_body);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        Log.d("Main", "Created");
        floatingActionButton = findViewById(R.id.floating_action_button);
        pager = findViewById(R.id.view_pager);


        adapter = new BottomFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);

        bottomNavigation.setDefaultBackgroundColor(fetchColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(fetchColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(fetchColor(R.color.colorInactive));
        bottomNavigation.setColored(true);
        bottomNavigation.setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomTabResting));
        // bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);


        item1 = new AHBottomNavigationItem(R.string.bottom_show, R.drawable.ic_music_note_black_24dp, colors[0]);
        item2 = new AHBottomNavigationItem(R.string.bottom_tickets, R.drawable.ic_local_activity_black_24dp, colors[1]);
        item3 = new AHBottomNavigationItem(R.string.bottom_bar, R.drawable.ic_local_bar_black_24dp, colors[2]);
        item4 = new AHBottomNavigationItem(R.string.bottom_settings, R.drawable.ic_settings_black_24dp, colors[3]);

        //pager.setOffscreenPageLimit(4);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setOnTabSelectedListener(
                (position, wasSelected) -> {
                    pager.setCurrentItem(position,true);
                    adapter.setSelected(position);

                    return true;
                }
        );


        bottomNavigation.setCurrentItem(0);

    }

    private int fetchColor(@ColorRes int color){
        return ContextCompat.getColor(this, color);
    }
}
