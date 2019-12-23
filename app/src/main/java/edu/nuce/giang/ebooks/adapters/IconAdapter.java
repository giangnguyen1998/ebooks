package edu.nuce.giang.ebooks.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.nuce.giang.ebooks.activities.home.EBookAuthorFragment;
import edu.nuce.giang.ebooks.activities.home.EBookFragment;
import edu.nuce.giang.ebooks.activities.home.EBookCollectionFragment;
import edu.nuce.giang.ebooks.activities.home.EBookProfileFragment;


public class IconAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private FictionTabAdapterSelected selected;

    public IconAdapter(FragmentManager fm, int numOfTabs, FictionTabAdapterSelected selected) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
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
