package com.example.elisp.valuti.api;

import android.content.Context;

import com.example.elisp.valuti.klasi.Valuti;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApi {
    Context activity;

    public static final int requste_max_time_in_seconds = 20;

    public RestApi(Context activity) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstanceO(){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .readTimeout(requste_max_time_in_seconds, TimeUnit.SECONDS)
        .connectTimeout(requste_max_time_in_seconds, TimeUnit.SECONDS).build();


        return new Retrofit.Builder().baseUrl(ApiConstants.baseURL).addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
    }

    public ApiService request(){return  getRetrofitInstanceO().create(ApiService.class);}

    public  Call<ArrayList<Valuti>> getBtc ( String btc) {return  request().getBtc(  btc);}

    public Call<ArrayList<Valuti>> getCoins(String convert, int limit)
    {
        return request().getCoins(convert,limit);
    }




}
