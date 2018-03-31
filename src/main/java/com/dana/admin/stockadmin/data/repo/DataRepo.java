package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.CoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface DataRepo extends JpaRepository<CoreData,Long> ,IDataAlgo , QuerydslPredicateExecutor<CoreData> {

    List<CoreData> findByDate(LocalDate date);




//    @Query("SELECT c FROM CoreData c WHERE c.code = :searchTerm order by c.date  ")
//     List<CoreData> findLatestTwo(@Param("searchTerm") String searchTerm);

        List<CoreData> findTop2ByCodeOrderByDateDesc(String code);
        List<CoreData> findTop1ByOrderByDateDesc();

    @Query(value = "SELECT  Avg(close)as data   FROM core_data where date >= :date and code=:code ",
            nativeQuery = true )
    List<Double> findAveragePrice(@Param("code") String code,@Param("date") String date);

    @Query(value = "SELECT  Avg(volume)as data   FROM core_data where date >= :date and code=:code ",
            nativeQuery = true )
    List<BigDecimal> findAverageVolume(@Param("code") String code, @Param("date") String date);



    //@Query
    //List<CoreData> findByfiftychgfile(@Param("value") double value);


}
