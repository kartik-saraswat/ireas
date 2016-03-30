package projectireas.core;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Exam {

	@JsonProperty
	private Long id;

	@JsonProperty
	private Long courseId;

	@JsonProperty
	private Long invigilatorId;

	@JsonProperty
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyyMMddHHmm")
	private DateTime examStartTime;

	@JsonProperty
	private Long examTypeId;

	@JsonProperty
	private String room;

	public Exam(Long courseId, Long invigilatorId, DateTime examStartTime, Long examTypeId, String room) {
		super();
		this.courseId = courseId;
		this.invigilatorId = invigilatorId;
		this.examStartTime = examStartTime;
		this.examTypeId = examTypeId;
		this.room = room;
	}

	public Exam() {
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