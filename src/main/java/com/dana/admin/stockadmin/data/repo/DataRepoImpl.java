package com.dana.admin.stockadmin.data.repo;

import com.dana.admin.stockadmin.dto.MovingAverage;
import com.dana.admin.stockadmin.data.entity.CoreStock;
import com.dana.admin.stockadmin.data.entity.TechTechstr;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class DataRepoImpl implements IDataAlgo {


LoadingCache<String, LocalDate> cache = CacheBuilder.newBuilder()
        .expireAfterAccess(4, TimeUnit.HOURS)
        .build(new CacheLoader<String, LocalDate>() {
            @Override
            public LocalDate load(String arg0) throws Exception {
                System.out.println("---------------cachebuilder add :" +arg0);
                return  getCurrDate();
            }
        });

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

    public LocalDate getDate() throws Exception{
        return   cache.get("localdate");
    }


    @Override
    public List<Object[]>  breakRoundNumber(String code){
        String mysql =" SELECT A.CLOSE as today, B.CLOSE as yesterday  FROM  ( SELECT floor(close) as close from core_data where code=? ORDER BY date desc LIMIT 0,1) AS A, (SELECT floor(close) as close from core_data where code=? ORDER BY date desc LIMIT 1,1 ) AS B  WHERE A.CLOSE < B.CLOSE ";
//// TODO: 2/24/2018 have to test , change to use order by date rather than currdate()

        Query query = entityManager.createNativeQuery(mysql);
        query.setParameter(1, code);
        query.setParameter(2, code);

        return query.getResultList();
    }




    @Override
    public List<Object[]>  consequitveDayFallStr(String date){
        String mysql = "SELECT code, max(close) as mymax, min(close), count(*) count " +
                " FROM core_data where date >=? and changePercent<0 group by code  having count >=6";
        Query query = entityManager.createNativeQuery(mysql);

        query.setParameter(1, date);



        return query.getResultList();
    }




//    @Override
//    public List<Object[]>  volumeGreaterSixMonth(){
//        //// TODO: 2/27/2018  not implemented in JSP to detech high volume
//        String mysql = " select code,date,close,open,low,high,changepercent FROM  data  where "+
//               "date=? and  volume > (Avg3mth * 4 )";
//        Query query = entityManager.createNativeQuery(mysql);
//        query.setParameter(1, getCurrDate());
//        return query.getResultList();
//    }//done

//
//    public ArrayList <TechTechstr>   volumelargeandPriceReversal(){
//        //// TODO: 2/27/2018  not implemented in JSP to detech high volume
//        String mysql = " select code,date,close,open,low,high,changepercent FROM  data  where "+
//                "date=? and  volume > (Avg3mth * 2 )";
//        Query query = entityManager.createNativeQuery(mysql);
//        query.setParameter(1, getCurrDate());
//        List<Object[]>  coreData = query.getResultList();
//        ArrayList <TechTechstr> arr = new ArrayList<>();
//        for (Object[] result : coreData) {
//
//            System.out.println ("Data Repo volumelargeandPriceReversal---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//            TechTechstr str = new TechTechstr((String)result[0] ,((java.sql.Date)result[1]).toLocalDate()  , 11);
//
//            String mysql1 ="INSERT INTO techstr (code,date,mode,close,lowlow) VALUES (?,?,?,?,?)";
//
//            double close=(Double) result[2];
//            double low=(Double) result[3];
//            double open=(Double) result[4];
//            double high=(Double) result[5];
//            double changepercent=(Double) result[6];
//
//            if( close > ((low+high    )/2 )    ){
//
//                str.setClose(close );
//                str.setLowlow(""+low);
//                arr.add(str);
//            }
//
//
//        }
//
//
//        return arr;
//    }//done




    @Override
    public List<Object[]>  downGreater40Percent(String code){
        // get max price from last 12 month
        //vs today price ,  ?? down >30%
        String mysql =" select (select low from core_data where code=?  and date= ? ) as t," +
                "(SELECT max(low) as z " +
                " FROM core_data where date > curdate()-interval 12 month  and  code=?) as y"  ;

        Query query = entityManager.createNativeQuery(mysql);

        query.setParameter(1, code);
        query.setParameter(2, getCurrDate());
        query.setParameter(3, code);

        return query.getResultList();
    }



    @Override
    //50dAvg compare to past 3 years 50d AVG
    //find <2x of 3year 50d , fall very fast

    public  List<Object[]>  fiftyDayDistance() {
        String mysql ="SELECT code,date,close,fifty, format(fiftychg*100,2) as fiftyDchg "+
                "FROM  core_data as dd  where date= ? " +
                " and fiftychg < ( (SELECT avg(fiftychg) as fiftychg  FROM core_data " +
                "where code=dd.code and  date >= curdate() - interval 3 year and fiftychg < 0 ) *2 ) ";


        Query query = entityManager.createNativeQuery(mysql);
        query.setParameter(1, getCurrDate());
        return query.getResultList();
    }

//    @Override
//    //lets you say how much current price below 50d avg
//    public  List<Object[]>  fiftyDayless(double fiftydaylesspercent){
//        String mysql ="SELECT code,date, close , fifty,   format(fiftychg  *100,2) as " +
//                "fiftyDchg FROM core_data where fiftychg < ? and date= ? " +
//                "order by fiftychg  ASC";
//
//
//        Query query = entityManager.createNativeQuery(mysql);
//        query.setParameter(1, fiftydaylesspercent);
//        query.setParameter(2, getCurrDate());
//
//        return query.getResultList();
//    }//done
//
//    @Override
//    //lets you say how much current price below 50d avg
//    public  List<Object[]>  greaterVolAvg(int multiply){
//
//        String mysql ="select code,date,close,volume ,Avg3mth,changePercent   "+
//                "FROM  core_data  where date=? and  volume > (Avg3mth * ? )";
//
//
//
//        Query query = entityManager.createNativeQuery(mysql);
//        query.setParameter(1, getCurrDate());
//        query.setParameter(2, multiply);
//
//        return query.getResultList();
//    }



    @Override
    public  List<Object[]>  movingAveragebetween(MovingAverage movingAverage, double start , double end ){

        String mysql = "SELECT code,date,close,? FROM core_data  where date=? and " +
                " ? between ?  and  ?";
        System.out.println("-------------> call movingAveragebetween ");
        Query query = entityManager.createNativeQuery(mysql);
        query.setParameter(1, movingAverage.name);
        query.setParameter(2, getCurrDate());
        query.setParameter(3, movingAverage.name);

        query.setParameter(4, start);
        query.setParameter(5, end);


        return query.getResultList();
    }


    // need to use last record instead of currdate , as if use on weekend  SAT or SUN then no record will be selected
    private LocalDate getCurrDate(){
        String mysql = "select date from core_data order by date desc limit 1  ";
        Query query = entityManager.createNativeQuery(mysql);
        //System.out.println("-------------> call getCurrDate ");
        List<Object[]> coreData = query.getResultList();
        Object date = coreData.get(0) ;
     //   System.out.println("-------------> call getCurrDate " + ((java.sql.Date)date ).toLocalDate());
        return ((java.sql.Date)date ).toLocalDate();

    }
/**
 *
 *    @Override
public List<Object[]>  twoHundredCrossFourHundred(double start , double end ){
String mysql = "SELECT code,date,twohundredchg FROM core_data where date=? and ((twohundred - fourhundred )/fourhundred ) between ? and ?";
Query query = entityManager.createNativeQuery(mysql);
query.setParameter(1, getCurrDate());
query.setParameter(2, start);
query.setParameter(3, end);



return query.getResultList();

}//done
 @Override
 public List<Object[]>  down4Percent(){
 String mysql ="SELECT code,date,close,format(fifty,2) as fiftyd,changePercent , format(fiftychg*100,2) as fiftyDchg   "+
 "FROM  core_data  where date= ?  and changePercent < -0.04";



 Query query = entityManager.createNativeQuery(mysql);
 query.setParameter(1, getCurrDate());
 return query.getResultList();
 }//done


 @Override
 public List<Object[]>  lowRsi(){
 String mysql =" select code,date,rsi from core_data where date=? " +
 "and rsi < 28";
 //// TODO: 2/24/2018 have to test , change to use order by date rather than currdate()

 Query query = entityManager.createNativeQuery(mysql);
 query.setParameter(1, getCurrDate());
 return query.getResultList();
 }//done



 *
 */

}
