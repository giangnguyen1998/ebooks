package edu.nuce.giang.ebooks.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.nuce.giang.ebooks.activities.PopularFragment;


public class TabAdapter extends FragmentStatePagerAdapter {
    int mnumoftabs;

    public TabAdapter(FragmentManager fm, int numoftabs) {
        super(fm);
        this.mnumoftabs = numoftabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PopularFragment tab1 = new PopularFragment();
                return tab1;
            case 1:
                PopularFragment tab2 = new PopularFragment();
                return tab2;

            case 2:
                PopularFragment tab3 = new PopularFragment();
                return tab3;
            case 3:
                PopularFragment tab4 = new PopularFragment();
                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mnumoftabs;
    }
}
