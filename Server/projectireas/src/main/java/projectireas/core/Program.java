package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Program {

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;
	
	@JsonProperty
	private String code;

	@JsonProperty
	private Long departmentId;

	public Program() {
		super();
	}

	public Program(String name, String code, Long departmentId) {
		super();
		this.name = name;
		this.code = code;
		this.departmentId = departmentId;
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
