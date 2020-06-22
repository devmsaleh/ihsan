package com.ihsan.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_AP_BANK_ACCOUNTS_V")
public class BankDeposit {

	@Id
	@Column(name = "BANK_ACCOUNT_ID")
	private BigInteger id;

	@Column(name = "ACCOUNT_NUMBER")
	private String name;

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
