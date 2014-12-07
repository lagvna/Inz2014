package com.lagvna.customtypes;

import java.io.Serializable;

public class Guest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String email;
	private String telephone;
	private String id;

	public Guest(String name, String surname, String email, String telephone,
			String id) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
