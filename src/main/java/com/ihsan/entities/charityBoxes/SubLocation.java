package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;

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
import javax.persistence.Transient;

import com.ihsan.constants.ErrorCodeEnum;

@Entity
@Table(name = "TM_SUB_LOCATIONS")
public class SubLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUB_LOCATION_SEQ")
	@SequenceGenerator(sequenceName = "SUB_LOCATION_SEQ", allocationSize = 1, name = "SUB_LOCATION_SEQ")
	@Column(name = "SUB_LOCATION_ID")
	private BigInteger id;

	@Column(name = "SUB_LOCATION_NAME")
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATION_ID")
	private Location location;

	@Column(name = "LOCATION_ID", insertable = false, updatable = false)
	private BigInteger locationId;

	@Column(name = "LOCATION_LATITUDE")
	private Float locationLatitude;

	@Column(name = "LOCATION_LONGITUDE")
	private Float locationLongitude;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "STATUS")
	private String status;

	@Transient
	private boolean alreadyExist;

	@Transient
	private ErrorCodeEnum errorCode;

	// we should map creation date here

	public BigInteger getLocationId() {
		return locationId;
	}

	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
	}

	public SubLocation(String name, BigInteger locationId, Float locationLatitude, Float locationLongitude) {
		this.name = name;
		this.location = new Location(locationId);
		this.locationId = locationId;
		if (locationLatitude != null)
			this.locationLatitude = locationLatitude;
		if (locationLongitude != null)
			this.locationLongitude = locationLongitude;
	}

	public SubLocation() {

	}

	public SubLocation(BigInteger id) {
		this.id = id;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Float getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Float locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public Float getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Float locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public boolean isAlreadyExist() {
		return alreadyExist;
	}

	public void setAlreadyExist(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
