package edu.nuce.giang.ebooks.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.nuce.giang.ebooks.activities.LibraryFragment;


public class LibraryAdapter  extends FragmentStatePagerAdapter {
    int mnumoftabs;

    public LibraryAdapter(FragmentManager fm, int numoftabs) {
        super(fm);
        this.mnumoftabs = numoftabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LibraryFragment tab1 = new LibraryFragment();
                return tab1;
            case 1:
                LibraryFragment tab2 = new LibraryFragment();
                return tab2;

            case 2:
                LibraryFragment tab3 = new LibraryFragment();
                return tab3;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mnumoftabs;
    }
}

