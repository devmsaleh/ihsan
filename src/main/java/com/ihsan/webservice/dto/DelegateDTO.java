package com.ihsan.webservice.dto;

import java.math.BigDecimal;

public class DelegateDTO {

	private String id;
	private String number;
	private String name;
	private String userName;
	private String expiryDate;
	private BigDecimal maxLimit;
	private boolean admin;
	private String startDate;
	private String token;
	private String pinCode = "1111";
	private String password;
	private boolean supervisor;
	private String mobile;
	private BigDecimal amountNotCollected = new BigDecimal(0);
	private boolean updateCoupons;
	private boolean charityBox;
	private boolean coupon;
	private boolean sponsorship;
	private boolean project;
	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal depositAmount = new BigDecimal(0);

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
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

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSupervisor() {
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

	public BigDecimal getAmountNotCollected() {
		return amountNotCollected;
	}

	public void setAmountNotCollected(BigDecimal amountNotCollected) {
		this.amountNotCollected = amountNotCollected;
	}

	public boolean isCharityBox() {
		return charityBox;
	}

	public void setCharityBox(boolean charityBox) {
		this.charityBox = charityBox;
	}

	public boolean isCoupon() {
		return coupon;
	}

	public void setCoupon(boolean coupon) {
		this.coupon = coupon;
	}

	public boolean isUpdateCoupons() {
		return updateCoupons;
	}

	public void setUpdateCoupons(boolean updateCoupons) {
		this.updateCoupons = updateCoupons;
	}

	public boolean isSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(boolean sponsorship) {
		this.sponsorship = sponsorship;
	}

	public boolean isProject() {
		return project;
	}

	public void setProject(boolean project) {
		this.project = project;
	}

}
