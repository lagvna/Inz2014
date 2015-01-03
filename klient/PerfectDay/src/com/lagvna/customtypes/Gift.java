package com.lagvna.customtypes;

import java.io.Serializable;

public class Gift implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String link;
	private String shop;
	private String description;
	private String buyer;
	private String id;

	public Gift(String name, String link, String shop, String description,
			String buyer, String id) {
		this.name = name;
		this.link = link;
		this.shop = shop;
		this.description = description;
		this.buyer = buyer;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
