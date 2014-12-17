package com.jun.xiaoquren.dao.model;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = 5599048491265884538L;
	
	private Integer id = null;
	private String name;

	public City() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public String getStringId() {
		return String.valueOf(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
