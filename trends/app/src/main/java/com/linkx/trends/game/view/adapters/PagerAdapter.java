package com.linkx.trends.game.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.linkx.trends.game.Consts;
import com.linkx.trends.game.view.fragments.FragmentGameList;

/**
 * Created by ulyx.yang on 2016/9/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private final int pagerCounts;

    public PagerAdapter(FragmentManager fm, int pagerCounts) {
        super(fm);
        this.pagerCounts = pagerCounts;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentGameList.newInstance(Consts.tabs[getPosition(position)].tag);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Consts.tabs[getPosition(position)].name;
    }

    @Override
    public int getCount() {
        return pagerCounts;
//        return Integer.MAX_VALUE;
    }

    private int getPosition(int position) {
        if (position < 0) {
            position = 0;
        }
        return position % pagerCounts;
    }
}
