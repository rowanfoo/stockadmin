package com.dana.admin.stockadmin.service.admin;

import com.dana.admin.stockadmin.data.entity.CoreData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Component
public class CalcChangePercent extends BaseAdmin {

    LocalDate date;

    @Override
    public  void run() {
        System.out.println("---------------------->CalcChangePercent");

        for (String code :  allasxcodes) {
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
