package projectireas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attendance")
public class AttendanceModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "exam_id", nullable = false)
	private Long examId;

	@Column(name = "student_id", nullable = false)
	private Long studentId;

	@Column(name = "attendance", nullable = false)
	private Boolean attendance;

	public AttendanceModel(Long examId, Long studentId, Boolean attendance) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.attendance = attendance;
	}

	public AttendanceModel() {
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
