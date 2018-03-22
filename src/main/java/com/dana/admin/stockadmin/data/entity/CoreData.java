package com.dana.admin.stockadmin.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
public class CoreData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String code;
  private java.time.LocalDate date;
  @JsonProperty("open_price")
  private Double open;
  @JsonProperty("day_high_price")
  private Double high;
  @JsonProperty("day_low_price")
  private Double low;
  @JsonProperty("last_price")
  private Double close;
  private String closevol;
  private Long volume;
  @JsonProperty("change_price")
  private Double changes;
  private Double changepercent;
  @JsonProperty("previous_close_price")
  private String previousclose;
  private Double avg3mth;
  private Double fifty;
  private Double fiftychg;
  private Double twenty;
  private Double twentychg;
  private Double fourty;
  private Double fourtychg;
  private Double sevenfive;
  private Double sevenfivechg;
  private Double onehundredfifty;
  private Double onehundredfiftychg;
  private Double twohundred;
  private Double twohundredchg;
  private Double fourhundred;
  private Double fourhundredchg;
  private Double avgup;
  private Double avgdown;
  private Double rsi;
  private Double rsichg;
  private Double rsiasx;
  private Double relativestrenght;
  private Double avgupvol;
  private Double avgdownvol;
  private Double rsivol;

//  public CoreData(String code,String date, String price,String change, String changepercent, String open, String high, String low,
//              String volume){
//      this.code=code;
//
//
//  }
  public CoreData(){}


  public CoreData(String code, LocalDate date, Double price, Double change, Double changepercent, Double open, Double high, Double low,
                  Long volume){
    this.code=code;
    this.date=date;
    this.close=price;
    this.changes=change;
    this.changepercent=changepercent;
    this.open=open;
    this.high=high;
    this.low=low;
    this.volume=volume;


  }



  @JsonSetter("last_trade_date")
  public void setDateJSON(String mydate){
    System.out.println("------------setMyDate : "+ mydate );


    String newdate = mydate.substring(0,mydate.indexOf("T") ).trim();
    System.out.println("NEW DATE------ "+ newdate);
 		this.date = LocalDate.parse(newdate);

  }

  @JsonSetter("code")
  public void setCodeJSON(String code){
    System.out.println("------------setCodeJSON : "+ code );
    this.code=code+".AX";

  }
  @JsonSetter("change_in_percent")
  public void setChangePercentJSON(String percent){
    System.out.println("------------setChangePercent : "+ percent );


    String mypercent = percent.substring(0,percent.indexOf("%") );
    System.out.println("NEW setChangePercent------ "+ percent);
    this.changepercent = (Double.parseDouble(mypercent)/100);

  }


  @Override
  public String toString() {
    return "CoreData{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", date=" + date +
            ", open=" + open +
            ", high=" + high +
            ", low=" + low +
            ", close=" + close +
            ", closevol='" + closevol + '\'' +
            ", volume=" + volume +
            ", changes=" + changes +
            ", changepercent=" + changepercent +
            ", previousclose='" + previousclose + '\'' +
            ", avg3mth=" + avg3mth +
            ", fifty=" + fifty +
            ", fiftychg=" + fiftychg +
            ", twenty=" + twenty +
            ", twentychg=" + twentychg +
            ", fourty=" + fourty +
            ", fourtychg=" + fourtychg +
            ", sevenfive=" + sevenfive +
            ", sevenfivechg=" + sevenfivechg +
            ", onehundredfifty=" + onehundredfifty +
            ", onehundredfiftychg=" + onehundredfiftychg +
            ", twohundred=" + twohundred +
            ", twohundredchg=" + twohundredchg +
            ", fourhundred=" + fourhundred +
            ", fourhundredchg=" + fourhundredchg +
            ", avgup=" + avgup +
            ", avgdown=" + avgdown +
            ", rsi=" + rsi +
            ", rsichg=" + rsichg +
            ", rsiasx=" + rsiasx +
            ", relativestrenght=" + relativestrenght +
            ", avgupvol=" + avgupvol +
            ", avgdownvol=" + avgdownvol +
            ", rsivol=" + rsivol +
            '}';
  }
}
