package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;
import java.util.Date;

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
@Table(name = "TM_CHARITY_BOX_TRANSFERS_DTL")
public class CharityBoxTransferDetail {

	@Id
	@GenericGenerator(name = "charityBoxTransferDetail_id_generator", strategy = "com.ihsan.entities.ids.CharityBoxTransferDetailIdGenerator")
	@GeneratedValue(generator = "charityBoxTransferDetail_id_generator")
	@Column(name = "TRANSFER_LINE_ID")
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARITY_BOX_ID")
	private CharityBox charityBox;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEW_CHARITY_BOX")
	private CharityBox newCharityBox; // in case of insert or replace

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_LOCATION_ID")
	private SubLocation subLocation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTION_TYPE")
	private CharityBoxActionType actionType;

	@Column(name = "ACTION_TYPE", insertable = false, updatable = false)
	private String actionTypeId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_ID")
	private Delegate supervisor;

	@Column(name = "NOTES")
	private String notes;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACTION_NUMBER", insertable = false, updatable = false)
	private BigInteger transactionNumber;

	@Column(name = "SAFETY_CASE")
	private BigInteger safetyCase;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARITY_BOX_TRANSFER_ID")
	private CharityBoxTransfer charityBoxTransfer;

	@Transient
	private ErrorCodeEnum errorCode = ErrorCodeEnum.SUCCESS_CODE;

	@Column(name = "IS_TAWASUL_APP")
	private boolean tawasulApp = true;

	@Column(name = "TRANSFER_TYPE")
	private BigInteger transferType;

	@Column(name = "IS_SUB_LOCATION_CLOSED")
	private boolean subLocationTemporaryClosed;

	public BigInteger getSafetyCase() {
		return safetyCase;
	}

	public void setSafetyCase(BigInteger safetyCase) {
		this.safetyCase = safetyCase;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public CharityBox getCharityBox() {
		return charityBox;
	}

	public void setCharityBox(CharityBox charityBox) {
		this.charityBox = charityBox;
	}

	public CharityBox getNewCharityBox() {
		return newCharityBox;
	}

	public void setNewCharityBox(CharityBox newCharityBox) {
		this.newCharityBox = newCharityBox;
	}

	public SubLocation getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(SubLocation subLocation) {
		this.subLocation = subLocation;
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

	public CharityBoxActionType getActionType() {
		return actionType;
	}

	public void setActionType(CharityBoxActionType actionType) {
		this.actionType = actionType;
	}

	public Delegate getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Delegate supervisor) {
		this.supervisor = supervisor;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigInteger getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(BigInteger transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public CharityBoxTransfer getCharityBoxTransfer() {
		return charityBoxTransfer;
	}

	public void setCharityBoxTransfer(CharityBoxTransfer charityBoxTransfer) {
		this.charityBoxTransfer = charityBoxTransfer;
	}

	public boolean isTawasulApp() {
		return tawasulApp;
	}

	public void setTawasulApp(boolean tawasulApp) {
		this.tawasulApp = tawasulApp;
	}

	public BigInteger getTransferType() {
		return transferType;
	}

	public void setTransferType(BigInteger transferType) {
		this.transferType = transferType;
	}

	public boolean isSubLocationTemporaryClosed() {
		return subLocationTemporaryClosed;
	}

	public void setSubLocationTemporaryClosed(boolean subLocationTemporaryClosed) {
		this.subLocationTemporaryClosed = subLocationTemporaryClosed;
	}

	public String getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(String actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	@Override
	public String toString() {
		return "CharityBoxTransferDetail [id=" + id + ", actionTypeId=" + actionTypeId + ", notes=" + notes
				+ ", transactionNumber=" + transactionNumber + ", safetyCase=" + safetyCase + ", transferType="
				+ transferType + ", subLocationTemporaryClosed=" + subLocationTemporaryClosed + "]";
	}

}
