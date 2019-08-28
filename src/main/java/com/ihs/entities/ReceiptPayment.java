package com.ihs.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TM_RECEIPT_PAYMENTS")
public class ReceiptPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tm_receipt_payments_seq")
	@SequenceGenerator(sequenceName = "tm_receipt_payments_seq", allocationSize = 1, name = "tm_receipt_payments_seq")
	@Column(name = "PAYMENT_CODE", unique = true)
	private BigInteger id;

	@Column(name = "CREDIT_CARD_NO")
	private String creditCardTransactionNumber;

	@OneToOne
	@JoinColumn(name = "RECEIPT_CODE")
	private Receipt receipt;

	@Column(name = "CHECK_NUMBER")
	private String chequeNumber;

	// @Temporal(TemporalType.DATE)
	@Column(name = "CHECK_DATE")
	private Date chequeDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDON")
	private Date creationDate = new Date();

	@OneToOne
	@JoinColumn(name = "CREATEDBY")
	private Delegate createdBy;

	@Column(name = "CACHE_VALUE")
	private BigDecimal cashValue;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCreditCardTransactionNumber() {
		return creditCardTransactionNumber;
	}

	public void setCreditCardTransactionNumber(String creditCardTransactionNumber) {
		this.creditCardTransactionNumber = creditCardTransactionNumber;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
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

	public BigDecimal getCashValue() {
		return cashValue;
	}

	public void setCashValue(BigDecimal cashValue) {
		this.cashValue = cashValue;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
