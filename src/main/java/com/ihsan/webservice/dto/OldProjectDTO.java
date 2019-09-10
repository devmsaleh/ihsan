package com.ihsan.webservice.dto;

import java.math.BigDecimal;

public class OldProjectDTO {

    private String id;
    private String name;
    private String description;
    private BigDecimal cost;
    private String countryName;
    private String categoryName;

    public OldProjectDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public OldProjectDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
