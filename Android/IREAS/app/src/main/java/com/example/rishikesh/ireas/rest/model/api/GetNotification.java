package com.example.rishikesh.ireas.rest.model.api;

import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.NotificationList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GetNotification   implements Callback<NotificationList> {

    TextView textView;

    public GetNotification(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onResponse(Response<NotificationList> response, Retrofit retrofit) {

        try {
            textView.setText("Notifications are :-\n "+response.body().toString());
        } catch (Exception e){
            textView.setText("Notifications Not Found");
        }
    }

    @Override
    public void onFailure(Throwable t) {
        textView.setText("GetNotifications Error Is : "+t.getLocalizedMessage());
    }
}
