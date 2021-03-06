package com.example.lukakaic.equipmentmaintenance.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lukakaic on 1/11/18.
 */

public class ApiClient {
    // stari URL koji se koristio pri izradi projekta
    //public static final String BASE_URL = "http://159.89.108.135/api/";
    // novi URL nakon sto je server deployan
    public static final String BASE_URL = "http://161.53.19.119:8080";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
