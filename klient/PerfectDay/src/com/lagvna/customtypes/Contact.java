package com.lagvna.customtypes;

import java.io.Serializable;

public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String sector;
	private String telephone;
	private String email;
	private String note;
	private String id;

	public Contact(String id, String name, String sector, String email,
			String telephone, String note) {
		this.setId(id);
		this.setName(name);
		this.setSector(sector);
		this.setTelephone(telephone);
		this.setEmail(email);
		this.setNote(note);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
