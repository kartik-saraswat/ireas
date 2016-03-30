package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Person {

	@JsonProperty
	private long id;

	@JsonProperty
	private String fullName;

	@JsonProperty
	private String jobTitle;

	public Person() {
	}

	public Person(String fullName, String jobTitle) {
		this.fullName = fullName;
		this.jobTitle = jobTitle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	 @Override
	    public String toString() {
	        return MoreObjects.toStringHelper(this)
	                .add("id", id)
	                .add("fullName", fullName)
	                .add("jobTitle", jobTitle)
	                .toString();
	    }
}
