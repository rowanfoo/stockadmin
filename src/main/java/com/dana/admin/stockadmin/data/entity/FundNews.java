package com.dana.admin.stockadmin.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class FundNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private java.time.LocalDate date;
    private String link ;
    private String title;
    private String seen;
    private String ok;
    private String notes;
    private String yesnotes;

    public  FundNews(){}

    public FundNews(String code, LocalDate date, String title, String link) {
        this.code = code;
        this.date = date;
        this.link = link;
        this.title = title;
    }
}
