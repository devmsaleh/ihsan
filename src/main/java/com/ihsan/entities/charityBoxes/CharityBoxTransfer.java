package com.ihsan.entities.charityBoxes;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.Delegate;

@Entity
@Table(name = "TM_CHARITY_BOX_TRANSFERS")
public class CharityBoxTransfer {

	@Id
	@GenericGenerator(name = "charityBoxTransfer_id_generator", strategy = "com.ihsan.entities.ids.CharityBoxTransferIdGenerator")
	@GeneratedValue(generator = "charityBoxTransfer_id_generator")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "CH_BOX_TRANS_SEQ")
	// @SequenceGenerator(sequenceName = "CH_BOX_TRANS_SEQ", allocationSize = 1,
	// name = "CH_BOX_TRANS_SEQ")
	@Column(name = "TRANSFER_ID")
	private BigInteger id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_ID")
	private Delegate supervisor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@Column(name = "STATUS")
	private String status = "1";

	@Column(name = "ORG_ID")
	private Integer orgId = 1;

	@Column(name = "NOTES")
	private String notes;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSFER_NUMBER", insertable = false, updatable = false)
	private BigInteger transferNumber;

	@OneToOne(mappedBy = "charityBoxTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private CharityBoxTransferDetail charityBoxTransferDetail;

	@Transient
	private ErrorCodeEnum errorCode = ErrorCodeEnum.SUCCESS_CODE;

	@Column(name = "IS_TAWASUL_APP")
	private boolean tawasulApp = true;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_LOCATION_ID")
	private SubLocation subLocation;

	public CharityBoxTransfer() {

	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Delegate getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Delegate supervisor) {
		this.supervisor = supervisor;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigInteger getTransferNumber() {
		return transferNumber;
	}

	public void setTransferNumber(BigInteger transferNumber) {
		this.transferNumber = transferNumber;
	}

	public CharityBoxTransferDetail getCharityBoxTransferDetail() {
		return charityBoxTransferDetail;
	}

	public void setCharityBoxTransferDetail(CharityBoxTransferDetail charityBoxTransferDetail) {
		this.charityBoxTransferDetail = charityBoxTransferDetail;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isTawasulApp() {
		return tawasulApp;
	}

	public void setTawasulApp(boolean tawasulApp) {
		this.tawasulApp = tawasulApp;
	}

	public SubLocation getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(SubLocation subLocation) {
		this.subLocation = subLocation;
	}

}
