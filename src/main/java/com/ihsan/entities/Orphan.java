package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ihsan.util.GeneralUtils;

// case_status 3 متوقف
//  7 جاهز للكفالة
// 9 عقد جديد
@Entity
@Table(name = "POS_SP_CASES_V")
public class Orphan {

	@Id
	@Column(name = "CASE_ID")
	private BigInteger id;

	@Column(name = "CASENAME")
	private String name;

	@Column(name = "CASEPHOTO")
	private String photo;

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDATE")
	private Date birthDate;

	@Transient
	private String birthDateStr;

	@Column(name = "BIRTHLOCATION")
	private String birthLocation;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "FAMILYCOUNT")
	private Integer familyCount;

	@Column(name = "MOTHERNAME")
	private String motherName;

	@Temporal(TemporalType.DATE)
	@Column(name = "FATHERDEATHDATE")
	private Date fatherDeathDate;

	@Column(name = "CASE_NO")
	private String caseNumber;

	@Column(name = "NATIONALITY_NAME")
	private String nationalityName;

	@Column(name = "SPONCER_CATEGORY")
	private BigInteger sponsorshipTypeId;

	@Column(name = "FAMILY_ID")
	private BigInteger familyId;

	@Column(name = "GENDER_NAME")
	private String genderName;

	@Column(name = "CHECK_FLAG")
	private Boolean reserved;

	public Orphan() {

	}

	public Orphan(BigInteger id) {
		this.id = id;
	}

	public Boolean isReserved() {
		if (reserved == null) {
			reserved = false;
		}
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	public BigInteger getSponsorshipTypeId() {
		return sponsorshipTypeId;
	}

	public void setSponsorshipTypeId(BigInteger sponsorshipTypeId) {
		this.sponsorshipTypeId = sponsorshipTypeId;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthLocation() {
		return birthLocation;
	}

	public void setBirthLocation(String birthLocation) {
		this.birthLocation = birthLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getFamilyCount() {
		if (familyCount == null) {
			familyCount = 0;
		}
		return familyCount;
	}

	public void setFamilyCount(Integer familyCount) {
		this.familyCount = familyCount;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Date getFatherDeathDate() {
		return fatherDeathDate;
	}

	public void setFatherDeathDate(Date fatherDeathDate) {
		this.fatherDeathDate = fatherDeathDate;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public BigInteger getFamilyId() {
		return familyId;
	}

	public void setFamilyId(BigInteger familyId) {
		this.familyId = familyId;
	}

	public String getBirthDateStr() {
		birthDateStr = GeneralUtils.formatDate(birthDate);
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

}
