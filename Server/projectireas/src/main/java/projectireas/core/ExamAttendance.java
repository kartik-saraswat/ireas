package projectireas.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamAttendance {
	
	@JsonProperty
	private Exam exam;
    
	@JsonProperty
	private List<StudentAttendance> studentAttendanceList;

	public ExamAttendance() {
		super();
	}

	public ExamAttendance(Exam exam, List<StudentAttendance> studentAttendanceList) {
		super();
		this.exam = exam;
		this.studentAttendanceList = studentAttendanceList;
	}

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
}
