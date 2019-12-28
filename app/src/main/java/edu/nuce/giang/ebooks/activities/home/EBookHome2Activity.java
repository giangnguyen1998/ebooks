package edu.nuce.giang.ebooks.activities.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.activities.filter.EBookFilterActivity;
import edu.nuce.giang.ebooks.adapters.FictionTabAdapterSelected;
import edu.nuce.giang.ebooks.adapters.IconAdapter;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.CheckLoginModel;

public class EBookHome2Activity extends AppCompatActivity implements FictionTabAdapterSelected {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.edittext)
    EditText textFilter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_home2);

        ButterKnife.bind(this);

        if (getIntent().getSerializableExtra("user") != null) {
            SharedPrefs prefs = new SharedPrefs(getApplicationContext());
            prefs.clearAccount();
            prefs.setModel((CheckLoginModel) getIntent().getSerializableExtra("user"));
        }

        setUpTabLayout();
        setUpViewPager();

        textFilter.setOnClickListener(v -> {
            Intent intent = new Intent(EBookHome2Activity.this, EBookFilterActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EBookHome2Activity.this,
                    toolbar, "filterBook");
            startActivity(intent, options.toBundle());
        });
    }

    private void setUpTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.books));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.author));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.collection));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.favourite));
        tabLayout.setTabGravity(TabLayout.MODE_FIXED);
    }

    private void setUpViewPager() {
        IconAdapter tabAdapter = new IconAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        String status = getIntent().getStringExtra("status");
        if (status != null && !status.equals("")) {
            viewPager.setCurrentItem(3);
            new CustomSweetAlertDialog(EBookHome2Activity.this)
                    .alertDialogSuccess("Successfully!", status);
        }

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

    @Override
    public Fragment getItemFragment(int position) {
        switch (position) {
            case 0:
                return new EBookFragment();
            case 1:
                return new EBookAuthorFragment();
            case 2:
                return new EBookCollectionFragment();
            case 3:
                return new EBookProfileFragment();
            default:
                return null;
        }
    }
}



