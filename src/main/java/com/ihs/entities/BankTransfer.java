package com.ihs.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SP_BANKSETTING_DTL")
public class BankTransfer {

    @Id
    @Column(name = "BANK_CODE")
    private BigInteger id;

    @Column(name = "BANK_NAME")
    private String name;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
