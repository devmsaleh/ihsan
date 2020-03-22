package com.ihsan.entities;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_CP_PROJECTS_V")
public class OldProject {

	@Id
	@Column(name = "PROJECT_ID")
	private BigInteger id;

	@Column(name = "PROJECT_NAME")
	private String name;

	@Column(name = "PROJECT_DESC")
	private String description;

	@Column(name = "COST")
	private BigDecimal cost;

	@Column(name = "COUNTRY_ID")
	private BigInteger countryId;

	@Column(name = "PROJECT_TYPE")
	private String projectCategoryId;

	public OldProject() {

	}

	public OldProject(BigInteger id) {
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

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}

	public String getProjectCategoryId() {
		return projectCategoryId;
	}

	public void setProjectCategoryId(String projectCategoryId) {
		this.projectCategoryId = projectCategoryId;
	}

	@Override
	public String toString() {
		return "OldProject [id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost
				+ ", countryId=" + countryId + ", projectCategoryId=" + projectCategoryId + "]";
	}

}
