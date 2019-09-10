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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ihsan.entities.Delegate;

@Entity
@Table(name = "TM_CHARITY_BOX_ATTACHMENT")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_SEQ")
	@SequenceGenerator(sequenceName = "ATTACHMENT_SEQ", allocationSize = 1, name = "ATTACHMENT_SEQ")
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "NAME")
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSFER_LINE_ID")
	private CharityBoxTransferDetail charityBoxTransfer;

	@Column(name = "TRANSFER_LINE_ID", insertable = false, updatable = false)
	private BigInteger charityBoxTransferId;

	@Column(name = "FILE_PATH")
	private String filePath;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	public BigInteger getCharityBoxTransferId() {
		return charityBoxTransferId;
	}

	public void setCharityBoxTransferId(BigInteger charityBoxTransferId) {
		this.charityBoxTransferId = charityBoxTransferId;
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

	public CharityBoxTransferDetail getCharityBoxTransfer() {
		return charityBoxTransfer;
	}

	public void setCharityBoxTransfer(CharityBoxTransferDetail charityBoxTransfer) {
		this.charityBoxTransfer = charityBoxTransfer;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
