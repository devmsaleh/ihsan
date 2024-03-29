package com.ihsan.entities;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_CP_PROJECT_CATEGORIES_V")
public class ProjectStudy {

	@Id
	@Column(name = "PROJECT_TYPE_ID")
	private BigInteger id;

	@Column(name = "PROJECT_NAME")
	private String name;

	@Column(name = "PROJECT_DESC")
	private String description;

	@Column(name = "PROJECT_COST")
	private BigDecimal cost;

	@Column(name = "COUNTRY_ID")
	private String countryId;

	@Column(name = "PROJECT_TYPE")
	private BigInteger projectTypeId;

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public ProjectStudy() {

	}

	public ProjectStudy(BigInteger id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigInteger getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(BigInteger projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

}
