package com.dana.admin.stockadmin.service.admin;

import com.dana.admin.stockadmin.data.entity.CoreData;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CalcRSI extends BaseAdmin {
    public void run(){
        System.out.println("---------------------->CalcRSI");
        for (String code :  allasxcodes) {
            System.out.println(" --------- findTopTwoToday -----");
            //List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(code+".AX") ;

            System.out.println(" ********************  DONE findTopTwoToday -----" + coreData.size());

            CoreData coreDataToday =coreData.get(0);
            CoreData coreDataYest =coreData.get(1);
            Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
            coreDataToday.setChanges(change );
            coreDataToday.setChangepercent (change/ coreDataYest.getClose() );
            datarepo.save(coreDataToday);



        }

    }


}
