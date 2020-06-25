package com.ihsan.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ihsan.util.GeneralUtils;

@Entity
@Table(name = "POS_DELEGATE_NOTES")
public class DelegateNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private BigInteger id;

	@Column(name = "DELEGATE_ID")
	private BigInteger delegateId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date creationDate = new Date();

	@Column(name = "NOTE")
	private String notes;

	@Column(name = "ACTIVE")
	private boolean active = true;

	@Transient
	private String creationDateFormatted;

	public DelegateNote() {

	}

	public DelegateNote(BigInteger delegateId, String notes) {
		this.delegateId = delegateId;
		this.notes = notes;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreationDateFormatted() {
		if (creationDate != null) {
			creationDateFormatted = GeneralUtils.formatDateTime(creationDate, "ar");
		}
		return creationDateFormatted;
	}

	public void setCreationDateFormatted(String creationDateFormatted) {
		this.creationDateFormatted = creationDateFormatted;
	}

}
