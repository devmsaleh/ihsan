package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TM_COUPONS_COLLECTION_AUTO_DTL")
public class ReceiptCollectionDetail {

	@Id
	@Column(name = "COUPONS_CLCT_AUTO_DTL_ID")
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPONS_CLCT_AUTO_ID")
	private ReceiptCollection receiptCollection;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPONS_COLLECTION_ID")
	private Receipt receipt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	public ReceiptCollectionDetail(BigInteger id, ReceiptCollection receiptCollection, Receipt receipt,
			Delegate createdBy) {
		this.id = id;
		this.receiptCollection = receiptCollection;
		this.receipt = receipt;
		this.createdBy = createdBy;
	}

	public ReceiptCollectionDetail() {

	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public ReceiptCollection getReceiptCollection() {
		return receiptCollection;
	}

	public void setReceiptCollection(ReceiptCollection receiptCollection) {
		this.receiptCollection = receiptCollection;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Delegate getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Delegate createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
