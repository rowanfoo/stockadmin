package com.dana.admin.stockadmin.service.admin;


import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.util.FormatUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
public class CalcAverage extends BaseAdmin {


    @Override
    public void run() {
        System.out.println("---------------------->CalcAverage");
        for (String code :  allasxcodes) {
            String mycode =code+".AX";
            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(mycode) ;
            CoreData coreDataToday =coreData.get(0);

            Double close  = coreDataToday.getClose();
            LocalDate date75 = FormatUtil.getWorkDay(coreDataToday.getDate(), 75);
            LocalDate date20 = FormatUtil.getWorkDay(coreDataToday.getDate(), 20);
            LocalDate date40 = FormatUtil.getWorkDay(coreDataToday.getDate(), 40);
            LocalDate date150 = FormatUtil.getWorkDay(coreDataToday.getDate(), 150);
            LocalDate date400 = FormatUtil.getWorkDay(coreDataToday.getDate(), 400);
            LocalDate date200 = FormatUtil.getWorkDay(coreDataToday.getDate(), 200);
            LocalDate date50 = FormatUtil.getWorkDay(coreDataToday.getDate(), 50);
            LocalDate date60 = FormatUtil.getWorkDay(coreDataToday.getDate(), 60);

            Double twenty =(Double)datarepo.findAveragePrice(mycode,date20.toString() ).get(0);
            Double fourty =(Double)datarepo.findAveragePrice(mycode,date40.toString() ).get(0);
            Double fifty =(Double)datarepo.findAveragePrice(mycode,date50.toString() ).get(0);
            Double sevenfive =(Double)datarepo.findAveragePrice(mycode,date75.toString() ).get(0);
            Double onehundredfifty =(Double)datarepo.findAveragePrice(mycode,date150.toString() ).get(0);
            Double twohundred =(Double)datarepo.findAveragePrice(mycode,date200.toString() ).get(0);
            Double fourhundred =(Double)datarepo.findAveragePrice(mycode,date400.toString() ).get(0);

            coreDataToday.setTwenty( twenty);
            coreDataToday.setTwentychg((close-twenty)/twenty   );

            coreDataToday.setFourty(fourty );
            coreDataToday.setFourtychg((close-fourty)/fourty   );

            coreDataToday.setFifty(fifty);
            coreDataToday.setFiftychg((close-fifty)/fifty   );


            coreDataToday.setSevenfive(sevenfive);
            coreDataToday.setSevenfivechg((close-sevenfive)/sevenfive   );

            coreDataToday.setOnehundredfifty(onehundredfifty );
            coreDataToday.setOnehundredfiftychg((close-onehundredfifty)/onehundredfifty   );

            coreDataToday.setTwohundred( twohundred);
            coreDataToday.setTwohundredchg((close-twohundred)/twohundred   );

            coreDataToday.setFourhundred( fourhundred);
            coreDataToday.setFourhundredchg ((close-fourhundred)/fourhundred   );

            coreDataToday.setAvg3mth((Double)datarepo.findAverageVolume(mycode,date60.toString() ).get(0)  );

            datarepo.save(coreDataToday);


        }
        
        

    }
}
