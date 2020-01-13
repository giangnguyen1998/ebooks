package edu.nuce.giang.ebooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.activities.home.EBookHomeActivity;

public class EBookSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_splash);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2*1000);
                    Intent intent = new Intent(EBookSplashActivity.this, EBookHomeActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
