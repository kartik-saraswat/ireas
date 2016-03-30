package com.example.rishikesh.ireas;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rishikesh on 10/19/2015.
 */
public interface RestInterface {
    @GET("/users/{userId}")
    void getUser(@Path("userId") Long userId, Callback<UserIreas> user);
}
