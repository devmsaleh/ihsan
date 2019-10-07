package com.ihsan.entities;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_CONTRACTS_CASES")
public class OldSponsorship {

	@Id
	@Column(name = "CONTRACT_CASE_ID")
	private BigInteger id;

	@OneToOne
	@JoinColumn(name = "CASE_ID")
	private Orphan orphan;

	@Column(name = "CASEAMOUNT")
	private BigDecimal amount;

	@Column(name = "SPONS_FOR")
	private String sponsorFor;

	@Column(name = "FIRST_TITLE", insertable = false, updatable = false)
	private BigInteger firstTitleId;

	public BigInteger getFirstTitleId() {
		return firstTitleId;
	}

	public void setFirstTitleId(BigInteger firstTitleId) {
		this.firstTitleId = firstTitleId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Orphan getOrphan() {
		return orphan;
	}

	public void setOrphan(Orphan orphan) {
		this.orphan = orphan;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSponsorFor() {
		return sponsorFor;
	}

	public void setSponsorFor(String sponsorFor) {
		this.sponsorFor = sponsorFor;
	}

}
