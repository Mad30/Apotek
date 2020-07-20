package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.Model.Obat;
import com.example.apotek.Model.Pesan;
import com.example.apotek.Model.User;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;
import com.example.apotek.listAdapter;
import com.example.apotek.obatadapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pesan_ObatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    obatadapter obatadapter;
    private Toolbar tb;
    Dialog dialog;
    List<Obat> obats;
    User user = SharedprefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan__obat);

        recyclerView = findViewById(R.id.recycler);
        layout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layout);
        tb = findViewById(R.id.toolbar);
        setActionBar(tb);

        Call<List<Obat>> call = RetrofitClient.getInstance().getApi()
                .getObat();
        call.enqueue(new Callback<List<Obat>>() {
            @Override
            public void onResponse(Call<List<Obat>> call, Response<List<Obat>> response) {
                List<Obat> obats = response.body();
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new obatadapter(Pesan_ObatActivity.this, obats));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(Pesan_ObatActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Obat>> call, Throwable t) {
                Toast.makeText(Pesan_ObatActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}