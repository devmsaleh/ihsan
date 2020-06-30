package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SupervisorReportDTO {

	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal creditCardAmount = new BigDecimal(0);
	private BigDecimal chequeAmount = new BigDecimal(0);
	private BigDecimal totalAmount = new BigDecimal(0);
	private int cashReceiptsCount;
	private int creditCardReceiptsCount;
	private int chequeReceiptsCount;
	private String date;
	private List<ReceiptPrintDTO> receiptsList = new ArrayList<ReceiptPrintDTO>();

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getCashReceiptsCount() {
		return cashReceiptsCount;
	}

	public void setCashReceiptsCount(int cashReceiptsCount) {
		this.cashReceiptsCount = cashReceiptsCount;
	}

	public int getCreditCardReceiptsCount() {
		return creditCardReceiptsCount;
	}

	public void setCreditCardReceiptsCount(int creditCardReceiptsCount) {
		this.creditCardReceiptsCount = creditCardReceiptsCount;
	}

	public int getChequeReceiptsCount() {
		return chequeReceiptsCount;
	}

	public void setChequeReceiptsCount(int chequeReceiptsCount) {
		this.chequeReceiptsCount = chequeReceiptsCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ReceiptPrintDTO> getReceiptsList() {
		return receiptsList;
	}

	public void setReceiptsList(List<ReceiptPrintDTO> receiptsList) {
		this.receiptsList = receiptsList;
	}

}
