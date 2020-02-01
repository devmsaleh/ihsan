package com.ihsan.enums;

public enum PermissionTypeEnum {

	COUPON(1), CHARITY_BOX(2), SPONSORSHIP(3), PROJECT(4);

	private int value;

	public int getValue() {
		return value;
	}

	private PermissionTypeEnum(int value) {
		this.value = value;
	}

}
