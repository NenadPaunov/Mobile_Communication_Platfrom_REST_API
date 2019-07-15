package com.rest.models;

public class MSG extends PhoneData {
	private String message_content;
	private String message_status;

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_status() {
		return message_status;
	}

	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}

	public MSG() {

	}

	public MSG(String message_content, String message_status) {
		super();
		this.message_content = message_content;
		this.message_status = message_status;
	}

}
