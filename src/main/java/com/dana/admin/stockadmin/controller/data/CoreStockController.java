package com.dana.admin.stockadmin.controller.data;

import com.dana.admin.stockadmin.data.entity.CoreStock;
import com.dana.admin.stockadmin.data.entity.QCoreStock;
import com.dana.admin.stockadmin.data.repo.StockRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stock")
public class CoreStockController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired  StockRepo stockRepo;

    ///get all ? code , data_when
    @GetMapping("/wishlist")
    @CrossOrigin
    public Iterable<CoreStock> wishlist(){
        return stockRepo.findAll(QCoreStock.coreStock.wishlist.eq("Y"));

    }



}
