package edu.nuce.giang.ebooks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.GridAdapter;
import edu.nuce.giang.ebooks.models.GridModel;

public class CocoBookGerneActivity extends AppCompatActivity {

    private ArrayList<GridModel> homeListModelClassArrayList1;
    private RecyclerView recyclerView;
    private GridAdapter bAdapter;
    String fieldName[] = {"ART\n" +
            "&\n" +
            "DESIGN", "SCIENCE", "BUSINESS", "FASHION", "POLITICS", "NATURE", "TECHNOLOGY", "IDEA", "SECURITY"};
    Integer fieldImage[] = {R.drawable.grp1, R.drawable.grp2, R.drawable.grp3, R.drawable.grp4,
            R.drawable.grp5, R.drawable.grp6, R.drawable.grp7,
            R.drawable.grp8, R.drawable.grp9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_gerne);

        CocoBookGerneActivity.this.getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CocoBookGerneActivity.this,
                3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList1 = new ArrayList<>();

        for (int i = 0; i < fieldName.length; i++) {
            GridModel beanClassForRecyclerView_contacts = new GridModel(fieldName[i], fieldImage[i]);
            homeListModelClassArrayList1.add(beanClassForRecyclerView_contacts);
        }
        bAdapter = new GridAdapter(CocoBookGerneActivity.this, homeListModelClassArrayList1);
        recyclerView.setAdapter(bAdapter);

    }
}
