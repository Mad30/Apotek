package com.example.apotek.Model;

public class Pesan {

    private   String nama_obat,tanggal,status,image;

    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getSelected() {
        return isSelected;
    }



    public String getImage() {
        return image;
    }

    private  int id,id_user,jumlah,harga;

    public String getStatus() {
        return status;
    }

    public String getNama_obat() {
        return nama_obat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getHarga() {
        return harga;
    }
}
