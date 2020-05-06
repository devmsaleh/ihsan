package com.ihsan.enums;

public enum CharityBoxActionTypeEnum {

	INSERT("1"), REPLACE("2"), COLLECT("3"), BROKEN("4"), CHECK("5"), COLLECTED_MONEY("7"), DESTROY("8"),
	WITHDRAWAL("9"), LOST("10");

	private String value;

	public String getValue() {
		return value;
	}

	private CharityBoxActionTypeEnum(String value) {
		this.value = value;
	}

}
