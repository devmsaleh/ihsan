package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TM_REGION")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGION_SEQ")
	@SequenceGenerator(sequenceName = "REGION_SEQ", allocationSize = 1, name = "REGION_SEQ")
	@Column(name = "TM_REGION_ID")
	private BigInteger id;

	@Column(name = "REGION_NAME")
	private String name;

	@Column(name = "TM_CITY_ID")
	private BigInteger emarahId;

	@Transient
	private boolean alreadyExist;

	public BigInteger getEmarahId() {
		return emarahId;
	}

	public void setEmarahId(BigInteger emarahId) {
		this.emarahId = emarahId;
	}

	public Region() {

	}

	public Region(String name, BigInteger emarahId) {
		this.name = name;
		this.emarahId = emarahId;
	}

	public Region(BigInteger id) {
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

	public boolean isAlreadyExist() {
		return alreadyExist;
	}

	public void setAlreadyExist(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
	}

}
