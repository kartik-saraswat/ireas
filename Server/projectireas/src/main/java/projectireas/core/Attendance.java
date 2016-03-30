package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attendance {

	@JsonProperty
	private Long id;

	@JsonProperty
	private Long examId;

	@JsonProperty
	private Long studentId;

	@JsonProperty
	private Boolean attendance;

	public Attendance(Long examId, Long studentId, Boolean attendance) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.attendance = attendance;
	}

	public Attendance() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Boolean getAttendance() {
		return attendance;
	}

	public void setAttendance(Boolean attendance) {
		this.attendance = attendance;
	}
}
