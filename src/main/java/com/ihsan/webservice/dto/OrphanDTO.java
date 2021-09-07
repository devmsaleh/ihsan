package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrphanDTO {

	private String id;

	private String name;

	private String birthDateStr;

	private String caseNumber;

	private String gender;

	private String genderId;

	private String nationality;

	private String familyNumber;

	private String fatherDeathReason;

	private String motherName;

	private BigDecimal caseAmount;

	private String sponsorFor;

	private String firstTitleId;

	private String firstTitleName;

	private Date birthDate;

	public OrphanDTO() {

	}

	public OrphanDTO(String id, String name, String birthDateStr, String caseNumber, String gender) {
		this.id = id;
		this.name = name;
		this.birthDateStr = birthDateStr;
		this.caseNumber = caseNumber;
		this.gender = gender;
	}

	public OrphanDTO(String id, String name, Date birthDate, String caseNumber, String genderId, BigDecimal caseAmount,
			String sponsorFor, String firstTitleId) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.caseNumber = caseNumber;
		this.genderId = genderId;
		this.caseAmount = caseAmount;
		this.sponsorFor = sponsorFor;
		this.firstTitleId = firstTitleId;
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

	public String getBirthDateStr() {
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFamilyNumber() {
		return familyNumber;
	}

	public void setFamilyNumber(String familyNumber) {
		this.familyNumber = familyNumber;
	}

	public String getFatherDeathReason() {
		return fatherDeathReason;
	}

	public void setFatherDeathReason(String fatherDeathReason) {
		this.fatherDeathReason = fatherDeathReason;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public BigDecimal getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(BigDecimal caseAmount) {
		this.caseAmount = caseAmount;
	}

	public String getSponsorFor() {
		return sponsorFor;
	}

	public void setSponsorFor(String sponsorFor) {
		this.sponsorFor = sponsorFor;
	}

	public String getFirstTitleId() {
		return firstTitleId;
	}

	public void setFirstTitleId(String firstTitleId) {
		this.firstTitleId = firstTitleId;
	}

	public String getFirstTitleName() {
		return firstTitleName;
	}

	public void setFirstTitleName(String firstTitleName) {
		this.firstTitleName = firstTitleName;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
