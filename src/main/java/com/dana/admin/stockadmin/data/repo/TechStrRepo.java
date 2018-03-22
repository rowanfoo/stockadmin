package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.TechTechstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TechStrRepo extends JpaRepository<TechTechstr,Long> {

}
