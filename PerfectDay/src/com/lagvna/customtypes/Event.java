package com.lagvna.customtypes;

import java.io.Serializable;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name = null;
	private String date = null;
	private String eventId = null;
	private String place = null;
	private String note = null;
	private String code = null;

	public Event() {

	}

	public Event(String eventId, String date, String name, String place,
			String note, String code) {
		this.setEventId(eventId);
		this.setName(name);
		this.setDate(date);
		this.setCode(code);
		this.setPlace(place);
		this.setNote(note);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
