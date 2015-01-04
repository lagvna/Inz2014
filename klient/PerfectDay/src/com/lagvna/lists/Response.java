package com.lagvna.lists;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String note;
	private String author;
	private String date;
	private String wallId;

	public Response(String id, String note, String author, String date,
			String wallId) {
		this.id = id;
		this.setNote(note);
		this.setAuthor(author);
		this.setDate(date);
		this.setWallId(wallId);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWallId() {
		return wallId;
	}

	public void setWallId(String wallId) {
		this.wallId = wallId;
	}

}