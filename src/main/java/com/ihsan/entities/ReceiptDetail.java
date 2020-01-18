package com.ihsan.entities;

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

@Entity
@Table(name = "TM_RECEIPT_DETAILS")
public class ReceiptDetail {

	@Id
	// @GenericGenerator(name = "receiptDetail_id_generator", strategy =
	// "com.ihsan.entities.ids.ReceiptDetailsIdGenerator")
	// @GeneratedValue(generator = "receiptDetail_id_generator")
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

	@Column(name = "PROJECT_CODE", insertable = false, updatable = false)
	private BigInteger couponId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "RECEIPT_CODE", insertable = false, updatable = false)
	private BigInteger receiptId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECEIPT_CODE")
	private Receipt receipt;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "RECEIPT_DETAIL_NAME")
	private String name;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "DONATOR_NAME")
	private String donatorName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DONATOR_COUNTRY_ID")
	private Country donatorCountry;

	@Column(name = "DONATOR_MOBILE")
	private String donatorMobile;

	@Column(name = "DONATOR_PHONE")
	private String donatorPhone;

	@Column(name = "DONATOR_POBOX")
	private String donatorPOBOX;

	@Column(name = "DONATOR_EMAIL")
	private String donatorEmail;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DONATOR_ID_SPONSORSHIP")
	private Donator sponsor;

	@Column(name = "CASE_ID")
	private BigInteger orphanId;

	@Column(name = "CASE_AMOUNT")
	private BigDecimal caseAmount;

	@Column(name = "GIFT_AMOUNT")
	private BigDecimal giftAmount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GIFT_TYPE_CODE")
	private GiftType giftType;

	@Column(name = "CASE_PAYMENT_TYPE")
	private String casePaymentType;

	@Column(name = "SPONSOR_FOR")
	private String sponsorFor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRST_TITLE")
	private FirstTitle firstTitle;

	@Column(name = "FIRST_TITLE", insertable = false, updatable = false)
	private String firstTitleId;

	@Temporal(TemporalType.DATE)
	@Column(name = "SPONSORSHIP_START_DATE")
	private Date sponsorshipStartDate = new Date();

	@Column(name = "CASE_AMOUNT_PER_MONTH")
	private BigDecimal caseAmountPerMonth;

	@Column(name = "CASE_PAYMENT_MONTHS")
	private Integer casePaymentNumberOfMonths = 1;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_STUDY_ID")
	private ProjectStudy projectStudy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_COUNTRY_ID")
	private Country projectCountry;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DONATOR_ID_PROJECT")
	private Donator donator;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECEIPT_TYPE_ID")
	private NewProjectType receiptType;

	@Column(name = "PROJECT_COMMITMENT")
	private BigDecimal projectCommitment;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SC_PROJECT_ID")
	private OldProject oldProject;

	@Column(name = "IS_TAWASUL_APP")
	private boolean tawasulApp = true;

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

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public Country getDonatorCountry() {
		return donatorCountry;
	}

	public void setDonatorCountry(Country donatorCountry) {
		this.donatorCountry = donatorCountry;
	}

	public String getDonatorMobile() {
		return donatorMobile;
	}

	public void setDonatorMobile(String donatorMobile) {
		this.donatorMobile = donatorMobile;
	}

	public String getDonatorPhone() {
		return donatorPhone;
	}

	public void setDonatorPhone(String donatorPhone) {
		this.donatorPhone = donatorPhone;
	}

	public String getDonatorPOBOX() {
		return donatorPOBOX;
	}

	public void setDonatorPOBOX(String donatorPOBOX) {
		this.donatorPOBOX = donatorPOBOX;
	}

	public String getDonatorEmail() {
		return donatorEmail;
	}

	public void setDonatorEmail(String donatorEmail) {
		this.donatorEmail = donatorEmail;
	}

	public Donator getSponsor() {
		return sponsor;
	}

	public void setSponsor(Donator sponsor) {
		this.sponsor = sponsor;
	}

	public BigInteger getOrphanId() {
		return orphanId;
	}

	public void setOrphanId(BigInteger orphanId) {
		this.orphanId = orphanId;
	}

	public BigDecimal getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(BigDecimal caseAmount) {
		this.caseAmount = caseAmount;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public GiftType getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftType giftType) {
		this.giftType = giftType;
	}

	public String getCasePaymentType() {
		return casePaymentType;
	}

	public void setCasePaymentType(String casePaymentType) {
		this.casePaymentType = casePaymentType;
	}

	public String getSponsorFor() {
		return sponsorFor;
	}

	public void setSponsorFor(String sponsorFor) {
		this.sponsorFor = sponsorFor;
	}

	public FirstTitle getFirstTitle() {
		return firstTitle;
	}

	public void setFirstTitle(FirstTitle firstTitle) {
		this.firstTitle = firstTitle;
	}

	public String getFirstTitleId() {
		return firstTitleId;
	}

	public void setFirstTitleId(String firstTitleId) {
		this.firstTitleId = firstTitleId;
	}

	public Date getSponsorshipStartDate() {
		return sponsorshipStartDate;
	}

	public void setSponsorshipStartDate(Date sponsorshipStartDate) {
		this.sponsorshipStartDate = sponsorshipStartDate;
	}

	public ProjectStudy getProjectStudy() {
		return projectStudy;
	}

	public void setProjectStudy(ProjectStudy projectStudy) {
		this.projectStudy = projectStudy;
	}

	public Country getProjectCountry() {
		return projectCountry;
	}

	public void setProjectCountry(Country projectCountry) {
		this.projectCountry = projectCountry;
	}

	public Donator getDonator() {
		return donator;
	}

	public void setDonator(Donator donator) {
		this.donator = donator;
	}

	public NewProjectType getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(NewProjectType receiptType) {
		this.receiptType = receiptType;
	}

	public BigDecimal getProjectCommitment() {
		return projectCommitment;
	}

	public void setProjectCommitment(BigDecimal projectCommitment) {
		this.projectCommitment = projectCommitment;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public BigDecimal getCaseAmountPerMonth() {
		return caseAmountPerMonth;
	}

	public void setCaseAmountPerMonth(BigDecimal caseAmountPerMonth) {
		this.caseAmountPerMonth = caseAmountPerMonth;
	}

	public Integer getCasePaymentNumberOfMonths() {
		return casePaymentNumberOfMonths;
	}

	public void setCasePaymentNumberOfMonths(Integer casePaymentNumberOfMonths) {
		this.casePaymentNumberOfMonths = casePaymentNumberOfMonths;
	}

	public OldProject getOldProject() {
		return oldProject;
	}

	public void setOldProject(OldProject oldProject) {
		this.oldProject = oldProject;
	}

	public BigInteger getCouponId() {
		return couponId;
	}

	public void setCouponId(BigInteger couponId) {
		this.couponId = couponId;
	}

	public boolean isTawasulApp() {
		return tawasulApp;
	}

	public void setTawasulApp(boolean tawasulApp) {
		this.tawasulApp = tawasulApp;
	}

}
