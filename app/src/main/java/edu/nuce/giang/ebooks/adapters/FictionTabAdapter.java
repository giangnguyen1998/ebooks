package edu.nuce.giang.ebooks.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FictionTabAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs;
    private FictionTabAdapterSelected selected;

    public FictionTabAdapter(FragmentManager fm, int mNumOfTabs, FictionTabAdapterSelected selected) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        this.selected = selected;
    }

    @Override
    public Fragment getItem(int position) {
        return selected.getItemFragment(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

