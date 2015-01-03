package com.lagvna.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wall implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String note;
	private String author;
	private String date;
	private List<Response> responses = new ArrayList<Response>();

	public Wall(long id, String note, String author, String date) {
		this.id = id;
		this.setNote(note);
		this.setAuthor(author);
		this.setDate(date);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
}