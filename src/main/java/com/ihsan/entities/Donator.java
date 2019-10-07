package com.ihsan.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SP_BENEFICENTS")
public class Donator {

    @Id
    @Column(name = "BENEFICENT_ID")
    private BigInteger id;

    @Column(name = "BENEFICENTNAME")
    private String name;

    @Column(name = "BENEFICENT_NO")
    private String number;

    @Column(name = "BENEFICENTEMAIL")
    private String email;

    @Column(name = "BENEFICENTMOBILE")
    private String mobile;

    @Column(name = "BOXMAIL")
    private String mailBox;

    @Column(name = "ACCOUNTNO")
    private String accountNumber;

    public Donator() {

    }

    public Donator(BigInteger id) {
        this.id = id;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
