package com.example.rishikesh.ireas;

import com.example.rishikesh.ireas.rest.service.RestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RestClient {

    private static final String BASE_URL = "http://10.42.0.1:8080";

    private RestService service;
    public RestClient() {

        Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmm").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(RestService.class);
     }

    public RestService getService(){
        return service;
    }
}
