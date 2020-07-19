package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.apotek.Model.Pesan;
import com.example.apotek.Model.User;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;
import com.example.apotek.listAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {

    ListView listView;
    Toolbar tb;
    TextView harga;
    User user;
    listAdapter listAdapter;
    List<Pesan> pesans;
    ArrayList<Pesan> pesanArrayList;
    int hargatot = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        listView = findViewById(R.id.list);
        tb = findViewById(R.id.bottom);
        setActionBar(tb);
        user = SharedprefManager.getInstance(this).getUser();
        harga = findViewById(R.id.harga);
        Intent i = getIntent();
       hargatot =  i.getIntExtra("harga",0);

//        listView.setAdapter(new listAdapter(getApplicationContext(),R.layout.list_status,nama,harga,desc,arr));

        Call<List<Pesan>> call = RetrofitClient.getInstance()
                .getApi().getpesan(user.getId());
        call.enqueue(new Callback<List<Pesan>>() {
            @Override
            public void onResponse(Call<List<Pesan>> call, Response<List<Pesan>> response) {
                pesans = response.body();
                if (response.isSuccessful())
                {
                    listView.setAdapter(new listAdapter(StatusActivity.this,R.layout.list_status,pesans));
                }
                else
                {
                    Toast.makeText(StatusActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pesan>> call, Throwable t) {
                Toast.makeText(StatusActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StatusActivity.this,MainActivity.class));
    }
}
