package com.example.elibrary.apiservices;

import com.example.elibrary.models.BookDetails;
import com.example.elibrary.models.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ACIS SAPKOTA on 9/25/2019.
 */
public interface BookDetailsApiService {
    @GET("book/{id}")
    Call<BookDetails> getBooksDetails(
            @Path("id") String bookId);


}
