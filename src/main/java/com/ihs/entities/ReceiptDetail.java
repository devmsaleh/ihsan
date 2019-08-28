package com.ihs.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TM_RECEIPT_DETAILS")
public class ReceiptDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tm_receipt_details_seq")
	@SequenceGenerator(sequenceName = "tm_receipt_details_seq", allocationSize = 1, name = "tm_receipt_details_seq")
	@Column(name = "RECEIPT_DETAIL_CODE", unique = true)
	private BigInteger id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_CODE")
	private CouponType coupon;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "RECEIPT_CODE", insertable = false, updatable = false)
	private BigInteger receiptId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECEIPT_CODE")
	private Receipt receipt;

	@Column(name = "NOTES")
	private String notes;

	// @Column(name = "RECEIPT_DETAIL_NAME", columnDefinition = "nvarchar2 (1000)")
	@Transient
	private String name;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Delegate getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Delegate createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public CouponType getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponType coupon) {
		this.coupon = coupon;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigInteger receiptId) {
		this.receiptId = receiptId;
	}

}
