package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ihsan.entities.Delegate;

@Entity
@Table(name = "TM_DELEGATE_ROUTE")
public class Route {

	@Id
	@Column(name = "ROUTE_ID")
	private BigInteger id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ROUTE_DATE")
	private Date date;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELEGATE_ID")
	private Delegate delegate;

	@Column(name = "STATUS")
	private Integer status = 1;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private Delegate createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdateDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_UPDATE_BY")
	private Delegate lastUpdateBy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "route", cascade = CascadeType.ALL)
	private Set<RouteDetail> routeDetailsList = new HashSet<RouteDetail>();

	public Set<RouteDetail> getRouteDetailsList() {
		return routeDetailsList;
	}

	public void setRouteDetailsList(Set<RouteDetail> routeDetailsList) {
		this.routeDetailsList = routeDetailsList;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Delegate getDelegate() {
		return delegate;
	}

	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Delegate getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Delegate lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

}
