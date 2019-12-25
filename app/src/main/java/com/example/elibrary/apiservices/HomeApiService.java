package com.example.elibrary.apiservices;

import com.example.elibrary.models.Home;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ACIS SAPKOTA on 10/20/2019.
 */
public interface HomeApiService {
    @GET("home")
    Call<Home> getAllBooks();


}
