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

}
