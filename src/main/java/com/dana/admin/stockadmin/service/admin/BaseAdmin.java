package com.dana.admin.stockadmin.service.admin;

import com.dana.admin.stockadmin.data.repo.DataRepo;
import com.dana.admin.stockadmin.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
@Component
public abstract class BaseAdmin {
    LocalDate date;
    @Autowired
    StockRepo repo;

    @Autowired
    DataRepo datarepo;
    @Autowired  ArrayList<String > allasxcodes;

    BaseAdmin(LocalDate date){
        this.date=date;
    }
    BaseAdmin(){
    }


 public abstract void run();



}
