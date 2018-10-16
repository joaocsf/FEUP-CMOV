package feup.cmpv.feup.casadamusica.view;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import feup.cmpv.feup.casadamusica.R;

public class MainBody extends AppCompatActivity {


    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2, R.color.bottomtab_3};
    private AHBottomNavigationItem item1, item2, item3, item4;
    private AHBottomNavigation bottomNavigation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_body);

        bottomNavigation = findViewById(R.id.bottom_navigation);


        bottomNavigation.setDefaultBackgroundColor(fetchColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(fetchColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(fetchColor(R.color.colorInactive));
        bottomNavigation.setColored(true);
        bottomNavigation.setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomTabResting));
        // bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(true);


        item1 = new AHBottomNavigationItem(R.string.bottom_show, R.drawable.ic_music_note_black_24dp, colors[0]);
        item2 = new AHBottomNavigationItem(R.string.bottom_tickets, R.drawable.ic_local_activity_black_24dp, colors[1]);
        item3 = new AHBottomNavigationItem(R.string.bottom_bar, R.drawable.ic_local_bar_black_24dp, colors[2]);
        item4 = new AHBottomNavigationItem(R.string.bottom_settings, R.drawable.ic_settings_black_24dp, colors[3]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        View fragment = findViewById(R.id.frame);

//        bottomNavigation.setOnTabSelectedListener(
//                (position, wasSelected) -> {
//                    return;
//                }
//        );

        bottomNavigation.setCurrentItem(0);


    }

    private int fetchColor(@ColorRes int color){
        return ContextCompat.getColor(this, color);
    }
}
