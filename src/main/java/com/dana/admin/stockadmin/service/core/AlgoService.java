package com.dana.admin.stockadmin.service.core;



import com.dana.admin.stockadmin.data.entity.TechTechstr;
import com.dana.admin.stockadmin.data.repo.DataRepo;
import com.dana.admin.stockadmin.data.repo.TechStrRepo;
import com.dana.admin.stockadmin.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgoService {

    @Autowired
    DataRepo dataRepo;

    @Autowired
    TechStrRepo techStrRepo;


//    public void lowRsi(){
//        System.out.println(" --------- findlowRSI -----");
//        List<Object[]> coreData = dataRepo.lowRsi();
//        for (Object[] result : coreData) {
//
//            System.out.println ("LOW RSI---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//            TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 25);
//            str.setChangepercent((Double) result[2]);
//            techStrRepo.save(str);
//        }
//
//    }
//
//
//    public void run(){
//    }
//    public void consequitveDayFallStr(){
//        System.out.println(" --------- consequitveDayFallStr -----");
//        LocalDate mydate = FormatUtil.getWorkDay(LocalDate.now(), 8);
//        List<Object[]> coreData = dataRepo.consequitveDayFallStr(mydate.toString());
//
//        for (Object[] result : coreData) {
//
//            System.out.println ("ConsequitveDayFallStr---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//            TechTechstr str = new TechTechstr((String)result[0] ,LocalDate.now()  , 6);
//
//            str.setClose(((BigInteger) result[1]).doubleValue() );
//            str.setFifty((Double) result[2] );
//            str.setFiftychg((Double) result[3]);
//
//
//            techStrRepo.save(str);
//        }
//
//    }
//
//
//
//        public void volumeGreaterSixMonth(){
//            System.out.println(" --------- volumeGreaterSixMonth -----");
//
//            List<Object[]> coreData = dataRepo.volumeGreaterSixMonth();
//
//            for (Object[] result : coreData) {
//
//                System.out.println ("volumeGreaterSixMonth---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//                TechTechstr str = new TechTechstr((String)result[0] ,((java.sql.Date)result[1]).toLocalDate()  , 11);
//                str.setClose(((BigInteger) result[1]).doubleValue() );
//                techStrRepo.save(str);
//            }
//
//        }
//
//
//        public void volumelargeandPriceReversal() {
//            System.out.println(" --------- volumelargeandPriceReversal -----");
//
//            ArrayList<TechTechstr> coreData = dataRepo.volumelargeandPriceReversal();
//            coreData.forEach((s) -> {
//
//                techStrRepo.save(s);
//
//                });
//
//
//
//        }
    }
