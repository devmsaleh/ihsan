package com.ihsan.webservice.dto;

import com.ihsan.enums.CouponTypeEnum;

public class CouponTypeDTO {

	private String id;

	private String name;

	private boolean mustEnterDonator;

	private int value;

	private int minimumAmount;

	private String qrCode;

	private int priority;

	private CouponTypeEnum type = CouponTypeEnum.NORMAL;

	private boolean favorite;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMustEnterDonator() {
		return mustEnterDonator;
	}

	public void setMustEnterDonator(boolean mustEnterDonator) {
		this.mustEnterDonator = mustEnterDonator;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(int minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public CouponTypeEnum getType() {
		return type;
	}

	public void setType(CouponTypeEnum type) {
		this.type = type;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

}
