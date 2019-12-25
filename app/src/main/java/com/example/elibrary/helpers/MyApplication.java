package com.example.elibrary.helpers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by ACIS SAPKOTA on 9/19/2019.
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static MyApplication myApplication;
    private static SharedPreferences sharedPreferences;
    private static boolean isActive;

    public void onCreate() {

        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        myApplication = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());

    }

    public static boolean isActivityVisible() {
        return isActive;
    }

    public static Context getAppContext() {
        return myApplication.getApplicationContext();
    }

    public static SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
