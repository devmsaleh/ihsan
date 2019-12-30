package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

public class LocationDTO {

	private BigInteger id;
	private String name;
	private BigInteger regionId;
	private String address;
	private String mobile;
	private Float locationLatitude;
	private Float locationLongitude;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Float getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Float locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Float getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Float locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

}
