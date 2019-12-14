package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ihsan.entities.CouponType;

public class CouponReportDTO {

	private BigInteger id;
	private String name;
	private BigDecimal amount = new BigDecimal(0);

	public CouponReportDTO() {

	}

	public CouponReportDTO(CouponType couponType, BigDecimal amount) {
		this.id = couponType.getId();
		this.name = couponType.getName();
		this.amount = amount;
	}

	public CouponReportDTO(BigInteger id, String name) {
		this.id = id;
		this.name = name;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CouponReportDTO other = (CouponReportDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
