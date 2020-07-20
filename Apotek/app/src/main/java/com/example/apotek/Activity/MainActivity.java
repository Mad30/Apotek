package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.apotek.R;
import com.example.apotek.Storage.SharedprefManager;

public class MainActivity extends AppCompatActivity {

    private long time;
    private Toast back;
    private Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb =findViewById(R.id.toolbar);
        setActionBar(tb);
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis())
        {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            back.cancel();
        }
        else
        {
            back = Toast.makeText(MainActivity.this, "Press back again", Toast.LENGTH_SHORT);
            back.show();
        }

        time = System.currentTimeMillis();
    }

    public void Klikmenu(View view) {

        switch (view.getId())
        {
            case R.id.shop:
                startActivity(new Intent(MainActivity.this,Pesan_ObatActivity.class));
                break;
            case R.id.keranjang:
                startActivity(new Intent(MainActivity.this,StatusActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this,EditProfile.class));
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedprefManager.getInstance(MainActivity.this).clear();
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedprefManager.getInstance(MainActivity.this).login())
        {
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }
}
