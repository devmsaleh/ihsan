package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NewCouponDTO {

    private BigInteger couponTypeId;
    private String couponTypeName;
    private BigDecimal amount;
    private String notes;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigInteger getCouponTypeId() {
        return couponTypeId;
    }

    public void setCouponTypeId(BigInteger couponTypeId) {
        this.couponTypeId = couponTypeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

}
