package com.ihsan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_CP_PROJECTS_COUNTRIES_V")
public class NewProjectCountry {

	@Id
	@Column(name = "COUNTRY_ID")
	private String id;

	@Column(name = "COUNTRY_NAME")
	private String name;

	public NewProjectCountry() {

	}

	public NewProjectCountry(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
