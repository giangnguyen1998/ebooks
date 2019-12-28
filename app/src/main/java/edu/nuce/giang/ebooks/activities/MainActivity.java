package edu.nuce.giang.ebooks.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView screen11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_main);

        screen11 = findViewById(R.id.screen11);


    }
}
