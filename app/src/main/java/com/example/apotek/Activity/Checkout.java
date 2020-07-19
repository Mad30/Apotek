package com.example.apotek.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.Model.Pesan;
import com.example.apotek.Model.User;
import com.example.apotek.R;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    TextView txharga,txnama,txjumlah,txpost;
    User user = SharedprefManager.getInstance(this).getUser();
    Bitmap bitmap;
    private static String CHANNELL_ID = "apotekCH";
    private static String CHANNEL_NAME = "ApotekCH";
    private static String CHANNEL_DESC = "apotekch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        img = findViewById(R.id.image);
        txharga = findViewById(R.id.harga);
        txnama = findViewById(R.id.namaobat);
        txpost = findViewById(R.id.post);
        txjumlah = findViewById(R.id.jumlah);

        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.choose).setOnClickListener(this);



        Intent i = getIntent();
       String nama = i.getStringExtra("nama");
        String jml = i.getStringExtra("jumlah");
        String hrg = i.getStringExtra("harga");
        int id = Integer.parseInt(i.getStringExtra("id"));
        txnama.setText("Nama Obat: "+nama);
        txjumlah.setText("Jumlah : "+jml);
        txharga.setText("Harga : "+"Rp"+hrg);
        txpost.append(" "+id);
        getuser();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel nc = new NotificationChannel(CHANNELL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            nc.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(nc);
        }

    }

    private void getuser() {
        Call<List<User>> call = RetrofitClient.getInstance().getApi().getUser(user.getId());

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(Checkout.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
                List<User> users = response.body();
                for (User us : users) {

                    txpost.setText(us.getAlamat()+", "+us.getNama()+", "+us.getTelp());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Checkout.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                upload();
                break;
            case R.id.choose:
                select();
                break;
        }
    }

    private void upload() {
        Intent i = getIntent();
        int id = Integer.parseInt(i.getStringExtra("id"));
        String image = ImagetoString();
        Call<Defaultresponse> call = RetrofitClient.getInstance().getApi().Uploadcheckout(id,image);
        call.enqueue(new Callback<Defaultresponse>() {
            @Override
            public void onResponse(Call<Defaultresponse> call, Response<Defaultresponse> response) {
                if (!response.body().isError())
                {
                    Toast.makeText(Checkout.this,"Gagal", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(Checkout.this, "Berhasil", Toast.LENGTH_LONG).show();
                notifikasi();
                startActivity(new Intent(Checkout.this,MainActivity.class));

            }

            @Override
            public void onFailure(Call<Defaultresponse> call, Throwable t) {
                Toast.makeText(Checkout.this,t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public String ImagetoString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imgByte,Base64.DEFAULT);
        return encodedImage;

    }

    private void select() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Pilih foto"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!=null){
            Uri filepath = data.getData();

           try {
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
               img.setImageBitmap(bitmap);
           }catch (IOException e){
               e.printStackTrace();
           }
        }
    }
    public void notifikasi()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNELL_ID)
                .setContentTitle("Apotek").setSmallIcon(R.drawable.logo_resize).setContentText("Terimakasih Sudah Berbelanja")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat nmanager = NotificationManagerCompat.from(this);
        nmanager.notify(1,builder.build());

    }


}
