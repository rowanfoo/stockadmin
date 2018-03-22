package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.data.entity.CoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface StockRepo extends JpaRepository<CoreStock,Long> ,StockCustomRepo, QuerydslPredicateExecutor<CoreStock> {


}
