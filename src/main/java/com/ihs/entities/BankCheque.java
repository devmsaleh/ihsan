package com.ihs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "FND_LOOKUP_VALUES")
@Where(clause = "LOOKUP_TYPE='BANKS'")
public class BankCheque {

    @Id
    @Column(name = "LOOKUP_CODE")
    private String id;

    @Column(name = "ARABIC_VALUE")
    private String nameArabic;

    @Column(name = "VALUE")
    private String nameEnglish;

    @Column(name = "LOOKUP_TYPE")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BankCheque() {

    }

    public BankCheque(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

}
