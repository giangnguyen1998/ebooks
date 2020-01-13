package edu.nuce.giang.ebooks.activities.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.GridViewAdapter;

public class PhotosActivity extends AppCompatActivity {

    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_store);
        gridView = findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this, StoreImageActivity.al_images, int_position);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AvatarActivity.class);
                intent.putExtra("int_position", int_position);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });
    }
}
