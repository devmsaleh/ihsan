package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "TM_PROJECTS") // الكوبونات مثل صدقة عامة او رعاية ايتام
public class CouponType {

	@Id
	@Column(name = "PROJECT_CODE")
	private BigInteger id;

	@Column(name = "PROJECT_NAME")
	private String name;

	@Column(name = "PROJECT_STATUS")
	private String status = "I"; // I or A

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "MUST_ENTER_DONATOR")
	private Integer mustEnterDonator;

	@Column(name = "COST")
	private Integer value;

	@Column(name = "MIN_AMOUNT")
	private Integer minimumAmount;

	@Column(name = "QR_CODE")
	private String qrCode;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "YEARLYPROJECT")
	private Integer type;

	public CouponType() {

	}

	public CouponType(BigInteger id) {
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

	// public byte[] getImage() {
	// return image;
	// }
	//
	// public void setImage(byte[] image) {
	// this.image = image;
	// }

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getMustEnterDonator() {
		if (mustEnterDonator == null) {
			mustEnterDonator = 0;
		}
		return mustEnterDonator;
	}

	public void setMustEnterDonator(Integer mustEnterDonator) {
		this.mustEnterDonator = mustEnterDonator;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getMinimumAmount() {
		if (minimumAmount == null) {
			minimumAmount = 0;
		}
		return minimumAmount;
	}

	public void setMinimumAmount(Integer minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Integer getPriority() {
		if (priority == null) {
			priority = 0;
		}
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public Integer getType() {
		if (type == null)
			type = 0;
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
