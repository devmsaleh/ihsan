package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

public class LocationDTO {

	private BigInteger id;
	private String name;
	private BigInteger regionId;

	public LocationDTO() {

	}

	public LocationDTO(BigInteger id, BigInteger regionId, String name) {
		this.id = id;
		this.name = name;
		this.regionId = regionId;
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

	public BigInteger getRegionId() {
		return regionId;
	}

	public void setRegionId(BigInteger regionId) {
		this.regionId = regionId;
	}

}
