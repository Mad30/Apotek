package com.example.apotek;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apotek.Activity.Pesan_ObatActivity;
import com.example.apotek.Activity.StatusActivity;
import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.Model.Obat;
import com.example.apotek.Model.User;
import com.example.apotek.RetrofitUtils.RetrofitClient;
import com.example.apotek.Storage.SharedprefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class obatadapter extends RecyclerView.Adapter<obatadapter.RecyclerViewAdapter> {

    private Context context;
    private List<Obat> obats;
    private User user = SharedprefManager.getInstance(context).getUser();

    public obatadapter(Context context, List<Obat> obats) {
        this.context = context;
        this.obats = obats;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_obat,
                parent, false);
        return new RecyclerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Obat obat = obats.get(position);
        holder.txtnama.setText(obat.getNama_obat());
        holder.texthrg.setText("Rp. " + obat.getHarga());
        holder.txtstok.setText("stok:"+obat.getJumlah());
        Glide.with(context).load(obat.getImage()).into(holder.images);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(R.layout.dialog_shop);

                TextView angka, nama, harga;
                ImageView img, min, plus, cart;
                Button beli;
                Obat obat1 = obats.get(position);

                angka = dialog.findViewById(R.id.angka);
                nama = dialog.findViewById(R.id.nama);
                harga = dialog.findViewById(R.id.harga);
                img = dialog.findViewById(R.id.img);
                min = dialog.findViewById(R.id.kurang);
                plus = dialog.findViewById(R.id.plus);
                beli = dialog.findViewById(R.id.beli);
                cart = dialog.findViewById(R.id.cart);
                nama.setText(obat1.getNama_obat());
                harga.setText("Rp" + obat1.getHarga());
                Glide.with(context).load(obat1.getImage()).into(img);

                min.setOnClickListener(v1 -> {
                    int jumlah = Integer.parseInt(angka.getText().toString());
                    if (jumlah > 1) {
                        jumlah -= 1;
                        angka.setText(String.valueOf(jumlah));

                    }
                });
                plus.setOnClickListener(v12 -> {
                    int jumlah = Integer.parseInt(angka.getText().toString());
                    if (jumlah < obat1.getJumlah()) {
                        jumlah += 1;
                        angka.setText(String.valueOf(jumlah));

                    }
                });
                cart.setOnClickListener(v14 -> {

                });

                beli.setOnClickListener(v13 -> {
                    int jumlah = Integer.parseInt(angka.getText().toString());
                    int hrg = obat1.getHarga() * jumlah;
                    String nmobat = obat1.getNama_obat();
                    Calendar calendar = Calendar.getInstance();
                    Date today = calendar.getTime();
                    String tanggal = new SimpleDateFormat("yyyy-mm-dd").format(today);
                    Call<Defaultresponse> call = RetrofitClient.getInstance()
                            .getApi().order(nmobat,
                                    jumlah, hrg,
                                    tanggal, user.getId()
                            );

                    call.enqueue(new Callback<Defaultresponse>() {
                        @Override
                        public void onResponse(Call<Defaultresponse> call, Response<Defaultresponse> response) {
                            if (!response.body().isError()) {
                                context.startActivity(new Intent(context.getApplicationContext(), StatusActivity.class));

                            }
                        }

                        @Override
                        public void onFailure(Call<Defaultresponse> call, Throwable t) {
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
//                Toast.makeText(ctx, "harga: "+hrg +", "+nmobat, Toast.LENGTH_SHORT).show();
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return obats.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView texthrg, txtnama, txtstok;
        ImageView images;
        CardView card;

        RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);

            texthrg = itemView.findViewById(R.id.textharga);
            txtnama = itemView.findViewById(R.id.textnama);
            txtstok = itemView.findViewById(R.id.textstok);
            images = itemView.findViewById(R.id.images);
            card = itemView.findViewById(R.id.card);

        }

    }
}