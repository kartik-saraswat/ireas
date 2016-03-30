package com.example.rishikesh.ireas.rest.model;

/**
 * Created by kartik on 22/10/15.
 */
import java.io.Serializable;
import java.util.Date;

public class Exam implements Serializable{
    private Long id;
    private Long courseId;
    private Long invigilatorId;
    private Date examStartTime;
    private Long examTypeId;
    private String room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getInvigilatorId() {
        return invigilatorId;
    }

    public void setInvigilatorId(Long invigilatorId) {
        this.invigilatorId = invigilatorId;
    }

    public Date getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }

    public Long getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(Long examTypeId) {
        this.examTypeId = examTypeId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", invigilatorId=" + invigilatorId +
                ", examStartTime=" + examStartTime +
                ", examTypeId=" + examTypeId +
                ", room='" + room + '\'' +
                '}';
    }
}
