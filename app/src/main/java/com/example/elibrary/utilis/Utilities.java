package com.example.elibrary.utilis;

import android.content.SharedPreferences;

import com.example.elibrary.constants.AppConstants;
import com.example.elibrary.models.Login;
import com.google.gson.GsonBuilder;

import static com.example.elibrary.helpers.MyApplication.getSharedPreference;

public class Utilities {

    public static void saveLoginResponse(Login login){
        String json=new GsonBuilder().create().toJson(login);
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putString(AppConstants.LOGIN_RESPONSE,json);
        editor.apply();
        setIsFirstLogin(true);
    }







    public static  Login getLoginResponse(){
        String savedUserResponse= getSharedPreference().getString(AppConstants.LOGIN_RESPONSE,null);
        return  new GsonBuilder().create().fromJson(savedUserResponse,Login.class);
    }

    public static void setIsFirstLogin(boolean status){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putBoolean(AppConstants.IS_FIRST_LOGIN,status);
        editor.apply();
    }

    public static boolean isLogin(){
        return getSharedPreference().getBoolean(AppConstants.IS_FIRST_LOGIN, false);
    }


    public static void saveUserTokenLogin(String token) {
    }
}
