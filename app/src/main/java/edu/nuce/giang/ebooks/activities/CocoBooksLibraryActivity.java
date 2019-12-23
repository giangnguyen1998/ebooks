package edu.nuce.giang.ebooks.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.LibraryAdapter;

public class CocoBooksLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_library);
        TabLayout tabLayout = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.vp);

        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("Reading Now"));
        tabLayout.addTab(tabLayout.newTab().setText("Read Later"));
        tabLayout.addTab(tabLayout.newTab().setText("Subscribed"));

        tabLayout.setTabGravity(TabLayout.MODE_FIXED);
        LibraryAdapter tabAdapter = new LibraryAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}




