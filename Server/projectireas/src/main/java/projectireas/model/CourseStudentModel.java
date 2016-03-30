package projectireas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_student")
public class CourseStudentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "course_id", nullable = false)
	private Long courseId;

	@Column(name = "student_id", nullable = false)
	private Long studentId;

	@Column(name = "active", nullable = false)
	private Boolean active;

	public CourseStudentModel() {
		super();
	}

	public CourseStudentModel(Long courseId, Long studentId, Boolean active) {
		super();
		this.courseId = courseId;
		this.studentId = studentId;
		this.active = active;
	}

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
