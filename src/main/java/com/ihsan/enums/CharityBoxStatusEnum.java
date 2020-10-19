package com.ihsan.enums;

public enum CharityBoxStatusEnum {

	// فى حالة عملية جمع الأموال ايضا يتم تحويل الحصالة الى فعالة
	ACTIVE("0", "فعالة", CharityBoxActionTypeEnum.INSERT),
	NOT_ACTIVE("1", "غير فعالة", CharityBoxActionTypeEnum.WITHDRAWAL),
	REPLACED("2", "مستبدلة", CharityBoxActionTypeEnum.REPLACE),
	COLLECTED("3", "مجمعة", CharityBoxActionTypeEnum.COLLECT), BROKEN("4", "مكسورة", CharityBoxActionTypeEnum.BROKEN),
	LOST("5", "مفقودة", CharityBoxActionTypeEnum.LOST), BROKEN_COLLECTED("6", "مكسورة مجمعة", null),
	DESTROYED("7", "تم الاتلاف", null), DONATION_COLLECTED("8", "تحصيل التبرعات", null);

	private String value;
	private String label;
	private CharityBoxActionTypeEnum relatedActionEnum;

	public String getValue() {
		return value;
	}

	private CharityBoxStatusEnum(String value, String label, CharityBoxActionTypeEnum relatedActionEnum) {
		this.value = value;
		this.label = label;
		this.relatedActionEnum = relatedActionEnum;
	}

	public String getLabel() {
		return label;
	}

	public CharityBoxActionTypeEnum getRelatedActionEnum() {
		return relatedActionEnum;
	}

}
