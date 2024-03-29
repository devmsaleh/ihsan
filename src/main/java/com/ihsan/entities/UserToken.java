package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;

@Entity
@Table(name = "USER_TOKEN")
public class UserToken {

	@Id
	@Column(name = "TOKEN")
	private String token = UUID.randomUUID().toString();

	@Column(name = "DELEGATE_ID")
	private BigInteger delegateId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate = DateUtils.addHours(new Date(), 1);

	public UserToken(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public UserToken() {

	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
