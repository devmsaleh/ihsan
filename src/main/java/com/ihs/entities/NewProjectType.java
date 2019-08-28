package com.ihs.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_TYPES")
public class NewProjectType {

    @Id
    @Column(name = "TYPE_ID")
    private BigInteger id;

    @Column(name = "PROJ_NAME")
    private String name;

    public NewProjectType() {

    }

    public NewProjectType(BigInteger id) {
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

}
