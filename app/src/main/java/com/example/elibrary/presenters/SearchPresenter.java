package com.example.elibrary.presenters;

import android.util.Log;

import com.example.elibrary.apiservices.ApiClient;
import com.example.elibrary.apiservices.LoginApiService;
import com.example.elibrary.apiservices.SearchApiService;
import com.example.elibrary.models.Login;
import com.example.elibrary.models.Search;
import com.example.elibrary.utilis.Utilities;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public class SearchPresenter {
    private WeakReference<View> view;

    public SearchPresenter(SearchPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private SearchPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    public interface View {
        void onSearchResponseSuccess(Search search);
        void onSearchResponseFailure(String message);

    }

    public  void searchBook(String bookname){

        SearchApiService searchApiService=ApiClient.getClient().create(SearchApiService.class);
        searchApiService.search(bookname).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        getView().onSearchResponseSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                getView().onSearchResponseFailure("something Went Wrong");

            }
        });

    }
}
