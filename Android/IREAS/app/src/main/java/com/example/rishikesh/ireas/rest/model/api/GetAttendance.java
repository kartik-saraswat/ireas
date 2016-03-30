package com.example.rishikesh.ireas.rest.model.api;

import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.ExamAttendance;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by kartik on 22/10/15.
 */
public class GetAttendance  implements Callback<ExamAttendance> {

    TextView textView;

    public GetAttendance(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onResponse(Response<ExamAttendance> response, Retrofit retrofit) {

        try {
            textView.setText("Exam Attendance : "+response.body().toString());
        } catch (Exception e){
            textView.setText("Exam Attendance Not Found");
        }
    }

    @Override
    public void onFailure(Throwable t) {
        textView.setText("Error Is : "+t.getLocalizedMessage());
    }
}