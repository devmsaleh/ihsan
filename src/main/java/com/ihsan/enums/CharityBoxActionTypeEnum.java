package com.ihsan.enums;

public enum CharityBoxActionTypeEnum {

	INSERT("1", "زرع"), REPLACE("2", "استبدال"), COLLECT("3", "جمع"), BROKEN("4", "مكسورة"), CHECK("5", "فحص"),
	COLLECTED_MONEY("7", "جمع أموال"), DESTROY("8", "إتلاف"), WITHDRAWAL("9", "سحب"), LOST("10", "مفقودة"),
	UNDEFINED("-1", "غير معرف");

	private String value;
	private String label;

	public static CharityBoxActionTypeEnum getEnumByName(String name) {
		if (name == null)
			return null;
		for (CharityBoxActionTypeEnum enumObj : CharityBoxActionTypeEnum.values()) {
			if (enumObj.toString().equals(name))
				return enumObj;
		}
		return null;
	}

	public static CharityBoxActionTypeEnum getEnumByValue(String value) {
		if (value == null)
			return null;
		for (CharityBoxActionTypeEnum enumObj : CharityBoxActionTypeEnum.values()) {
			if (enumObj.getValue().equals(value))
				return enumObj;
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	private CharityBoxActionTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
