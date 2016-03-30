package com.example.rishikesh.ireas.rest.model;

/**
 * Created by kartik on 22/10/15.
 */
public class Notification {

    private Course course;
    private Exam exam;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "course=" + course +
                ", exam=" + exam +
                '}';
    }
}
