package com.example.elibrary.apiservices;

import com.example.elibrary.models.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public interface LoginApiService {
    @POST("login")
    @FormUrlEncoded
    Call<Login> login(
            @Field("email") String email,
            @Field("password") String password
    );



}
