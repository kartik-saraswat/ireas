package com.example.rishikesh.ireas.rest.service;

import com.example.rishikesh.ireas.rest.model.ExamAttendance;
import com.example.rishikesh.ireas.rest.model.FaceResult;
import com.example.rishikesh.ireas.rest.model.NotificationList;
import com.example.rishikesh.ireas.rest.model.SMat;
import com.example.rishikesh.ireas.rest.model.StackOverflowQuestions;
import com.example.rishikesh.ireas.rest.model.User;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RestService {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);

    @GET("/user/{userId}")
    Call<User> getUser(@Path("userId") Long userId);

    @POST("/login")
    Call<User> loginUser(@Body User user);

    @GET("/notifications/{userId}")
    Call<NotificationList> getNotification(@Path("userId") Long userId);

    @GET("/exam/{examId}/students")
    Call<ExamAttendance> getStudentsList(@Path("examId") Long examId);

    @GET("/attendance/exam/{examId}")
    Call<ExamAttendance> getAttendance(@Path("examId") Long examId);

    @POST("/check/faceImage")
    Call<FaceResult> checkFace(@Body SMat sMat);

    @POST("/check/matchFaceImage")
    Call<FaceResult> matchFace(@Body SMat sMat);
}