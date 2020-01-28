package com.ihsan.enums;

public enum PermissionTypeEnum {

	COUPON(1), CHARITY_BOX(2), SPONSORSHIP(3), PROJECT(4);

	private Integer value;

	public Integer getValue() {
		return value;
	}

	private PermissionTypeEnum(Integer value) {
		this.value = value;
	}

}
