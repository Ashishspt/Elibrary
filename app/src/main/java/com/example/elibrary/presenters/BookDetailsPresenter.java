package com.example.elibrary.presenters;

import android.util.Log;

import com.example.elibrary.apiservices.ApiClient;
import com.example.elibrary.apiservices.BookDetailsApiService;
import com.example.elibrary.apiservices.LoginApiService;
import com.example.elibrary.models.BookDetails;
import com.example.elibrary.models.Login;
import com.example.elibrary.utilis.Utilities;
import com.example.elibrary.utilis.UtilitiesFunctions;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public class BookDetailsPresenter {
    private WeakReference<View> view;

    public BookDetailsPresenter(BookDetailsPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private BookDetailsPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    public interface View {
        void onBookDetailsResponseSuccess(BookDetails bookDetails);
        void onBookDetailsResponseFailure(String message);

    }

    public  void  bookDetails(String bookid){
        BookDetailsApiService bookDetailsApiService=ApiClient.getClient().create(BookDetailsApiService.class);
        bookDetailsApiService.getBooksDetails(bookid).enqueue(new Callback<BookDetails>() {
            @Override
            public void onResponse(Call<BookDetails> call, Response<BookDetails> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(getView()!=null){
                            getView().onBookDetailsResponseSuccess(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BookDetails> call, Throwable t) {
                getView().onBookDetailsResponseFailure(UtilitiesFunctions.handleApiError(t));

            }
        });
    }

    }

