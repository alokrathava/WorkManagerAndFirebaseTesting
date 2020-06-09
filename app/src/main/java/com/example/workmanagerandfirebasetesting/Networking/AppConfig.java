package com.example.workmanagerandfirebasetesting.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {


   private static String BASE_URL = "http://192.168.0.112/";

    public static Retrofit getRetrofit() {


        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
