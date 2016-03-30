package com.example.rishikesh.ireas.rest.model;

/**
 * Created by kartik on 22/10/15.
 */
public class StudentAttendance {

    private User student;
    private Boolean attendance;

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "StudentAttendance{" +
                "student=" + student +
                ", attendance=" + attendance +
                '}';
    }
}
