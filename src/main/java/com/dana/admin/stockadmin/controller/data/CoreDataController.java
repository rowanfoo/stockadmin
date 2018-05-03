package com.dana.admin.stockadmin.controller.data;

import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.service.data.CoreDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class CoreDataController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CoreDataService coreDataService;

///get all ? code , data_when
    @RequestMapping()
    //@CrossOrigin(origins = "http://localhost:8090")
    @CrossOrigin
    public Iterable<CoreData> getDataFrom( @RequestParam String code,@RequestParam String date ){
        logger.info("----------------getDataFrom :  code  : ",code );
        return coreDataService.getDataFrom(code,date );
    }



}
