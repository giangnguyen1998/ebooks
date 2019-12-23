package edu.nuce.giang.ebooks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.nuce.giang.ebooks.R;

public class CocoBookDetailActivity extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_detail);
        txt=(TextView)findViewById(R.id.txtToolbarTitle);
        txt.setText("Science");

    }
}
