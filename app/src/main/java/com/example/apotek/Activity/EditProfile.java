package com.example.apotek.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.Model.LoginResponse;
import com.example.apotek.Model.User;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    Toolbar tb;
    User user;
    EditText etnama,ettelp,etalm,etuser,editnama,edituser,edittelp,editalamat;
    BottomSheetDialog dialog;
    Button editsave,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        tb = findViewById(R.id.toolbar);
        etnama = findViewById(R.id.etnama);
        etalm = findViewById(R.id.etalamat);
        ettelp = findViewById(R.id.ettelp);
        etuser = findViewById(R.id.etuser);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        setActionBar(tb);
        user = SharedprefManager.getInstance(this).getUser();

        getuser();

    }

    private void getuser() {
        Call<List<User>> call = RetrofitClient.getInstance().getApi().getUser(user.getId());

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(EditProfile.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
                List<User> users = response.body();
                for (User us : users) {

                    etnama.setText(us.getNama());
                    etalm.setText(us.getAlamat());
                    ettelp.setText(us.getTelp());
                    etuser.setText(us.getUsername());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(EditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_edit:
                popUp();
                break;
        }
    }

    private void popUp() {
        dialog = new BottomSheetDialog(EditProfile.this);
        dialog.setContentView(R.layout.dialog_edit);

        editnama= dialog.findViewById(R.id.editnama);
        edittelp= dialog.findViewById(R.id.edittelp);
        edituser= dialog.findViewById(R.id.editusername);
        cancel = dialog.findViewById(R.id.cancel);
        editalamat = dialog.findViewById(R.id.editalamat);
        editsave = dialog.findViewById(R.id.editsave);

        getuser();
        String uname =etuser.getText().toString();
        String nm =etnama.getText().toString();
        String mil =etalm.getText().toString();
        String telp = ettelp.getText().toString();
        editnama.setText(nm);
        editalamat.setText(mil);
        edituser.setText(uname);
        edittelp.setText(telp);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
        dialog.show();
    }

    private void updateUser() {
        String uname =edituser.getText().toString();
        String nama =editnama.getText().toString();
        String alamat =editalamat.getText().toString();
        String telp = edittelp.getText().toString();

        if (nama.isEmpty())
        {
            editnama.setError("Nama tidak boleh kosong");
            editnama.requestFocus();
            return;
        }
        if (alamat.isEmpty())
        {
            editalamat.setError("Alamat tidak boleh kosong");
            editalamat.requestFocus();
            return;
        }
        if (telp.isEmpty())
        {
            edittelp.setError("Nomor telepon tidak boleh kosong");
            edittelp.requestFocus();
            return;
        }
        if(telp.length()<12)
        {
            edittelp.setError("Nomor telepon tidak valid");
            edittelp.requestFocus();
            return;
        }
        if (uname.isEmpty())
        {
            edituser.setError("Username tidak boleh kosong");
            edituser.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().upadateUser(
                user.getId(),nama,alamat,telp,uname);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.body().isError())
                {
                    Toast.makeText(EditProfile.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                    SharedprefManager.getInstance(EditProfile.this).SaveUser(response.body().getUser());
                    startActivity(new Intent(EditProfile.this,EditProfile.class));
                    dialog.dismiss();
                }
                else if (response.body().getUser() == null){
//                    Toast.makeText(EditProfile.this, "User berhasil diupdate", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfile.this, EditProfile.class));
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfile.this, EditProfile.class));
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(EditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
    startActivity(new Intent(EditProfile.this,MainActivity.class));
    super.onBackPressed();
    }
}
