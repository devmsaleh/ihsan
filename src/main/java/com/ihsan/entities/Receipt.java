package com.ihsan.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TM_RECEIPTS")
@DynamicUpdate
public class Receipt {

	@Id
	@GenericGenerator(name = "receipt_id_generator", strategy = "com.ihsan.entities.ids.ReceiptIdGenerator")
	@GeneratedValue(generator = "receipt_id_generator")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "tm_receipts_seq")
	// @SequenceGenerator(sequenceName = "tm_receipts_seq", allocationSize = 1, name
	// = "tm_receipts_seq")
	@Column(name = "RECEIPT_CODE", unique = true)
	private BigInteger id;

	@Column(name = "RECEIPT_NUMBER")
	private String number;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TM_DELEGATES_CODE")
	private Delegate delegate;

	@Column(name = "TOTAL_AMOUNT")
	private BigDecimal totalAmount;

	@Column(name = "TOTAL_PAID")
	private BigDecimal totalPaid;

	@Column(name = "REMAINING")
	private BigDecimal remaining = new BigDecimal(0);

	@Column(name = "DONATOR_PHONE_NO")
	private String donatorPhoneNumber;

	@Column(name = "DONATOR_NAME", columnDefinition = "nvarchar2 (100)")
	private String donatorName;

	@Column(name = "COLLECTED")
	private String collected = "N"; // 0 means not collected , 1 means collected

	// status 0,1
	@Column(name = "STATUS")
	private Integer status = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIPT_DATE")
	private Date receiptDate = new Date();

	@Column(name = "ORG_ID")
	private Integer orgId = 1;

	@Column(name = "NO_OF_PRINTS")
	private Integer numberOfPrints;

	// @Column(name = "RECEIPT_TYPE")
	@Transient
	private String paymentType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receipt", cascade = CascadeType.ALL)
	private Set<ReceiptDetail> receiptDetailsList = new HashSet<ReceiptDetail>();

	@OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ReceiptPayment receiptPayment;

	@Column(name = "NOTES", columnDefinition = "nvarchar2 (150)")
	private String notes;

	@Column(name = "IS_TAWASUL_APP")
	private boolean tawasulApp = true;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ReceiptPayment getReceiptPayment() {
		return receiptPayment;
	}

	public void setReceiptPayment(ReceiptPayment receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public Set<ReceiptDetail> getReceiptDetailsList() {
		return receiptDetailsList;
	}

	public void setReceiptDetailsList(Set<ReceiptDetail> receiptDetailsList) {
		this.receiptDetailsList = receiptDetailsList;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getRemaining() {
		return remaining;
	}

	public void setRemaining(BigDecimal remaining) {
		this.remaining = remaining;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Delegate getDelegate() {
		return delegate;
	}

	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDonatorPhoneNumber() {
		return donatorPhoneNumber;
	}

	public void setDonatorPhoneNumber(String donatorPhoneNumber) {
		this.donatorPhoneNumber = donatorPhoneNumber;
	}

	public String getCollected() {
		if (StringUtils.isBlank(collected)) {
			collected = "N";
		}
		return collected;
	}

	public void setCollected(String collected) {
		this.collected = collected;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Integer getOrgId() {
		if (orgId == null)
			orgId = 0;
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getNumberOfPrints() {
		if (numberOfPrints == null)
			numberOfPrints = 0;
		return numberOfPrints;
	}

	public void setNumberOfPrints(Integer numberOfPrints) {
		this.numberOfPrints = numberOfPrints;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isTawasulApp() {
		return tawasulApp;
	}

	public void setTawasulApp(boolean tawasulApp) {
		this.tawasulApp = tawasulApp;
	}

}
