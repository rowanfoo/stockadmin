package com.dana.admin.stockadmin.dto;

public enum MovingAverage {

twenty("twentychg"),
    fourty("fourtychg"),
    fifty("fiftychg"),
    seventyfive("seventyfivechg"),
    onehundredfifty("onehundredfiftychg"),
    twohundred("twohundredchg"),
    fourhundred("fourhundredchg");


    public String  name;
    MovingAverage(String name){
        this.name=name;
    }



}
