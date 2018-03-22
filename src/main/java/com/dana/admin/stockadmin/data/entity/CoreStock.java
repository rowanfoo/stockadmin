package com.dana.admin.stockadmin.data.entity;




import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class CoreStock {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String code;
  private java.sql.Date date;
  private String name;
  private String descp;
  private Long shares;
  private String marketcap;
  private String consequtivelow;
  private String category;
  private String subcategory;
  private String top;
  private String notes1;
  private String notes2;
  private String reason;
  private String moat;
  private String fundnotes;
  private Double buytrigger;
  private String wishlist;
  private String trend;
  private Double alertprice;
  private Double normandyprice;
  private String normandynotes;
  private String fydate;
  private String technicalnotes;
  private String montlynotes;
  private String weeklynotes;
  private String dailynotes;
  private Double stoploss;
  private String stoplossnotes;
  private String whenbuy;
  private Double whenbuyprice;
  private String easychange;
  private Double keysupportprice;
  private String keysupportpricenotes;
  private String defendkeynotes;
  private Double defendkeyprice;
  private String nope;
  private String news;
  private String chart;
  private String stagegrowth;
  private String research;
  private String researchcat;
  private String onenotes;
  private String aindex;
  private String delisted;

   public  CoreStock(){}



}
