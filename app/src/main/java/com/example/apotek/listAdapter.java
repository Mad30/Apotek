package com.example.apotek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.apotek.Activity.Checkout;
import com.example.apotek.Activity.StatusActivity;
import com.example.apotek.Model.Obat;
import com.example.apotek.Model.Pesan;

import java.util.List;

public class listAdapter extends ArrayAdapter {


    private Context ctx;
    private int res;
   private List<Pesan> pesanList;

    public listAdapter(@NonNull Context context, int resource,List<Pesan> pesanList ) {
        super(context,resource, R.id.texname, pesanList);

        this.pesanList = pesanList;
        ctx = context;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_status,parent,false);

        }

        TextView tx1,tx2,tx3,tx4;
        LinearLayout layout;
        Pesan pesan = pesanList.get(position);
        tx1 = convertView.findViewById(R.id.texname);
        tx2 = convertView.findViewById(R.id.txttgl);
        tx3 = convertView.findViewById(R.id.textharga);
        tx4 = convertView.findViewById(R.id.textjumlah);
        layout = convertView.findViewById(R.id.layout);


        tx1.setText("Nama obat: "+pesan.getNama_obat());
        tx2.setText("Tanggal: "+pesan.getTanggal());
        tx3.setText("Harga: "+"Rp"+pesan.getHarga());
        tx4.setText("Jumlah: "+pesan.getJumlah());
        String nama = pesan.getNama_obat();
        String id = String.valueOf(pesan.getId());
        String jml = String.valueOf(pesan.getJumlah());
        String harga = String.valueOf(pesan.getHarga());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getContext(),Checkout.class);
               i.putExtra("nama",nama);
               i.putExtra("jumlah",jml);
               i.putExtra("harga",harga);
               i.putExtra("id",id);
               getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
