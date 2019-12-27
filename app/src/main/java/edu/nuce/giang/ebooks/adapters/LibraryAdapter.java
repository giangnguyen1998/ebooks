package edu.nuce.giang.ebooks.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.nuce.giang.ebooks.activities.library.LibraryFragment;


public class LibraryAdapter  extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public LibraryAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LibraryFragment tab1 = new LibraryFragment();
                Bundle args1 = new Bundle();
                args1.putInt("value", 1);
                tab1.setArguments(args1);
                return tab1;
            case 1:
                LibraryFragment tab2 = new LibraryFragment();
                Bundle args2 = new Bundle();
                args2.putInt("value", 2);
                tab2.setArguments(args2);
                return tab2;

            case 2:
                LibraryFragment tab3 = new LibraryFragment();
                Bundle args3 = new Bundle();
                args3.putInt("value", 3);
                tab3.setArguments(args3);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

