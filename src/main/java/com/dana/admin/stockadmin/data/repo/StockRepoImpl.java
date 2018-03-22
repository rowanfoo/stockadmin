package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.data.entity.CoreStock;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
@Repository
public class StockRepoImpl implements StockCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;
    private SimpleJpaRepository<CoreStock, Long> repository;
    @PostConstruct
    public void init() {
        System.out.println( "----------------StockCustomRepoImpl");
        System.out.println( "----------------------------StockCustomRepoImpl ---"+ entityManager.getMetamodel().getEntities() );

        JpaEntityInformation<CoreStock, Long> contactEntityInfo = new JpaMetamodelEntityInformation<CoreStock, Long>(CoreStock.class, entityManager.getMetamodel());
        repository = new SimpleJpaRepository<CoreStock, Long>(contactEntityInfo, entityManager);
    }


    @Override
    public List<CoreStock> getMyId(String id) {
        System.out.println("----------------my name ");
        return repository.findAllById(Arrays.asList(1L) );
    }

    @Override
    public List<CoreStock> geStockName(String name) {

        Query query = entityManager.createNativeQuery("SELECT * FROM core_stock " +
                "WHERE name LIKE ?", CoreStock.class);
        query.setParameter(1, "%"+name + "%");
        return query.getResultList();
    }


}
