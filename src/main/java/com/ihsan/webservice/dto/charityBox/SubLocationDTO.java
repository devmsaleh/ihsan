package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

import com.ihsan.entities.charityBoxes.SubLocation;

public class SubLocationDTO {

	private BigInteger id;
	private String name;
	private BigInteger locationId;
	private String locationName;
	private String regionName;
	private Float locationLatitude;
	private Float locationLongitude;
	private String address;
	private String mobile;
	private int rating;
	private boolean subLocationTemporaryClosed;
	private String notes;

	public SubLocationDTO() {

	}

	public SubLocationDTO(SubLocation subLocation) {
		this.id = subLocation.getId();
		this.name = subLocation.getName();
		this.locationId = subLocation.getLocationId();
		this.locationLatitude = subLocation.getLocationLatitude();
		this.locationLongitude = subLocation.getLocationLongitude();
	}

	public SubLocationDTO(BigInteger id, BigInteger locationId, String name, Float locationLatitude,
			Float locationLongitude) {
		this.id = id;
		this.name = name;
		this.locationId = locationId;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
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

	public BigInteger getLocationId() {
		return locationId;
	}

	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isSubLocationTemporaryClosed() {
		return subLocationTemporaryClosed;
	}

	public void setSubLocationTemporaryClosed(boolean subLocationTemporaryClosed) {
		this.subLocationTemporaryClosed = subLocationTemporaryClosed;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
