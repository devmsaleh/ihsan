package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TM_CHARITY_BOXES_TYPES")
public class CharityBoxType {

	@Id
	@Column(name = "TYPE_ID")
	private BigInteger id;

	@Column(name = "CHARITY_BOX_TYPE")
	private String name;

	public CharityBoxType() {

	}

	public CharityBoxType(BigInteger id) {
		this.id = id;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
