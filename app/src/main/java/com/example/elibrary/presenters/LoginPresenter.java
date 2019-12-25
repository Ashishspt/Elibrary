package com.example.elibrary.presenters;

import android.util.Log;

import com.example.elibrary.apiservices.ApiClient;
import com.example.elibrary.apiservices.LoginApiService;
import com.example.elibrary.helpers.ShowToast;
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
public class LoginPresenter {
    private WeakReference<View> view;

    public LoginPresenter(LoginPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private LoginPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    public interface View {
        void onLoginResponseSuccess(Login login);
        void onLoginResponseFailure(String message);

    }

    public  void userLogin(String email,String password){
        LoginApiService loginApiService= ApiClient.getClient().create(LoginApiService.class);
        loginApiService.login(email,password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        Log.e( "onResponse :new", new GsonBuilder().create().toJson(response.body()))  ;
                        Utilities.saveLoginResponse(response.body());
                        getView().onLoginResponseSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                ShowToast.withLongMessage("UtilitiesFunctions.handleApiError(t)");
                Log.e( "onFailure: ", new GsonBuilder().create().toJson(t));
                if(getView()!=null){
                    getView().onLoginResponseFailure("Something went Wrong");
                }



            }
        });
    }
}
