package com.dana.admin.stockadmin.service.admin;

import com.dana.admin.stockadmin.data.entity.CoreData;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CalcRSI extends BaseAdmin {
    @Override
    public void run(){

        System.out.println(" --------- calcRSI START -----");
        int periodLength = 14;
        for (String code :  allasxcodes) {


            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(code+".AX") ;

            CoreData coreDataToday =coreData.get(0);
            CoreData coreDataYest =coreData.get(1);

            Double today  = coreDataToday.getClose() ;
            Double yest =   coreDataYest.getClose();
            Double avgUp=coreDataYest.getAvgup();
            Double avgDown = coreDataYest.getAvgdown();
            Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
            Double gains = Math.max(0, change);
            Double losses = Math.max(0, -change);

            avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
            avgDown = ((avgDown * (periodLength - 1)) + losses) / (periodLength);
            Double rsi = 100 - (100 / (1 + (avgUp / avgDown)));


            coreDataToday.setAvgup(avgUp);
            coreDataToday.setAvgdown(avgDown);

            coreDataToday.setRsi(rsi );
            datarepo.save(coreDataToday);


        }
        System.out.println("--------------DONE -------->CalcRSI");

    }


}
