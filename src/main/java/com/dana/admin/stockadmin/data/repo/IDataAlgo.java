package com.dana.admin.stockadmin.data.repo;


import com.dana.admin.stockadmin.dto.MovingAverage;
import com.dana.admin.stockadmin.data.entity.TechTechstr;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public interface IDataAlgo {

    //List<Object[]>  lowRsi();
   // ArrayList<TechTechstr> volumelargeandPriceReversal();
   // List<Object[]>  down4Percent();
    //List<Object[]>  fallWithLowVolumeStr();
    // List<Object[]>  twoHundredCrossFourHundred(double start , double end );
    //List<Object[]>  greaterVolAvg(int multiply);
    // List<Object[]>  fiftyDayless(double  fiftydaylesspercent);
    //List<Object[]>  volumeGreaterSixMonth();

    List<Object[]>  breakRoundNumber(String code);
    List<Object[]>  downGreater40Percent(String code);
    List<Object[]>  consequitveDayFallStr(String date);


    List<Object[]>  fiftyDayDistance();

    List<Object[]>  movingAveragebetween(MovingAverage movingAverage, double start , double end );

    // LocalDate getDate() throws Exception;

}
