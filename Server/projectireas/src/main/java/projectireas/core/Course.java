package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private String code;

	@JsonProperty
	private Long programId;

	public Course(String name, String code, Long programId) {
		super();
		this.name = name;
		this.code = code;
		this.programId = programId;
	}

	public Course() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}


}
