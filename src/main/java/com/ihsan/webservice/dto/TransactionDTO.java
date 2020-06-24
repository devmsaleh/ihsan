package com.ihsan.webservice.dto;

public class TransactionDTO {

	private String description;

	public TransactionDTO() {

	}

	public TransactionDTO(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
