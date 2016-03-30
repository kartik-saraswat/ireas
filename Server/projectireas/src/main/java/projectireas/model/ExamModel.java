package projectireas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

@Entity
@Table(name = "exam")
public class ExamModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "course_id", nullable = false)
	private Long courseId;

	@Column(name = "invigilator_id", nullable = false)
	private Long invigilatorId;

	@Column(name = "exam_start_time", nullable = false)
	private DateTime examStartTime;

	@Column(name = "exam_type_id", nullable = false)
	private Long examTypeId;

	@Column(name = "room", nullable = false)
	private String room;

	public ExamModel(Long courseId, Long invigilatorId, DateTime examStartTime, Long examTypeId, String room) {
		super();
		this.courseId = courseId;
		this.invigilatorId = invigilatorId;
		this.examStartTime = examStartTime;
		this.examTypeId = examTypeId;
		this.room = room;
	}

	public ExamModel() {
		super();
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

	public Long getInvigilatorId() {
		return invigilatorId;
	}

	public void setInvigilatorId(Long invigilatorId) {
		this.invigilatorId = invigilatorId;
	}

	public DateTime getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(DateTime examStartTime) {
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


}
