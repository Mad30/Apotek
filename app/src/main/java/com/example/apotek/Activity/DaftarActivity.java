package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.DefaultDatabaseErrorHandler;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btndaftar,btnpost;
    private EditText etnama,etalm,ettelp,etuname,etpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        etnama = findViewById(R.id.etnama);
        etalm = findViewById(R.id.etalamat);
        ettelp = findViewById(R.id.ettelp);
        etuname = findViewById(R.id.etuname);
        etpass = findViewById(R.id.etpass);
        btnpost = findViewById(R.id.btnpost);
        btndaftar = findViewById(R.id.btndaftar);
        btndaftar.setOnClickListener(this);
        btnpost.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btndaftar:
                daftar();
                break;
            case R.id.btnpost:
                startActivity(new Intent(DaftarActivity.this,LoginActivity.class));
                break;
        }
    }

    private void daftar() {
        String nama,alamat,telp,username,password;
        nama = etnama.getText().toString().trim();
        alamat = etalm.getText().toString().trim();
        telp = ettelp.getText().toString().trim();
        username = etuname.getText().toString().trim();
        password = etpass.getText().toString().trim();

        if (nama.isEmpty())
        {
            etnama.setError("Nama tidak boleh kosong");
            etnama.requestFocus();
            return;
        }
        if (alamat.isEmpty())
        {
            etalm.setError("Alamat tidak boleh kosong");
            etalm.requestFocus();
            return;
        }
        if (telp.isEmpty())
        {
            ettelp.setError("Nomor telepon tidak boleh kosong");
            ettelp.requestFocus();
            return;
        }
        if(telp.length()<12)
        {
            ettelp.setError("Nomor telepon tidak valid");
            ettelp.requestFocus();
            return;
        }
        if (username.isEmpty())
        {
            etuname.setError("Username tidak boleh kosong");
            etuname.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            etpass.setError("Password tidak boleh kosong");
            etpass.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            etpass.setError("Password harus lebih dari 6 karakter");
            etpass.requestFocus();
            return;
        }


        Call<Defaultresponse> call = RetrofitClient.getInstance().getApi()
                .daftar(nama,alamat,telp,username,password);

        call.enqueue(new Callback<Defaultresponse>() {
            @Override
            public void onResponse(Call<Defaultresponse> call, Response<Defaultresponse> response) {
                Defaultresponse defaultresponse = response.body();
                if (!defaultresponse.isError())
                {
                    Toast.makeText(DaftarActivity.this, defaultresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DaftarActivity.this,LoginActivity.class));

                }

                Toast.makeText(DaftarActivity.this, defaultresponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Defaultresponse> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        if ( SharedprefManager.getInstance(DaftarActivity.this).login())
        {
            Intent i = new Intent(DaftarActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }


}
