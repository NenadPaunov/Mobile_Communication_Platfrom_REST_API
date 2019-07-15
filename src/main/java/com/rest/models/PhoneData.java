package com.rest.models;

public class PhoneData {
	private String message_type;
	private long timestamp;
	private long origin;
	private long destination;

	public PhoneData() {

	}

	public PhoneData(String message_type, long timestamp, long origin, long destination) {
		super();
		this.message_type = message_type;
		this.timestamp = timestamp;
		this.origin = origin;
		this.destination = destination;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getOrigin() {
		return origin;
	}

	public void setOrigin(long origin) {
		this.origin = origin;
	}

	public long getDestination() {
		return destination;
	}

	public void setDestination(long destination) {
		this.destination = destination;
	}

}
