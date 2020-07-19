package com.example.apotek.RetrofitUtils;

import com.example.apotek.Model.Defaultresponse;
import com.example.apotek.Model.LoginResponse;
import com.example.apotek.Model.Obat;
import com.example.apotek.Model.Pesan;
import com.example.apotek.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApotekApi {

    @GET("user")
    Call<List<User>>getUser(@Query("id") int id);

    @GET("pesan")
    Call<List<Pesan>>getStatus();

    @GET("pesan")
    Call<List<Pesan>>getpesan(@Query("id_user") int id_user);

    @GET("obat")
    Call<List<Obat>> getObat();

    @FormUrlEncoded
    @POST("daftar")
    Call<Defaultresponse> daftar(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telp") String telp,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("pesan")
    Call<Defaultresponse> order(
            @Field("nama_obat") String nama_obat,
            @Field("jumlah") int jumlah,
            @Field("harga") int harga,
            @Field("tanggal") String tanggal,
            @Field("id_user") int id_user
    );

    @FormUrlEncoded
    @PUT("updateUser")
    Call<LoginResponse> upadateUser(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telp") String telp,
            @Field("username") String username
    );
    @FormUrlEncoded
    @POST("checkout")
    Call<Defaultresponse> Uploadcheckout(
            @Field("id")int id,
            @Field("checkout")String checkout
    );
}
