package com.ihsan.entities.charityBoxes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "FND_LOOKUP_VALUES")
@Where(clause = "LOOKUP_TYPE='CATEGORY_OF_BOXES'")
public class CharityBoxCategory {

	@Id
	@Column(name = "LOOKUP_CODE")
	private String id;

	@Column(name = "ARABIC_VALUE")
	private String name;

	@Column(name = "LOOKUP_TYPE")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CharityBoxCategory() {

	}

	public CharityBoxCategory(String id) {
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
