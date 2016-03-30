package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {

	@JsonProperty
	private Course course;
	
	@JsonProperty
	private Exam exam;

	public Notification() {
		super();
	}

	public Notification( Course course, Exam exam) {
		super();
		this.course = course;
		this.exam = exam;
	}
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
}
