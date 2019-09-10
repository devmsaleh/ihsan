package com.ihsan.webservice.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OldSponsorshipDTO {

    private BigInteger sponsorId;
    private List<OrphanSponsorshipDTO> orphansList = new ArrayList<OrphanSponsorshipDTO>();
    private String sponsorFor;
    private String firstTitleId;
    private DonateAllOrphans donateAllOrphans = new DonateAllOrphans();

    public String getSponsorFor() {
        return sponsorFor;
    }

    public void setSponsorFor(String sponsorFor) {
        this.sponsorFor = sponsorFor;
    }

    public String getFirstTitleId() {
        return firstTitleId;
    }

    public void setFirstTitleId(String firstTitleId) {
        this.firstTitleId = firstTitleId;
    }

    public BigInteger getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(BigInteger sponsorId) {
        this.sponsorId = sponsorId;
    }

    public List<OrphanSponsorshipDTO> getOrphansList() {
        return orphansList;
    }

    public void setOrphansList(List<OrphanSponsorshipDTO> orphansList) {
        this.orphansList = orphansList;
    }

    public DonateAllOrphans getDonateAllOrphans() {
        return donateAllOrphans;
    }

    public void setDonateAllOrphans(DonateAllOrphans donateAllOrphans) {
        this.donateAllOrphans = donateAllOrphans;
    }

}
