package com.rest.models;

public class Call extends PhoneData {
	private long duration;
	private String status_code;
	private String status_description;

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public Call() {

	}

	public Call(long duration, String status_code, String status_description) {
		super();
		this.duration = duration;
		this.status_code = status_code;
		this.status_description = status_description;
	}

}
