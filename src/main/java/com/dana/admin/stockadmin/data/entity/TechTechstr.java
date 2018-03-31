package com.dana.admin.stockadmin.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Setter
@Getter

public class TechTechstr {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String code;
  private LocalDate date;
  private long mode;
  private double close;
  private double fifty;
  private double fiftychg;
  private long volume;
  private double threemthvol;
  private double changepercent;
  private long fiftycount;
  private double open;
  private String lowlow;
  private double sixmonthprice;
  private String seen;
  private double twohundredchg;
  private String ok;
  private String notes;

  public TechTechstr(){}

  public TechTechstr(String code, LocalDate date, long mode) {
    this.code = code;
    this.date = date;
    this.mode = mode;
  }

  @Override
  public String toString() {
    return "TechTechstr{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", date=" + date +
            ", mode=" + mode +
            ", close=" + close +
            ", fifty=" + fifty +
            ", fiftychg=" + fiftychg +
            ", volume=" + volume +
            ", threemthvol=" + threemthvol +
            ", changepercent=" + changepercent +
            ", fiftycount=" + fiftycount +
            ", open=" + open +
            ", lowlow='" + lowlow + '\'' +
            ", sixmonthprice=" + sixmonthprice +
            ", seen='" + seen + '\'' +
            ", twohundredchg=" + twohundredchg +
            ", ok='" + ok + '\'' +
            ", notes='" + notes + '\'' +
            '}';
  }
}
