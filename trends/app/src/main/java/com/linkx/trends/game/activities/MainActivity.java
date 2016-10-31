package com.linkx.trends.game.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.linkx.trends.R;
import com.linkx.trends.game.Consts;
import com.linkx.trends.game.view.adapters.PagerAdapter;

public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    //    @Bind(R.id.navigation_view)
//    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.pager_title_strip)
    PagerTitleStrip pagerTitleStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActionBar();
        setupViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupActionBar() {
//        toolbarTitle.setText("大家都在玩");
        toolbarTitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /*
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        */

    }

    private void setupViews() {

        /*
        navigationView.addHeaderView(new DrawerHeaderView(this));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // TODO
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
        */

//        for (Consts.Tab tab : Consts.tabs) {
//            tabLayout.addTab(tabLayout.newTab().setText(tab.name));
//        }

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), Consts.tabs.length);
        viewPager.setAdapter(adapter);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

}
