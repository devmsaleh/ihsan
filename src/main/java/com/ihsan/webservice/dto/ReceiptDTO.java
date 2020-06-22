package com.ihsan.webservice.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ihsan.entities.PaymentTypeEnum;

public class ReceiptDTO {

	private BigInteger delegateId;

	private String donatorPhoneNumber;

	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	private String donatorName;

	private boolean smsEnglish = false;

	private List<NewCouponDTO> couponsList = new ArrayList<NewCouponDTO>();

	private List<NewProjectDTO> newProjectsList = new ArrayList<NewProjectDTO>();
	private List<OldProjectDonationDTO> oldProjectsList = new ArrayList<OldProjectDonationDTO>();
	private List<NewSponsorshipDTO> newSponsorshipList = new ArrayList<NewSponsorshipDTO>();
	private List<OldSponsorshipDTO> oldSponsorshipList = new ArrayList<OldSponsorshipDTO>();

	private ReceiptPaymentDTO receiptPaymentDTO = new ReceiptPaymentDTO();

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public String getDonatorPhoneNumber() {
		return donatorPhoneNumber;
	}

	public void setDonatorPhoneNumber(String donatorPhoneNumber) {
		this.donatorPhoneNumber = donatorPhoneNumber;
	}

	public List<NewCouponDTO> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<NewCouponDTO> couponsList) {
		this.couponsList = couponsList;
	}

	public ReceiptPaymentDTO getReceiptPaymentDTO() {
		return receiptPaymentDTO;
	}

	public void setReceiptPaymentDTO(ReceiptPaymentDTO receiptPaymentDTO) {
		this.receiptPaymentDTO = receiptPaymentDTO;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public List<NewProjectDTO> getNewProjectsList() {
		return newProjectsList;
	}

	public void setNewProjectsList(List<NewProjectDTO> newProjectsList) {
		this.newProjectsList = newProjectsList;
	}

	public List<OldProjectDonationDTO> getOldProjectsList() {
		return oldProjectsList;
	}

	public void setOldProjectsList(List<OldProjectDonationDTO> oldProjectsList) {
		this.oldProjectsList = oldProjectsList;
	}

	public List<NewSponsorshipDTO> getNewSponsorshipList() {
		return newSponsorshipList;
	}

	public void setNewSponsorshipList(List<NewSponsorshipDTO> newSponsorshipList) {
		this.newSponsorshipList = newSponsorshipList;
	}

	public List<OldSponsorshipDTO> getOldSponsorshipList() {
		return oldSponsorshipList;
	}

	public void setOldSponsorshipList(List<OldSponsorshipDTO> oldSponsorshipList) {
		this.oldSponsorshipList = oldSponsorshipList;
	}

	public boolean isSmsEnglish() {
		return smsEnglish;
	}

	public void setSmsEnglish(boolean smsEnglish) {
		this.smsEnglish = smsEnglish;
	}

}
