package com.example.apotek.RetrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String base_url ="http://192.168.43.83/Apotek/api/";
    private Retrofit retrofit;
    private static RetrofitClient instance;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (instance == null)
        {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApotekApi getApi(){
        return retrofit.create(ApotekApi.class);
    }
}
