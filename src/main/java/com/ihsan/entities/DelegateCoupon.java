package com.ihsan.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TM_DELEGATE_COUPONS")
public class DelegateCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "DELEGATE_ID")
	private BigInteger delegateId;

	@Column(name = "COUPON_ID")
	private BigInteger couponId;

	public DelegateCoupon() {

	}

	public DelegateCoupon(BigInteger delegateId, BigInteger couponId) {
		this.delegateId = delegateId;
		this.couponId = couponId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public BigInteger getCouponId() {
		return couponId;
	}

	public void setCouponId(BigInteger couponId) {
		this.couponId = couponId;
	}

}
