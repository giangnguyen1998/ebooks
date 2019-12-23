package edu.nuce.giang.ebooks.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import edu.nuce.giang.ebooks.models.CheckLoginModel;

public class SharedPrefs {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private CheckLoginModel model;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "pref-account";
    private static final String IS_ACCOUNT = "account";

    public SharedPrefs(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public CheckLoginModel getModel() {
        Gson gson = new Gson();
        String json = pref.getString(IS_ACCOUNT, "");
        model = gson.fromJson(json, CheckLoginModel.class);
        return model;
    }

    public void setModel(CheckLoginModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString(IS_ACCOUNT, json);
        editor.commit();
    }

    public void clearAccount() {
        pref.edit().clear().apply();
    }
}
