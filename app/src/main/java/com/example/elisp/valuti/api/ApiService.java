package com.example.elisp.valuti.api;

import com.example.elisp.valuti.klasi.Valuti;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("ticker")
    Call<ArrayList<Valuti>> getCoins(@Query("convert") String convert,@Query("limit") int limit);

    @GET("ticker/{id}")
    Call<ArrayList<Valuti>> getBtc(@Path ("id") String id);



}
