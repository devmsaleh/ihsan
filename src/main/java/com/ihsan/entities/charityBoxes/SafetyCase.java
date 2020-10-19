package com.ihsan.entities.charityBoxes;

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
@Table(name = "TM_SAFETY_CASE")
public class SafetyCase {

	@Id
	@Column(name = "SAFETY_CASE_ID")
	private BigInteger id;

	@Column(name = "SAFETY_CASE_NAME")
	private String name;

	@Column(name = "SAFETY_CASE_NUMBER")
	private String number;

	@Column(name = "STATUS")
	private String status = "N"; // N = Not Active , Y = Active

	@Column(name = "SAFETY_CASE_BARCODE")
	private String barcode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdateDate;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		if (StringUtils.isBlank(status))
			status = "N";
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
