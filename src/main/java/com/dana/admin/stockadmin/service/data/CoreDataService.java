package com.dana.admin.stockadmin.service.data;

import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.data.entity.QCoreData;
import com.dana.admin.stockadmin.data.repo.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CoreDataService {
    @Autowired
    DataRepo dataRepo;

    public Iterable<CoreData> getDataFrom(String code , String date){

        Iterable<CoreData> data =  dataRepo.findAll(QCoreData.coreData.date.gt(LocalDate.parse(date)).and(QCoreData.coreData.code.eq(code)));
        return data;
    }

    @Cacheable("coredata")
      public CoreData getData(String code , String date){
            System.out.println("----------------------- get DATA---------------");
        Optional <CoreData> data =  dataRepo.findOne (
                            QCoreData.coreData.date.eq(LocalDate.parse(date)).
                            and(QCoreData.coreData.code.eq(code)));
            System.out.println("----------------------- OK GOT DATA---------------" + data.get());

        return data.get();
    }


}
