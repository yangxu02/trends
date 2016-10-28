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
    private final int pagrCounts;

    public PagerAdapter(FragmentManager fm, int pagrCounts) {
        super(fm);
        this.pagrCounts = pagrCounts;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0 || position >= Consts.tabs.length) {
            return null;
        }

        return FragmentGameList.newInstance(Consts.tabs[position].tag);
    }

    @Override
    public int getCount() {
        return pagrCounts;
    }
}
