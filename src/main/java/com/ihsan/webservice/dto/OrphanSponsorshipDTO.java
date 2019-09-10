package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OrphanSponsorshipDTO {

    private BigInteger orphanId;
    private String orphanName;
    private String sponsorFor;
    private String firstTitleId;
    private BigDecimal amount;
    private String giftId;
    private BigDecimal giftAmount;
    private int numberOfMonths = 1;
    private String sponsorshipStartDate;

    public BigInteger getOrphanId() {
        return orphanId;
    }

    public void setOrphanId(BigInteger orphanId) {
        this.orphanId = orphanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public String getSponsorFor() {
        return sponsorFor;
    }

    public void setSponsorFor(String sponsorFor) {
        this.sponsorFor = sponsorFor;
    }

    public String getOrphanName() {
        return orphanName;
    }

    public void setOrphanName(String orphanName) {
        this.orphanName = orphanName;
    }

    public int getNumberOfMonths() {
        if (numberOfMonths <= 0) {
            numberOfMonths = 1;
        }
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public String getFirstTitleId() {
        return firstTitleId;
    }

    public void setFirstTitleId(String firstTitleId) {
        this.firstTitleId = firstTitleId;
    }

    public String getSponsorshipStartDate() {
        return sponsorshipStartDate;
    }

    public void setSponsorshipStartDate(String sponsorshipStartDate) {
        this.sponsorshipStartDate = sponsorshipStartDate;
    }

}
