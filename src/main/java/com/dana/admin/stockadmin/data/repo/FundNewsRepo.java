package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.FundNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FundNewsRepo extends JpaRepository<FundNews,Long>, QuerydslPredicateExecutor<FundNews> {


}


