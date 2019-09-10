package com.ihsan.webservice.dto;

public class GeneralResponseDTO {

	private boolean success;
	private String id;

	public GeneralResponseDTO() {

	}

	public GeneralResponseDTO(String id) {
		this.id = id;
		this.success = true;
	}

	public GeneralResponseDTO(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
