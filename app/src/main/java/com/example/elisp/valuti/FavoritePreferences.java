package com.example.elisp.valuti;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.elisp.valuti.klasi.Model;
import com.google.gson.Gson;

public class FavoritePreferences {

    private static SharedPreferences getPreferences(Context c){
        return c.getApplicationContext().getSharedPreferences("MyFavorites", Activity.MODE_PRIVATE);
    }

    public static void addFav (Model favoriti, Context c){

        Gson gson = new Gson();
        String mapString = gson.toJson(favoriti);
        getPreferences(c).edit().putString("Favorite", mapString).apply();
    }
    public static void addConvert(Context c,String convert)
    {
        getPreferences(c).edit().putString("Convert", convert).apply();
    }
    public static void addLimit(Context c,int limit)
    {
        getPreferences(c).edit().putInt("Limit", limit).apply();
    }
    public static String getConvert(Context c)
    {
        return getPreferences(c).getString("Convert", "USD");
    }
    public static int getLimit(Context c)
    {
        return getPreferences(c).getInt("Limit", 0);
    }
    public static Model getFav (Context c){

        return new Gson().fromJson(getPreferences(c).getString("Favorite", ""), Model.class);
    }

    public static void removeFavorites(Context c){

        getPreferences(c).edit().remove("Favorite").apply();
    }

}
