package com.example.rishikesh.ireas;

import android.app.Application;

import com.example.rishikesh.ireas.rest.model.Exam;
import com.example.rishikesh.ireas.rest.model.User;
import com.example.rishikesh.ireas.rest.service.RestService;

public class IreasApplication extends Application {

    private RestService service = new RestClient().getService();
    private User userLoggedIn = null;
    private Exam currentExam = null;


    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public RestService getService() {
        return service;
    }

    public Exam getCurrentExam() {
        return currentExam;
    }

    public void setCurrentExam(Exam currentExam) {
        this.currentExam = currentExam;
    }
}
