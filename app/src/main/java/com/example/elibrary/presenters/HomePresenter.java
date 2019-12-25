package com.example.elibrary.presenters;

import com.example.elibrary.apiservices.ApiClient;
import com.example.elibrary.apiservices.HomeApiService;
import com.example.elibrary.apiservices.NotificationApiService;
import com.example.elibrary.helpers.ShowToast;
import com.example.elibrary.models.Home;
import com.example.elibrary.models.Notification;
import com.example.elibrary.utilis.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public class HomePresenter {
    private WeakReference<View> view;

    public HomePresenter(HomePresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private HomePresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    public interface View {
        void onHomeResponseSuccess(Home home);
        void onHomeResponseFailure(String message);

    }

    public  void home(){
        HomeApiService homeApiService=ApiClient.getClient().create(HomeApiService.class);
        homeApiService.getAllBooks().enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {
                if(response.isSuccessful()){
                    if(getView()!=null){
                        getView().onHomeResponseSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                ShowToast.withLongMessage("NO Internet Connection.\nTry Again");
               if(getView()!=null){
                   getView().onHomeResponseFailure(UtilitiesFunctions.handleApiError(t));
               }

            }
        });





    }
}
