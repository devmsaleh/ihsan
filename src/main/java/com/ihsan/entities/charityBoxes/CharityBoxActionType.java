package com.ihsan.entities.charityBoxes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "FND_LOOKUP_VALUES")
@Where(clause = "LOOKUP_TYPE='CHARITY_BOX_ACTION_TYPE'")
public class CharityBoxActionType {

	@Id
	@Column(name = "LOOKUP_CODE")
	private String id;

	@Column(name = "ARABIC_VALUE")
	private String name;

	public CharityBoxActionType() {

	}

	public CharityBoxActionType(String id) {
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
