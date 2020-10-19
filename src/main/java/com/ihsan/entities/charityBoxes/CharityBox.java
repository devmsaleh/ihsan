package com.ihsan.entities.charityBoxes;

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

import com.ihsan.entities.Delegate;

@Entity
@Table(name = "TM_CHARITY_BOXES")
public class CharityBox {

	@Id
	@Column(name = "CHARITY_BOX_ID")
	private BigInteger id;

	@Column(name = "CHARITY_BOX_NAME")
	private String name;

	@Column(name = "CHARITY_BOX_NUMBER")
	private String number;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARITY_BOX_TYPE")
	private CharityBoxType type;

	@Column(name = "CHARITY_BOX_TYPE", insertable = false, updatable = false)
	private BigInteger typeId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS")
	private CharityBoxStatus status;

	@Column(name = "STATUS", insertable = false, updatable = false)
	private String statusId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_OF_BOXES")
	private CharityBoxCategory category;

	@Column(name = "CATEGORY_OF_BOXES", insertable = false, updatable = false)
	private String categoryId;

	@Column(name = "CHARITY_BOX_BARCODE")
	private String barcode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_LOCATION_ID")
	private SubLocation subLocation;

	@Column(name = "SUB_LOCATION_ID", insertable = false, updatable = false)
	private BigInteger subLocationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdateDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_UPDATE_BY")
	private Delegate lastUpdateBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_ID")
	private Delegate supervisor;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "BRANCH_ID")
	private BigInteger branchId;

	public CharityBox() {

	}

	public CharityBox(BigInteger id) {
		this.id = id;
	}

	public Delegate getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Delegate supervisor) {
		this.supervisor = supervisor;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Delegate getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Delegate lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigInteger getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(BigInteger subLocationId) {
		this.subLocationId = subLocationId;
	}

	public BigInteger getTypeId() {
		return typeId;
	}

	public void setTypeId(BigInteger typeId) {
		this.typeId = typeId;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public CharityBoxType getType() {
		return type;
	}

	public void setType(CharityBoxType type) {
		this.type = type;
	}

	public CharityBoxStatus getStatus() {
		return status;
	}

	public void setStatus(CharityBoxStatus status) {
		this.status = status;
	}

	public CharityBoxCategory getCategory() {
		return category;
	}

	public void setCategory(CharityBoxCategory category) {
		this.category = category;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public SubLocation getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(SubLocation subLocation) {
		this.subLocation = subLocation;
	}

	public BigInteger getBranchId() {
		return branchId;
	}

	public void setBranchId(BigInteger branchId) {
		this.branchId = branchId;
	}

}
