package com.example.rishikesh.ireas.rest.model.api;

import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.User;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kartik on 22/10/15.
 */
public class GetUser implements Callback<User>{

    TextView textView;

    public GetUser(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {

        try {
            textView.setText(response.body().toString());
        } catch (Exception e){
            textView.setText("User Not Found");
        }
    }

    @Override
    public void onFailure(Throwable t) {
        textView.setText(t.getLocalizedMessage());
    }
}
