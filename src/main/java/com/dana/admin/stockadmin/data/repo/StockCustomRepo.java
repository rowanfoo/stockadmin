package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.CoreStock;

import java.util.List;

public interface StockCustomRepo {

     List<CoreStock> getMyId(String name);
     List<CoreStock> geStockName(String name) ;
}
