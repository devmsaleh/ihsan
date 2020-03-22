package com.ihsan.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_SP_SPONSOR_COUNTRIES_V")
public class SponsorshipCountry {

	@Id
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "NAME")
	private String name;

	public SponsorshipCountry() {

	}

	public SponsorshipCountry(BigInteger id) {
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
