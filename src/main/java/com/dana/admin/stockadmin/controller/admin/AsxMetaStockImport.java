package com.dana.admin.stockadmin.controller.admin;

import com.dana.admin.stockadmin.data.entity.CoreData;
import com.dana.admin.stockadmin.data.repo.DataRepo;
import com.dana.admin.stockadmin.dto.RunningStatus;
import com.dana.admin.stockadmin.scrap.asx.AsxNews;
import com.dana.admin.stockadmin.service.Algo.AlgoAdminDaily;
import com.dana.admin.stockadmin.service.admin.CalcAverage;
import com.dana.admin.stockadmin.service.admin.CalcRSI;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Controller
public class AsxMetaStockImport {

    @Autowired
    ArrayList<String > allasxcodes;

    @Autowired
    DataRepo datarepo;
    @Autowired
    AlgoAdminDaily algo;
    @Autowired
    CalcAverage calcAverage;
    @Autowired
    CalcRSI calcRSI;
    @Autowired
    RunningStatus runningStatus;
    @Autowired
    AsxNews asxNews;

    /*
    // save uploaded file to this folder
    private static String UPLOADED_FOLDER = "E://temp//";


    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        //try {

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);



        } catch (IOException e) {
            System.out.println("----------Err file ;" +e);
        }
        System.out.println("---------- file  Done" );

        modelMap.addAttribute("message", file.getName());
        return "uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

*/
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public void news( ) {
        System.out.println("----------------------------WEB TRIGGER RUN");

        asxNews.run();

    }

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public void submit( ) {
        System.out.println("----------------------------WEB TRIGGER RUN");
        importAllData();


    }

    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    public void calc( ) {
        System.out.println("----------------------------WEB TRIGGER RUN  CALC" );
        System.out.println("----RUN  CALC data AVERAGE --:");
        calcAverage.run();
        System.out.println("----RUN  AVERAGE DONE   --:");
        calcRSI.run();
        System.out.println("----RUN RSI DONE--:");


    }

    @RequestMapping(value = "/algo", method = RequestMethod.GET)
    public void algo( ) {
        System.out.println("----------------------------WEB TRIGGER RUN  ALGO" );
        System.out.println("----RUN  ALGO --:");
        algo.executeAll();
        System.out.println("----RUN  ALGO DONE   --:");



    }

private  void   insertdata(String code)throws Exception{
    final String uri = "https://www.asx.com.au/asx/1/share/"+code;
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    CoreData node = null;
        node = mapper.readValue(new URL(uri), CoreData.class);
    System.out.println("----data done --:"+node);
        datarepo.save(node);



}

    @Scheduled(cron = "0 15 15 ? * MON-FRI")


    public void  importAllData() {



        runningStatus.setImportstatus("");
       runningStatus.setRsistatus("");
        runningStatus.setAlgostatus("");

        System.out.println("----ASX import RUN !!!!!  --:");
        //allasxcodes.forEach((a)-> System.out.println("----codes--:"+a));
        runningStatus.setImportstatus("running");
        allasxcodes.stream()
                .forEach((a)->{
                    try {
                          TimeUnit.SECONDS.sleep(20);
                        //System.out.println("----ASX import data  --:"+a);
                        insertdata(a);
                    } catch (Exception e) {
                        System.out.println("----ASX import data  --:"+e);
                    }

                });
        System.out.println("----ASX import data   ALL DONE --:");
        System.out.println("----ASX import data   BYE--:");
        datarepo.flush();

        runningStatus.setImportstatus("completed");
        System.out.println("----RUN  CALC data  --:");

        runningStatus.setAveragestatus("running");
        calcAverage.run();
        runningStatus.setAveragestatus("completed");


        System.out.println("----RUN  AVERAGE DONE   --:");
        runningStatus.setRsistatus("running");
        calcRSI.run();
        runningStatus.setRsistatus("completed");
        System.out.println("----RUN ALGO --:");
        runningStatus.setAlgostatus("running");
        algo.executeAll();
        runningStatus.setAlgostatus("completed");
        asxNews.run();

    }





}
