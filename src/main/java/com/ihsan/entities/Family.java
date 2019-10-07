package com.ihsan.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SP_FAMILIES")
public class Family {

    @Id
    @Column(name = "FAMILY_ID")
    private BigInteger id;

    @Column(name = "FAMILY_NUM")
    private String number;

    @Column(name = "FAMILY_NAME")
    private String name;

    @Column(name = "FATHERDEATHREASON")
    private String fatherDeathReason;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherDeathReason() {
        return fatherDeathReason;
    }

    public void setFatherDeathReason(String fatherDeathReason) {
        this.fatherDeathReason = fatherDeathReason;
    }

}
