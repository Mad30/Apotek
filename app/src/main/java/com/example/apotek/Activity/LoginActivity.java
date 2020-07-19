package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apotek.Model.LoginResponse;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin,btncreate;
    private EditText etuname,etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etuname =  findViewById(R.id.etname);
        etpass =  findViewById(R.id.etpass);

        btnlogin = findViewById(R.id.btnlogin);
        btncreate = findViewById(R.id.btncreate);
        btncreate.setOnClickListener(this);
        btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btncreate:
                startActivity(new Intent(LoginActivity.this,DaftarActivity.class));
                break;
            case R.id.btnlogin:
                login();

                break;
        }

    }

    private void login() {

       String username = etuname.getText().toString().trim();
       String password = etpass.getText().toString().trim();

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

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi()
                .login(username,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError())
                {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    SharedprefManager.getInstance(LoginActivity.this).SaveUser(loginResponse.getUser());
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else{

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ( SharedprefManager.getInstance(this).login())
        {
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }
}
