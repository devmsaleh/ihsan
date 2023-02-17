package com.ihsan.enums;

public enum CharityBoxActionTypeEnum {

	INSERT("1", "زرع", CharityBoxStatusEnum.ACTIVE), REPLACE("2", "استبدال", CharityBoxStatusEnum.REPLACED),
	COLLECT("3", "جمع", CharityBoxStatusEnum.COLLECTED), BROKEN("4", "مكسورة", CharityBoxStatusEnum.BROKEN),
	CHECK("5", "فحص", null), DONATION_COLLECT("6", "تحصيل التبرعات", null),
	COLLECTED_MONEY("7", "جمع أموال", CharityBoxStatusEnum.ACTIVE), DESTROY("8", "إتلاف", null),
	WITHDRAWAL("9", "سحب", CharityBoxStatusEnum.NOT_ACTIVE), LOST("10", "مفقودة", CharityBoxStatusEnum.LOST),
	UNDEFINED("-1", "غير معرف", null);

	private final String value;
	private final String label;
	private final CharityBoxStatusEnum relatedStatusEnum;

	private CharityBoxActionTypeEnum(String value, String label, CharityBoxStatusEnum relatedStatusEnum) {
		this.value = value;
		this.label = label;
		this.relatedStatusEnum = relatedStatusEnum;
	}

	public static CharityBoxStatusEnum findStatus(CharityBoxActionTypeEnum actionTypeEnum) {
		if (actionTypeEnum == null)
			return null;
		for (CharityBoxActionTypeEnum enumLoop : CharityBoxActionTypeEnum.values()) {
			if (enumLoop.toString().equals(actionTypeEnum.toString()))
				return enumLoop.getRelatedStatusEnum();
		}
		return null;
	}

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

	public String getLabel() {
		return label;
	}

	public CharityBoxStatusEnum getRelatedStatusEnum() {
		return relatedStatusEnum;
	}

}
