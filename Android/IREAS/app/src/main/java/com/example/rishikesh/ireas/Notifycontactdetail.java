package com.example.rishikesh.ireas;

/**
 * Created by rishikesh on 10/17/2015.
 */

public class Notifycontactdetail {
    public String name;
    public String exam_name;
    public String exam_code;
    public String time;
    public String roomno;

    public Notifycontactdetail(String name, String exam_name, String exam_code,String time,String roomno) {
        this.name = name;
        this.exam_name = exam_name;
        this.exam_code = exam_code;
        this.time=time;
        this.roomno=roomno;
    }
    public String getName() { return name; }

    public String getExam_name() {return exam_name;}
    public String getExam_code() {
        return exam_code;
    }

    public String getTime() {return time;}
    public String getRoomno() {return roomno;}



}