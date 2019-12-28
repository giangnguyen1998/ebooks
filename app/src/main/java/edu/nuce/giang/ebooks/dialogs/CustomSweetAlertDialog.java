package edu.nuce.giang.ebooks.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.nuce.giang.ebooks.R;

public class CustomSweetAlertDialog {

    private Context context;

    public CustomSweetAlertDialog(Context context) {
        this.context = context;
    }

    public void alertDialogSuccess(String title,String message) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void alertDialogError(String title, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void alertDialogLoading() {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void showCustomDialog(String mTitle, String mDescription) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_ebooks_top_chat,
                null
        );

        TextView title = view.findViewById(R.id.tv_title);
        TextView description = view.findViewById(R.id.tv_description);
        final Button buttonCancel = view.findViewById(R.id.btn_cancel);
        final Button buttonAllow = view.findViewById(R.id.btn_allow);

        title.setText(mTitle);
        description.setText(mDescription);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        buttonAllow.setOnClickListener(v -> {
        });

        buttonCancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        });
    }

}


