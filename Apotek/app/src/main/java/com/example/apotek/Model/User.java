package com.example.apotek.Model;

public class User {

    private String nama,alamat,telp,username;
    private int id;

    public User(String nama, String alamat, String telp, String username, int id) {
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.username = username;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelp() {
        return telp;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
