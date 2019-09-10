package com.ihsan.webservice.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NewSponsorshipDTO {

    private BigInteger sponsorId;
    private List<OrphanSponsorshipDTO> orphansList = new ArrayList<OrphanSponsorshipDTO>();
    private String sponsorFor;
    private String firstTitleId;
    private NewDonatorDTO newDonatorDTO = new NewDonatorDTO();

    public NewDonatorDTO getNewDonatorDTO() {
        return newDonatorDTO;
    }

    public void setNewDonatorDTO(NewDonatorDTO newDonatorDTO) {
        this.newDonatorDTO = newDonatorDTO;
    }

    public BigInteger getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(BigInteger sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorFor() {
        return sponsorFor;
    }

    public void setSponsorFor(String sponsorFor) {
        this.sponsorFor = sponsorFor;
    }

    public List<OrphanSponsorshipDTO> getOrphansList() {
        return orphansList;
    }

    public void setOrphansList(List<OrphanSponsorshipDTO> orphansList) {
        this.orphansList = orphansList;
    }

    public String getFirstTitleId() {
        return firstTitleId;
    }

    public void setFirstTitleId(String firstTitleId) {
        this.firstTitleId = firstTitleId;
    }

}
