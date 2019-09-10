package com.ihsan.webservice.dto;

import java.util.ArrayList;
import java.util.List;

import com.ihsan.entities.PaymentTypeEnum;

public class ReceiptDTO {

	private String delegateId;

	private String donatorPhoneNumber;

	private PaymentTypeEnum paymentType = PaymentTypeEnum.CASH;

	private String donatorName;

	private List<NewCouponDTO> couponsList = new ArrayList<NewCouponDTO>();

	// private List<NewProjectDTO> newProjectsList = new ArrayList<NewProjectDTO>();
	// private List<OldProjectDonationDTO> oldProjectsList = new
	// ArrayList<OldProjectDonationDTO>();
	// private List<NewSponsorshipDTO> newSponsorshipList = new
	// ArrayList<NewSponsorshipDTO>();
	// private List<OldSponsorshipDTO> oldSponsorshipList = new
	// ArrayList<OldSponsorshipDTO>();

	private ReceiptPaymentDTO receiptPaymentDTO = new ReceiptPaymentDTO();

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public String getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(String delegateId) {
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

}
