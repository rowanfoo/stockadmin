package com.dana.admin.stockadmin.controller.data;

import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.service.data.CoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class CoreDataController {

    @Autowired
    CoreDataService coreDataService;

///get all ? code , data_when
    @RequestMapping()
    //@CrossOrigin(origins = "http://localhost:8090")
    @CrossOrigin
    public Iterable<CoreData> getDataFrom( @RequestParam String code,@RequestParam String date ){

        return coreDataService.getDataFrom(code,date );
    }



}
