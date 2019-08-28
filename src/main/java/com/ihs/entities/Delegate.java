package com.ihs.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "TM_DELEGATES")
public class Delegate {

	@Id
	@Column(name = "DELEGATE_ID", nullable = false)
	private String id;
	@Column(name = "DELEGATE_NUMBER")
	private String number;
	@Column(name = "DELEGATE_NAME")
	private String name;
	@Column(name = "STATUS")
	private String status = "I"; // I or A
	@Column(name = "USER_NAME", columnDefinition = "nvarchar2 (100)")
	private String userName;
	@Column(name = "USER_PWD", columnDefinition = "nvarchar2 (100)")
	private String password;
	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;
	@Column(name = "MAX_LIMIT")
	private String maxLimit;
	@Transient
	private boolean admin;
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	@Transient
	private String pinCode;
	@Transient
	private Integer category;
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "BANK_ACCOUNT_ID")
	private BigInteger bankAccountId;

	@Transient
	private boolean supervisor;

	@Transient
	private String token;

	@Transient
	private String idStr;

	public String getIdStr() {
		idStr = id.toString();
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public Delegate() {

	}

	public Delegate(String id) {
		this.id = id;
	}

	public String getStatus() {
		if (StringUtils.isBlank(status)) {
			status = "I";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getMaxLimit() {
		if (StringUtils.isBlank(maxLimit)) {
			maxLimit = "0";
		}
		return maxLimit;
	}

	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public boolean isSupervisor() {
		supervisor = category != null && category == 2;
		return supervisor;
	}

	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigInteger getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BigInteger bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

}
