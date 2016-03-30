package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private String role;

	@JsonProperty
	private String email;

	@JsonProperty
	private String password;

	public User() {
		super();
	}

	public User(String name, String role, String email, String password) {
		super();
		this.name = name;
		this.role = role;
		this.email = email;
		this.password = password;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}