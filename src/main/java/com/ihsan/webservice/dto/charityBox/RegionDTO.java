package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

public class RegionDTO {

	private BigInteger id;
	private String name;
	private String emarahId;

	public RegionDTO() {

	}

	public RegionDTO(BigInteger id, String name) {
		this.id = id;
		this.name = name;
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

	public String getEmarahId() {
		return emarahId;
	}

	public void setEmarahId(String emarahId) {
		this.emarahId = emarahId;
	}

}
