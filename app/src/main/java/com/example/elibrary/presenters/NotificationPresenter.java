package com.example.elibrary.presenters;

import com.example.elibrary.apiservices.ApiClient;
import com.example.elibrary.apiservices.NotificationApiService;
import com.example.elibrary.apiservices.SearchApiService;
import com.example.elibrary.models.Notification;
import com.example.elibrary.models.Search;
import com.example.elibrary.utilis.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ACIS SAPKOTA on 9/22/2019.
 */
public class NotificationPresenter {
    private WeakReference<View> view;

    public NotificationPresenter(NotificationPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private NotificationPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    public interface View {
        void onNotificationResponseSuccess(Notification notification);
        void onNotificationResponseFailure(String message);

    }

    public  void notification(){

        NotificationApiService notificationApiService=ApiClient.getClient().create(NotificationApiService.class);
        notificationApiService.getNotification().enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(getView()!=null){
                            getView().onNotificationResponseSuccess(response.body());
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

                getView().onNotificationResponseFailure(UtilitiesFunctions.handleApiError(t));

            }
        });



    }
}
