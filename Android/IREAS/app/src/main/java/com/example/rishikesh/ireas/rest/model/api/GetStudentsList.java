package com.example.rishikesh.ireas.rest.model.api;

import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GetStudentsList   implements Callback<List<User>> {

    TextView textView;

    public GetStudentsList(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onResponse(Response<List<User>> response, Retrofit retrofit) {

        try {
            textView.setText("Students List Is : "+response.body().toString());
        } catch (Exception e){
            textView.setText("Students Not Found");
        }
    }

    @Override
    public void onFailure(Throwable t) {
        textView.setText("Error Is : "+t.getLocalizedMessage());
    }
}