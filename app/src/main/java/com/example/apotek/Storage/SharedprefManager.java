package com.example.apotek.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.apotek.Model.User;

public class SharedprefManager {

    public static final String SHARED = "APOTEK_SHARED";
    private static SharedprefManager instance;
    private Context ctx;

    private SharedprefManager(Context ctx)
    {
        this.ctx = ctx;
    }

    public static synchronized SharedprefManager getInstance(Context ctx)
    {
        if (instance == null)
        {
            instance = new SharedprefManager(ctx);
        }
        return instance;
    }

    public void SaveUser(User user)
    {
        SharedPreferences sf = ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt("id",user.getId());
        editor.putString("nama",user.getNama());
        editor.putString("alamat",user.getAlamat());
        editor.putString("telp",user.getTelp());
        editor.putString("username",user.getUsername());
        editor.apply();
    }

    public boolean login()
    {
        SharedPreferences sf =ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        return sf.getInt("id",-1)!=-1;
    }

    public User getUser()
    {
        SharedPreferences sf =ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        return new User(
                sf.getString("nama",null),
                sf.getString("alamat",null),
                sf.getString("telp",null),
                sf.getString("username",null),
                sf.getInt("id",-1)
        );
    }

    public void clear()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
