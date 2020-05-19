package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TM_COUPONS_COLLECTION_AUTO")

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "collectProc", procedureName = "TM_PACKAGE.POST_E_COUPON_COLLECTION_TRX", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID", type = BigInteger.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_USER", type = BigInteger.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_STATUS", type = String.class) }) })
public class ReceiptCollection {

	@Id
	@Column(name = "COUPONS_CLCT_AUTO_ID")
	private BigInteger id;

	@Column(name = "COLLECTION_NUMBER")
	private String number;

	@Column(name = "STATUS")
	private int status = 0;

	@Column(name = "ORG_ID")
	private int orgId = 1;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COLLECTION_DATE")
	private Date collectionDate = new Date();

	@Column(name = "BANK_ACC_ID")
	private BigInteger bankAccountId;

	@Column(name = "BRANCH_ID")
	private BigInteger branchId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELEGATE_ID")
	private Delegate delegate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_ID")
	private Delegate supervisor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	public ReceiptCollection(BigInteger id, String number, Delegate delegate, Delegate supervisor) {
		this.id = id;
		this.number = number;
		this.delegate = delegate;
		this.bankAccountId = delegate.getBankAccountId();
		this.supervisor = supervisor;
		this.createdBy = supervisor;
	}

	public ReceiptCollection() {

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public BigInteger getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BigInteger bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Delegate getDelegate() {
		return delegate;
	}

	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	public Delegate getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Delegate supervisor) {
		this.supervisor = supervisor;
	}

	public Delegate getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Delegate createdBy) {
		this.createdBy = createdBy;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public BigInteger getBranchId() {
		return branchId;
	}

	public void setBranchId(BigInteger branchId) {
		this.branchId = branchId;
	}

}
