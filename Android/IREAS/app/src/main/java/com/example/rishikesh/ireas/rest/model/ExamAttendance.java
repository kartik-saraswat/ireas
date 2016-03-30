package com.example.rishikesh.ireas.rest.model;

import java.util.List;

public class ExamAttendance {

    private Exam exam;

    private List<StudentAttendance> studentAttendanceList;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<StudentAttendance> getStudentAttendanceList() {
        return studentAttendanceList;
    }

    public void setStudentAttendanceList(List<StudentAttendance> studentAttendanceList) {
        this.studentAttendanceList = studentAttendanceList;
    }

    @Override
    public String toString() {
        return "ExamAttendance{" +
                "exam=" + exam +
                ", studentAttendanceList=" + studentAttendanceList +
                '}';
    }
}
