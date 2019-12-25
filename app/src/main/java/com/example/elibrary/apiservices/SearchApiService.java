package com.example.elibrary.apiservices;

import com.example.elibrary.models.Login;
import com.example.elibrary.models.Search;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public interface SearchApiService {
    @POST("searchlist")
    @FormUrlEncoded
    Call<Search> search(
            @Field("name") String bookname

    );



}
