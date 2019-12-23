package edu.nuce.giang.ebooks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.HomeRecyclerAdapter;
import edu.nuce.giang.ebooks.models.HomeRecycleModelClass;


public class CocoBooksHomeActivity extends AppCompatActivity {
    private ArrayList<HomeRecycleModelClass> homeListModelClassArrayList1;
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter bAdapter;

    int bookImage[]={R.drawable.lord, R.drawable.the_world,R.drawable.brain,R.drawable.fred,
            R.drawable.broke,R.drawable.online};
    String bookName[]={"Hugo Huesca Dungeon Lord","The World Of Abstra","Mind and  Pieces of Light",
            "Hugo Huesca Dungeon Lord","The World Of Abstra","Mind and  Pieces of Light"};
    String authorName[]={"M Methew","Create Media","Charles","M Methew","Create Media","Charles"};


    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_home);

        editText = (EditText)findViewById(R.id.edittext);

        CocoBooksHomeActivity.this.getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CocoBooksHomeActivity.this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList1 = new ArrayList<>();

        for (int i = 0; i < bookImage.length; i++) {
            HomeRecycleModelClass beanClassForRecyclerView_contacts = new HomeRecycleModelClass(bookImage[i],bookName[i],authorName[i]);
            homeListModelClassArrayList1.add(beanClassForRecyclerView_contacts);
        }
        bAdapter = new HomeRecyclerAdapter(CocoBooksHomeActivity.this,homeListModelClassArrayList1);
        recyclerView.setAdapter(bAdapter);

    }
}
