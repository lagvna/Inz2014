package com.lagvna.customtypes;

import java.io.Serializable;

public class Note implements Serializable {
	private static final long serialVersionUID = 1L;
	private String description;
	private String isDone;
	private String id;

	public Note(String id, String isDone, String description) {
		this.setId(id);
		this.setIsDone(isDone);
		this.setDescription(description);
	}

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
