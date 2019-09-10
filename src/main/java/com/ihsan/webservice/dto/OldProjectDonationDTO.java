package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OldProjectDonationDTO {

    private BigDecimal amount;
    private BigInteger donatorId;
    private BigInteger oldProjectId;
    private String oldProjectName;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigInteger getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(BigInteger donatorId) {
        this.donatorId = donatorId;
    }

    public BigInteger getOldProjectId() {
        return oldProjectId;
    }

    public void setOldProjectId(BigInteger oldProjectId) {
        this.oldProjectId = oldProjectId;
    }

    public String getOldProjectName() {
        return oldProjectName;
    }

    public void setOldProjectName(String oldProjectName) {
        this.oldProjectName = oldProjectName;
    }

}
