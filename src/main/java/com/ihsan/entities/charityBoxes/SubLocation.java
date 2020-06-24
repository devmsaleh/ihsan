package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.Delegate;

@Entity
@Table(name = "TM_SUB_LOCATIONS")
public class SubLocation {

	@Id
	@GenericGenerator(name = "subLocation_id_generator", strategy = "com.ihsan.entities.ids.SubLocationIdGenerator")
	@GeneratedValue(generator = "subLocation_id_generator")
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
	private String status = "A";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdateDate;

	@Column(name = "LAST_UPDATE_BY")
	private BigInteger lastUpdateBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELEGATE_ID")
	private Delegate delegate;

	@Column(name = "IS_TAWASUL_APP")
	private boolean tawasulApp = true;

	@Transient
	private boolean alreadyExist;

	@Transient
	private ErrorCodeEnum errorCode;

	@Column(name = "IS_TEMPORARY_CLOSED")
	private boolean subLocationTemporaryClosed;

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
		if (StringUtils.isNotBlank(name) && subLocationTemporaryClosed) {
			name = name + " (" + "مغلق مؤقتا" + ")";
		}
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

	public boolean isTawasulApp() {
		return tawasulApp;
	}

	public void setTawasulApp(boolean tawasulApp) {
		this.tawasulApp = tawasulApp;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Delegate getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Delegate createdBy) {
		this.createdBy = createdBy;
	}

	public Delegate getDelegate() {
		return delegate;
	}

	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	public boolean isSubLocationTemporaryClosed() {
		return subLocationTemporaryClosed;
	}

	public void setSubLocationTemporaryClosed(boolean subLocationTemporaryClosed) {
		this.subLocationTemporaryClosed = subLocationTemporaryClosed;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigInteger getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(BigInteger lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

}
