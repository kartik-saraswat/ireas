package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department {
	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private String code;

	public Department(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public Department() {
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
}
