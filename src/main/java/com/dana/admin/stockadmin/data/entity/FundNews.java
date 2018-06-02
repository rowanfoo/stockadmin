package com.dana.admin.stockadmin.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class FundNews {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")

    private java.time.LocalDate date;
    private String link ;
    private String title;
    private String seen;
    private String ok;
    private String notes;
    private String yesnotes;

    public  FundNews(){}

//    public FundNews(String code, LocalDate date, String title, String link) {
//        this.code = code;
//        this.date = date;
//        this.link = link;
//        this.title = title;
//    }
@Override
public String toString() {
    return "FundNews{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", date=" + date +
            ", link='" + link + '\'' +
            ", title='" + title + '\'' +
            ", seen='" + seen + '\'' +
            ", ok='" + ok + '\'' +
            ", notes='" + notes + '\'' +
            ", yesnotes='" + yesnotes + '\'' +
            '}';
}


}
