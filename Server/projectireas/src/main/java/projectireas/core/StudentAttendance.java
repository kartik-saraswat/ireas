package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentAttendance {

	@JsonProperty
	private User student;

	@JsonProperty
	private Boolean attendance;

	public StudentAttendance() {
		super();
	}

	public StudentAttendance(User student, Boolean attendance) {
		super();
		this.student = student;
		this.attendance = attendance;
	}

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


}
