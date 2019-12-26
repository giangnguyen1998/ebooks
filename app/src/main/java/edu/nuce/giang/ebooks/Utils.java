package edu.nuce.giang.ebooks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import edu.nuce.giang.ebooks.activities.login.EBookLoginActivity;
import edu.nuce.giang.ebooks.api.EBookApi;
import edu.nuce.giang.ebooks.api.EBookClient;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.repositories.DataBaseUtils;

public class Utils {
    public static final String URL = "http://192.168.1.35:3000/";
    private static DataBaseUtils dataBaseUtils = null;

    public static DataBaseUtils getDataBaseUtilsInstance(Context context) {
        if (dataBaseUtils == null) {
            dataBaseUtils = new DataBaseUtils(context);
        }
        return dataBaseUtils;
    }

    public static EBookApi getEBookApiInstance() {
        return EBookClient.getEBookClient().create(EBookApi.class);
    }

    public static AlertDialog showAlertDialog(Context context, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", ((dialog1, which) -> {
            dialog1.dismiss();
        }));
        return dialog;
    }
}
