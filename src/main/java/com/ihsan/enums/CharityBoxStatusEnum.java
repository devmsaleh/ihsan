package com.ihsan.enums;

public enum CharityBoxStatusEnum {

	ACTIVE("0"), NOT_ACTIVE("1"), REPLACED("2"), COLLECTED("3"), BROKEN("4"), LOST("5"), BROKEN_COLLECTED("6"),
	CORRUPTED("7"), COLLECTED_MONEY("8");

	private String value;

	public String getValue() {
		return value;
	}

	private CharityBoxStatusEnum(String value) {
		this.value = value;
	}

}
