package com.dana.admin.stockadmin;

import com.dana.admin.stockadmin.controller.admin.AsxMetaStockImport;
import com.dana.admin.stockadmin.data.entity.QCoreData;
import com.dana.admin.stockadmin.dto.MovingAverage;
import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.data.entity.TechTechstr;
import com.dana.admin.stockadmin.data.repo.DataRepo;
import com.dana.admin.stockadmin.data.repo.StockRepo;
import com.dana.admin.stockadmin.data.repo.TechStrRepo;
import com.dana.admin.stockadmin.service.core.AlgoService;
import com.dana.admin.stockadmin.util.FormatUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.net.URL;
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockadminApplicationTests {

	@Autowired
	StockRepo repo;

	@Autowired
	DataRepo datarepo;


	@Autowired
	TechStrRepo techStrRepo;

	@Autowired
	AlgoService algoService;

	@Autowired
	ArrayList<String > allasxcodes;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	AsxMetaStockImport asximport;


	LoadingCache<String, LocalDate> cache = CacheBuilder.newBuilder()
			.expireAfterAccess(4, TimeUnit.HOURS)
			.build(new CacheLoader<String, LocalDate>() {
				@Override
				public LocalDate load(String arg0) throws Exception {
					System.out.println("---------------cachebuilder add :" +arg0);
					return  getLatestDate();
				}
			});

	@Test
	public void contextLoads() {
		System.out.println(" --------- contextLoads -----");
		//CoreStock stk = repo.findOne(1L);
	//	System.out.println(" --------- stk -----"+stk.getName());

//		List<com.mydana.alms.entity.CoreStock> ls = repo.getMyId("");
//
//		ls.forEach(a->{
//			System.out.println(" stocks  " +a.getName());
//		});
//
//
//		List<com.mydana.alms.entity.CoreStock> lsa = repo.geStockName("A");
//		lsa.forEach(a->{
//			System.out.println(" stocks with A " +a.getName());
//		});



	}
	@Test
	public void saveDataDate() {
		System.out.println(" --------- saveDataDate -----");
		CoreData data  = new  CoreData() ;
		data.setCode("BHP.ax");
		data.setDate(LocalDate.now());

		datarepo.save(data);
		System.out.println(" ********************  DONE saveDataDate -----");
	}


	@Test
	public void findByDate() {
		System.out.println(" --------- findByDate -----");
		List<CoreData> coreData = datarepo.findByDate(LocalDate.now().minusDays(1)) ;
		System.out.println(" ********************  DONE findByDate -----" + coreData.size());
	}


	@Test
	public void findTopTwoToday() {
		System.out.println(" --------- findTopTwoToday -----");
		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		System.out.println(" ********************  DONE findTopTwoToday -----" + coreData.size());

		CoreData coreDataToday =coreData.get(0);
		CoreData coreDataYest =coreData.get(1);
		Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
		coreDataToday.setChanges(change );
		coreDataToday.setChangepercent (change/ coreDataYest.getClose() );
		datarepo.save(coreDataToday);





	}

	@Test
	public void calcRSI() {
		int periodLength = 14;
		System.out.println(" --------- calcRSI -----");
		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		System.out.println(" ********************  DONE calcRSI -----" + coreData.size());

		CoreData coreDataToday =coreData.get(0);
		CoreData coreDataYest =coreData.get(1);

		Double today  = coreDataToday.getClose() ;
		Double yest =   coreDataYest.getClose();
		Double avgUp=coreDataYest.getAvgup();
		Double avgDown = coreDataYest.getAvgdown();
		Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
		Double gains = Math.max(0, change);
		Double losses = Math.max(0, -change);

		avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
		avgDown = ((avgDown * (periodLength - 1)) + losses) / (periodLength);
		Double rsi = 100 - (100 / (1 + (avgUp / avgDown)));


		coreDataToday.setAvgup(avgUp);
		coreDataToday.setAvgdown(avgDown);

		coreDataToday.setRsi(rsi );
		datarepo.save(coreDataToday);





	}


	@Test
	public void getAverage() {
		System.out.println(" --------- getAverage -----");

		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		CoreData coreDataToday =coreData.get(0);


		//List<Object[]> data = datarepo.findAverage("BHP.AX","2018-01-01" );

		String code ="RIO.AX";

		Double close  = coreDataToday.getClose();
		LocalDate date75 = FormatUtil.getWorkDay(coreDataToday.getDate(), 75);
		LocalDate date20 = FormatUtil.getWorkDay(coreDataToday.getDate(), 20);
		LocalDate date40 = FormatUtil.getWorkDay(coreDataToday.getDate(), 40);
		LocalDate date150 = FormatUtil.getWorkDay(coreDataToday.getDate(), 150);
		LocalDate date400 = FormatUtil.getWorkDay(coreDataToday.getDate(), 400);
		LocalDate date200 = FormatUtil.getWorkDay(coreDataToday.getDate(), 200);
		LocalDate date50 = FormatUtil.getWorkDay(coreDataToday.getDate(), 50);
		LocalDate date60 = FormatUtil.getWorkDay(coreDataToday.getDate(), 60);

		//Double  data = datarepo.findAveragePrice(code,date20.toString() ).get(0);

		// System.out.println(" --------- getAverage -----: "+ data[0]);


//		System.out.println(" --------- getAverage -----: "+ datarepo.findAveragePrice(code,date20.toString() ).get(0)[0] );


		Double twenty =(Double)datarepo.findAveragePrice(code,date20.toString() ).get(0);
		Double fourty =(Double)datarepo.findAveragePrice(code,date40.toString() ).get(0);
		Double fifty =(Double)datarepo.findAveragePrice(code,date50.toString() ).get(0);
		Double sevenfive =(Double)datarepo.findAveragePrice(code,date75.toString() ).get(0);
		Double onehundredfifty =(Double)datarepo.findAveragePrice(code,date150.toString() ).get(0);
		Double twohundred =(Double)datarepo.findAveragePrice(code,date200.toString() ).get(0);
		Double fourhundred =(Double)datarepo.findAveragePrice(code,date400.toString() ).get(0);





		coreDataToday.setTwenty( twenty);
		coreDataToday.setTwentychg((close-twenty)/twenty   );

		coreDataToday.setFourty(fourty );
		coreDataToday.setFourtychg((close-fourty)/fourty   );

		coreDataToday.setFifty(fifty);
		coreDataToday.setFiftychg((close-fifty)/fifty   );


		coreDataToday.setSevenfive(sevenfive);
		coreDataToday.setSevenfivechg((close-sevenfive)/sevenfive   );

		coreDataToday.setOnehundredfifty(onehundredfifty );
		coreDataToday.setOnehundredfiftychg((close-onehundredfifty)/onehundredfifty   );

		coreDataToday.setTwohundred( twohundred);
		coreDataToday.setTwohundredchg((close-twohundred)/twohundred   );

		coreDataToday.setFourhundred( fourhundred);
		coreDataToday.setFourhundredchg ((close-fourhundred)/fourhundred   );

		coreDataToday.setAvg3mth(datarepo.findAverageVolume(code,date60.toString() ).get(0) .longValue() );


		datarepo.save(coreDataToday);
		//ps1.setString(15, date60.toString());
		//ps1.setString(16, obj.getCode() );



		//System.out.println(" --------- getAverage -----: "+ data);


		System.out.println(" ********************  getAverage  DONE ****************");

	}
//
//	@Test
//	public void findByfiftychgfileFromFile () {
//		System.out.println(" --------- findByfiftychgfileFromFile -----");
//		List<CoreData> coreData = datarepo.findByfiftychgfile(40.0d) ;
//
//		System.out.println(" ********************  DONE findTopTwoToday -----" + coreData.size());
//
//
//
//
//
//
//	}


	@Test
	public void findlowRSI () {
//		System.out.println(" --------- findlowRSI -----");
//		List<Object[]> coreData = datarepo.lowRsi();
//
//
//
//
//		for (Object[] result : coreData) {
//
//
//
//			System.out.println ("LOW RSI---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 25);
//			str.setChangepercent((Double) result[2]);
//			techStrRepo.save(str);
//		}
//
//
//
//		System.out.println(" ********************  DONE findlowRSI -----" + coreData.size());
//
//




	}




	@Test
	public void consequitveDayFallStr () {
		//algoService.consequitveDayFallStr();
	}

	@Test
	public void  down4PercenteEst() {
//
//		List<Object[]> coreData = datarepo.down4Percent();
//		for (Object[] result : coreData) {
//			System.out.println ("down4PercenteEst ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 9);
//			str.setClose((Double) result[2]);
//			str.setFifty(   Double.parseDouble ((String) result[3]));
//			str.setFiftychg((Float) result[4]);
//			str.setChangepercent( Double.parseDouble ( result[5]+"" ));
//
//			//     techStrRepo.save(str);
//		}


	}
	@Test
	public void  fallWithLowVolumeStr() {
//		List<Object[]> coreData = datarepo.fallWithLowVolumeStr();
//		for (Object[] result : coreData) {
//			System.out.println ("fallWithLowVolumeStr ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 7);
//			str.setChangepercent(Double.parseDouble( result[2]+""));
//			str.setVolume(((BigInteger) result[3]).longValue());
//			str.setThreemthvol(   Double.parseDouble (result[4]+""));
//
//			//     techStrRepo.save(str);
//			System.out.println ("fallWithLowVolumeStr ---------------> "+str);
//
//		}

	}

	@Test
	public void  fiftyDayDistance() {
		List<Object[]> coreData = datarepo.fiftyDayDistance();
		for (Object[] result : coreData) {
			System.out.println ("fiftyDayDistance ---------------> "+result[0] + " " + result[1] + " - " + result[2]);


			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 2);
			str.setClose(Double.parseDouble( result[2]+""));
			str.setFifty(Double.parseDouble( result[3]+"") );
			str.setFiftychg(Double.parseDouble( result[4]+""));

			//     techStrRepo.save(str);
			System.out.println ("fiftyDayDistance ---------------> "+str);

		}

	}
	@Test
	public  void   fiftyDayless(){
//		List<Object[]> coreData = datarepo.fiftyDayless(0.2);
//		for (Object[] result : coreData) {
//			System.out.println ("fiftyDayless ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 1);
//			str.setClose(Double.parseDouble( result[2]+""));
//			str.setFifty(Double.parseDouble( result[3]+"") );
//			str.setFiftychg(Double.parseDouble( result[4]+""));
//
//			//     techStrRepo.save(str);
//			System.out.println ("fiftyDayless ---------------> "+str);
//
//		}

	}


	@Test
	public void  movingAveragetwoHundred(){
//		List<Object[]> coreData = datarepo.movingAveragebetween(MovingAverage.onehundredfifty,0.005,0.05);
//
//		for (Object[] result : coreData) {
//			System.out.println ("movingAveragetonehundredfifty ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//			String mysql1 ="INSERT INTO techstr (code,date ,mode,close,twohundredchg) VALUES (?,?,31,?,?)"  ;
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 31);
//			str.setClose(Double.parseDouble( result[1]+""));
//			str.setTwohundredchg(Double.parseDouble( result[2]+""));
//
//			//     techStrRepo.save(str);
//			System.out.println ("movingAverageonehundredfifty ---------------> "+str);
//
//		}
//		coreData = datarepo.movingAveragebetween(MovingAverage.twenty,0.005,0.05);
//
//		for (Object[] result : coreData) {
//			System.out.println ("movingAveragetwenty ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//			String mysql1 ="INSERT INTO techstr (code,date ,mode,close,twohundredchg) VALUES (?,?,29,?,?)"  ;
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 29);
//			str.setClose(Double.parseDouble( result[1]+""));
//			str.setTwohundredchg(Double.parseDouble( result[2]+""));
//
//			//     techStrRepo.save(str);
//			System.out.println ("movingAveragetwenty---------------> "+str);
//
//		}


	}


	@Test
	public void  greaterVolAvg(){
//		List<Object[]> coreData = datarepo.greaterVolAvg(2);
//
//		for (Object[] result : coreData) {
//			System.out.println ("greaterVolAvg ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 3);
//			str.setClose(Double.parseDouble( result[2]+""));
//			str.setVolume(((BigInteger) result[3]).longValue());
//			str.setThreemthvol(   Double.parseDouble (result[4]+""));
//			str.setChangepercent(Double.parseDouble( result[5]+""));
//
//
//
//
//
//
//			//     techStrRepo.save(str);
//			System.out.println ("greaterVolAvg ---------------> "+str);
//
//		}



	}
	@Test
	public void twoHundredCrossFourHundred(){
//		List<Object[]> coreData = datarepo.twoHundredCrossFourHundred(0,0.05);
//
//		for (Object[] result : coreData) {
//			System.out.println ("twoHundredCrossFourHundred ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//			String mysql1 = "INSERT INTO techstr (code,date,mode,twohundredchg) VALUES (?,?,32,?)";
//
//			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 32);
//			str.setTwohundredchg(Double.parseDouble( result[2]+""));
//
//			//     techStrRepo.save(str);
//			System.out.println ("twoHundredCrossFourHundred ---------------> "+str);
//
//		}
	}



	@Test
	public void  importData() {
		final String uri = "https://www.asx.com.au/asx/1/share/fmg";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			CoreData node = mapper.readValue(new URL(uri), CoreData.class);
			System.out.println("---------importData-------->  "+node );
			//	datarepo.save(node);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void  importAllData() {

		//allasxcodes.forEach((a)-> System.out.println("----codes--:"+a));

		allasxcodes.stream()
				.limit(10)
				.forEach((a)->{

//			importcode(a);
					try {
						TimeUnit.SECONDS.sleep(10);
						System.out.println("----codes--:"+a);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				});

	}

	private void  importcode(String code) {
		final String uri = "https://www.asx.com.au/asx/1/share/"+code;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			CoreData node = mapper.readValue(new URL(uri), CoreData.class);
			System.out.println("---------importData-------->  "+node );
			//	datarepo.save(node);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void  queryDslvolumeGreaterSixMonth() {
		System.out.println("=======queryDslvolumeGreaterSixMonth====" );
		Iterable<CoreData>  data = datarepo.findAll(
				QCoreData.coreData.volume.gt(QCoreData.coreData.avg3mth.multiply(3))
						.and( QCoreData.coreData.date.eq(getLatestDate()))
				);
		data.forEach((a)-> System.out.println("=======queryDslvolumeGreaterSixMonth====>" + a));

	}



	@Test
	public void  queryDsl() {
		System.out.println("---------queryDsl-------->  " );

		String mysql ="select code,date,changePercent,volume ,Avg3mth FROM  core_data  where date= ? " +
				"  and  volume < (Avg3mth * 0.6 ) and changePercent < -0.04";
		Iterable<CoreData>  data  = datarepo.findAll(
				(QCoreData.coreData.volume.lt(QCoreData.coreData.avg3mth.multiply(0.6))
				.and(QCoreData.coreData.changepercent.lt(-0.04)    )
								.and (QCoreData.coreData.date.eq (
										JPAExpressions.select(QCoreData.coreData.date.max())
										.from(QCoreData.coreData)
										.orderBy( QCoreData.coreData.date.desc())


				) ) ) ) ;


		System.out.println("---------queryDsl------data-->  ");
		data.forEach((a)-> System.out.println("=======core====>" + a));


	}
	@Test
	public void  fallWithLowVolumeStrReplace() {
//		List<Object[]> coreData = datarepo.fallWithLowVolumeStr();
//		for (Object[] result : coreData) {
//			System.out.println ("fallWithLowVolumeStr ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//		}

	}


	@Test
	public void  queryDslLowRSI(){
		String mysql =" select code,date,rsi from core_data where date=? " +
				"and rsi < 28";
		System.out.println("---------queryDslLowRSI-------->  " );
		Iterable<CoreData>  data  = datarepo.findAll(
				QCoreData.coreData.rsi.lt(28)
				.and (QCoreData.coreData.date.eq (getLatestDate()))
				);
		data.forEach((a)-> System.out.println("=======queryDslLowRSI====>" + a +" : RSI :" + a.getRsi()));

	}



	@Test
	public void  queryDslfiftyDayless(){
		System.out.println("---------queryDslfiftyDayless-------->  " );
		Iterable<CoreData>  data  = datarepo.findAll(
				QCoreData.coreData.fiftychg.lt(0.2)
						.and (QCoreData.coreData.date.eq (getLatestDate()))
		);
		data.forEach((a)-> System.out.println("=======queryDslfiftyDayless====>" ));

	}



	@Test
	public void  queryDsltwoHundredCrossFourHundred(){
		String mysql = "SELECT code,date,twohundredchg FROM core_data where date=? and ((twohundred - fourhundred )/fourhundred ) between ? and ?";

		System.out.println("---------queryDsltwoHundredCrossFourHundred-------->  " );
		NumberPath<Double> fourhundred = QCoreData.coreData.fourhundred;
		NumberPath<Double> twohundred = QCoreData.coreData.twohundred;


		Iterable<CoreData>  data  = datarepo.findAll(
				twohundred.subtract(fourhundred).divide(fourhundred).between(0.01, 0.05)
				.and (QCoreData.coreData.date.eq (getLatestDate()))
		);
		data.forEach((a)->{
			TechTechstr str = new TechTechstr(a.getCode() ,a.getDate() , 32);
			str.setTwohundredchg(a.getTwohundredchg() );
			//     techStrRepo.save(str);
		});
	}
	@Test
	public void  priceReversal(){
		String mysql = " select code,date,close,open,low,high,changepercent FROM  data  where "+
				"date=? and  volume > (Avg3mth * 2 )";
		System.out.println("---------priceReversal-------->  " );
		// you want it bounce from low ,
		NumberPath<Double> close = QCoreData.coreData.close;
		NumberPath<Double> low = QCoreData.coreData.low;
		NumberPath<Double> high = QCoreData.coreData.high;


		Iterable<CoreData>  data  = datarepo.findAll(
				close.ne(low)
				.and( (high.subtract(low).divide(close.subtract(low)).gt(0.7)  ))
						.and (QCoreData.coreData.date.eq (getLatestDate()))
		);
		data.forEach((a)->{
			System.out.println("---------priceReversal-------->  "+a  +" HIGH : "+ a.getHigh() +" :LOW: " + a.getLow() );
			TechTechstr str = new TechTechstr(a.getCode() ,a.getDate() , 11);
			//str.setTwohundredchg(a.getTwohundredchg() );
			//     techStrRepo.save(str);
		});
	}




	private LocalDate getLatestDate(){
//		LocalDate date =  datarepo.findOne(QCoreData.coreData.date.eq(QCoreData.coreData.date.max()) ).get().getDate();

//		LocalDate date1 =JPAExpressions.select(QCoreData.coreData.date.max()).from(QCoreData.coreData).fetchFirst();
	//	List<LocalDate> date1 =JPAExpressions.select(QCoreData.coreData.date.max()).from(QCoreData.coreData).fetch();

		JPAQuery query = new JPAQuery(entityManager);
		Object obj = query.from(QCoreData.coreData).select(QCoreData.coreData.date.max()   ).fetchOne();
		//query.from(QCoreData.coreData).select(QCoreData.coreData.date.max()   )
		System.out.println("---------getLatestDate------data-->  "+obj);
		return (LocalDate)obj ;

	}
	@Test
	public void breakRoundNumber() {
		System.out.println ("breakRoundNumber ---------------> ");


			List<Object[]> coreData = datarepo.breakRoundNumber("BHP.AX");

			for (Object[] result : coreData) {
				// System.out.println ("breakRoundNumber ---------------> "+result[0] + " " + result[1] + " - " + result[2]);

				TechTechstr str = new TechTechstr("BHP.AX" , LocalDate.now(), 23);
				//str.setClose(Double.parseDouble( result[2]+""));
				//str.setOpen(Double.parseDouble( result[3]+""));
				str.setClose((Double) result[0]);
				str.setOpen((Double)result[1]);

			//	techStrRepo.save(str);
				System.out.println ("breakRoundNumber ---------------> "+str);

			}

	}
	@Test
	public void downGreater40Percent(){
		System.out.println ("downGreater40Percent ---------------> ");


			List<Object[]> coreData = datarepo.downGreater40Percent("BHP.AX");

			for (Object[] result : coreData) {

				TechTechstr str = new TechTechstr("BHP.AX" , LocalDate.now(), 28);
				double min = Double.parseDouble( result[0]+"");
				double max = Double.parseDouble( result[1]+"");
				double no = (max-min)/max;
				System.out.println ("downGreater40Percent ---------------> no: " + no);
				if(no>0.3){
					str.setNotes( "fall from "+max+" to "+min +" % fall "+Math.round(no*100)  );
					techStrRepo.save(str);

				}
			}

	}


	@Test
	public void  importdata(){
		//asximport.importAllData();
		//asximport.calc();
		asximport.algo();
		//Double twenty =(Double)datarepo.findAveragePrice("BHP.AX",LocalDate.now().toString() ).get(0);


	}

	@Test
	public void  testdata(){
		NumberPath<Double> fourhundred = QCoreData.coreData.fourhundred;
		NumberPath<Double> twohundred = QCoreData.coreData.twohundred;


	//	Iterable<CoreData>  data  = datarepo.findAll(QCoreData.coreData.date.eq(LocalDate.now())		);
		Long count  =  datarepo.count(QCoreData.coreData.date.eq(LocalDate.now()));
		System.out.println("---------COUNT--------------"+count);

		Long countrsi  =  datarepo.count(QCoreData.coreData.date.eq(LocalDate.now()).
				and(QCoreData.coreData.rsi.eq(0.0).or(QCoreData.coreData.rsi.eq(100.00)))
		);
		System.out.println("---------countrsi--------------"+countrsi);

	}


}
