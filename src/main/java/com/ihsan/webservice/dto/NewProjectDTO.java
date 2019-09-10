package com.ihsan.webservice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NewProjectDTO {

    private BigInteger newProjectTypeId; // NewProjectType > ابار مياه - بناء منازل
    private String newProjectTypeName;
    private String projectCountryId;
    private BigInteger projectStudyId;
    private String projectName;
    private BigDecimal amount;
    private BigDecimal commitment;
    private BigInteger donatorId;
    private NewDonatorDTO newDonatorDTO = new NewDonatorDTO();

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCommitment() {
        return commitment;
    }

    public void setCommitment(BigDecimal commitment) {
        this.commitment = commitment;
    }

    public BigInteger getNewProjectTypeId() {
        return newProjectTypeId;
    }

    public void setNewProjectTypeId(BigInteger newProjectTypeId) {
        this.newProjectTypeId = newProjectTypeId;
    }

    public String getProjectCountryId() {
        return projectCountryId;
    }

    public void setProjectCountryId(String projectCountryId) {
        this.projectCountryId = projectCountryId;
    }

    public BigInteger getProjectStudyId() {
        return projectStudyId;
    }

    public void setProjectStudyId(BigInteger projectStudyId) {
        this.projectStudyId = projectStudyId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigInteger getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(BigInteger donatorId) {
        this.donatorId = donatorId;
    }

    public NewDonatorDTO getNewDonatorDTO() {
        return newDonatorDTO;
    }

    public void setNewDonatorDTO(NewDonatorDTO newDonatorDTO) {
        this.newDonatorDTO = newDonatorDTO;
    }

    public String getNewProjectTypeName() {
        return newProjectTypeName;
    }

    public void setNewProjectTypeName(String newProjectTypeName) {
        this.newProjectTypeName = newProjectTypeName;
    }

}
