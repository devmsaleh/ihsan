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

@Entity
@Table(name = "TM_LOCATIONS")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_SEQ")
	@SequenceGenerator(sequenceName = "LOCATION_SEQ", allocationSize = 1, name = "LOCATION_SEQ")
	@Column(name = "LOCATION_ID")
	private BigInteger id;

	@Column(name = "LOCATION_NAME")
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	private Region region;

	@Column(name = "REGION_ID", insertable = false, updatable = false)
	private BigInteger regionId;

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

	public BigInteger getRegionId() {
		return regionId;
	}

	public void setRegionId(BigInteger regionId) {
		this.regionId = regionId;
	}

	public Location() {

	}

	public Location(String name, BigInteger regionId) {
		this.name = name;
		this.region = new Region(regionId);
	}

	public Location(BigInteger id) {
		this.id = id;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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

	public boolean isAlreadyExist() {
		return alreadyExist;
	}

	public void setAlreadyExist(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
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
