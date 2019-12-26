package edu.nuce.giang.ebooks.activities.library;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.LibraryAdapter;

public class EBookLibraryActivity extends AppCompatActivity {

    @BindView(R.id.ic_back)
    ImageView onBack;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_library);

        ButterKnife.bind(this);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("Reading Now"));
        tabLayout.addTab(tabLayout.newTab().setText("Finished"));
        tabLayout.addTab(tabLayout.newTab().setText("All My Books"));

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

        onBack.setOnClickListener(v -> {
            finish();
        });
    }
}




