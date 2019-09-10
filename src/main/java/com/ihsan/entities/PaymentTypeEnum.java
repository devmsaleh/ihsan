package com.ihsan.entities;

public enum PaymentTypeEnum {

    CASH("CASH"), CHEQUE("CHEQUE"), CREDIT("CREDIT"), BANK_TRANSFER("BANK_TRANSFER");

    private String value;

    public String getValue() {
        return value;
    }

    private PaymentTypeEnum(String value) {
        this.value = value;
    }

}
