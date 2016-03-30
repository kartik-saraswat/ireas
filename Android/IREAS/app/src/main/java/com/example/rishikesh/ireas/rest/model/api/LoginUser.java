package com.example.rishikesh.ireas.rest.model.api;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.rishikesh.ireas.Login;
import com.example.rishikesh.ireas.Notification;
import com.example.rishikesh.ireas.rest.model.User;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kartik on 22/10/15.
 */
public class LoginUser  implements Callback<User> {

    TextView textView;
    Context packageContext;

    public LoginUser(TextView textView,  Context packageContext) {
        this.textView = textView;
        this.packageContext = packageContext;
    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {


    }

    @Override
    public void onFailure(Throwable t) {
        textView.setText("Error Is : "+t.getLocalizedMessage());
    }
}