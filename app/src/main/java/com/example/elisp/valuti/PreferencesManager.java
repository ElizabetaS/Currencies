package com.example.elisp.valuti;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.elisp.valuti.klasi.User;
import com.google.gson.Gson;



/**
 * Created by tasev on 2/1/18.
 */

public class PreferencesManager {

    private static final String USERID = "USERID";
    private static final String USER = "USER";

    private static SharedPreferences getPreferences(Context c) {
        return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);
    }

    public static void addUser(User user, Context c) {
        Gson gson = new Gson();
        String mapString = gson.toJson(user);
        getPreferences(c).edit().putString(USER, mapString).apply();
    }

    public static User getUser(Context c) {
        return new Gson().fromJson(getPreferences(c).
                getString(USER, ""), User.class);
    }

    public static void removeUser(Context c) {
        getPreferences(c).edit().remove(USER).apply();
    }

    public static void setUserID(String firstName, Context c) {
        getPreferences(c).edit().putString(USERID, firstName).apply();
    }

    public static String getUserID(Context c) {
        return getPreferences(c).getString(USERID, "");
    }

    public static void removeUserID(Context c) {
        getPreferences(c).edit().remove(USERID).apply();
    }


}
