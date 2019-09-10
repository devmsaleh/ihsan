package com.ihsan.entities.charityBoxes;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TM_DELEGATE_ROUTE_DTL")
public class RouteDetail {

	@Id
	@Column(name = "ROUTE_LINE_ID")
	private BigInteger id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROUTE_ID")
	private Route route;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUB_LOCATION_ID")
	private SubLocation subLocation;

	@Column(name = "STATUS")
	private Integer status = 1;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public SubLocation getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(SubLocation subLocation) {
		this.subLocation = subLocation;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
