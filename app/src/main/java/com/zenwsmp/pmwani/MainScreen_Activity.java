package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.zenwsmp.pmwani.Fragment.ExploreFragment;
import com.zenwsmp.pmwani.Fragment.HomeFragment;

public class MainScreen_Activity extends AppCompatActivity {

    private int currentTab = 0;
    private final int TAB_HOME = 0;
    private final int TAB_SEARCH = 1;
    private final int TAB_PROFILE = 2;
    private final int TAB_MYTICKET = 3;
    private final int TAB_MORE = 4;
    private  boolean backPressedToExitOnce=false;
    private AHBottomNavigation mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();

        loadHomeFragment();
    }

    private void setupBottomNavigation() {
        mBottomNavigationView.setTranslucentNavigationEnabled(false);
        mBottomNavigationView.setBehaviorTranslationEnabled(false);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home",R.drawable.home_reg, R.color.maingreen);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Explore", R.drawable.explore_reg, R.color.maingreen);
       // AHBottomNavigationItem item3 = new AHBottomNavigationItem("TRAFFIC", R.drawable.ic_timeline_black_36dp, R.color.tintblue);
        //  AHBottomNavigationItem item4 = new AHBottomNavigationItem("APPLICATION",R.drawable.ic_apps_black_36dp, R.color.tintblue);
        // AHBottomNavigationItem item5 = new AHBottomNavigationItem("HEALTH",R.drawable.ic_health, R.color.tintblue);
        // AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.str_more, R.drawable.ic_more_selected, R.color.colorPrimary);

        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);
       // mBottomNavigationView.addItem(item3);
        ///  mBottomNavigationView.addItem(item4);
        //  mBottomNavigationView.addItem(item5);

        currentTab = TAB_HOME;

        mBottomNavigationView.setAccentColor(getResources().getColor(R.color.maingreen));
        mBottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        mBottomNavigationView.setCurrentItem(TAB_HOME);
        mBottomNavigationView.getItem(TAB_HOME).setDrawable(R.drawable.home_reg);
        mBottomNavigationView.getItem(TAB_HOME).setColor(getResources().getColor(R.color.maingreen));
        mBottomNavigationView.setNotificationBackgroundColor(Color.parseColor("#ffffff"));
        mBottomNavigationView.setOnTabSelectedListener((position, wasSelected) -> {

           /* if(progress1.getVisibility()==View.VISIBLE)
                return false;*/

            switch (position)
            {
                case TAB_HOME: {

                    loadHomeFragment();
                    return true;
                }
                case TAB_SEARCH:{
                    loadExploreFragment();
                    return true;
                }
                /*case TAB_PROFILE:{
                    loadSingleLineFragment(Cloud);
                    return true;
                }
                case TAB_MYTICKET:{
                    loadApplicationUsageChartFragment(Cloud);
                    return true;
                }
                case TAB_MORE:{
                    loadBarGroupFragment(Cloud);
                    return true;
                }*/

            }
            return false;
        });

    }

    private void loadHomeFragment() {

       // tvTitle.setText("Cloud Dashboard");
        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadExploreFragment() {

        // tvTitle.setText("Cloud Dashboard");
        ExploreFragment fragment = ExploreFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

}