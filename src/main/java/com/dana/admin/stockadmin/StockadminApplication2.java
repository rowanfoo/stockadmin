package com.dana.admin.stockadmin;

import com.dana.admin.stockadmin.data.repo.DataRepo;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {StockadminApplication.class, Jsr310JpaConverters.class}
)
@EnableScheduling
public class StockadminApplication2 extends SpringBootServletInitializer implements CommandLineRunner {
    //@Value( "${asx.pathfile}" )
///	private String asxpath;

    @Autowired
    private ApplicationContext context;
    @Autowired
    DataRepo datarepo;

    @Bean
    public ArrayList<String> allasxcodes(){
        ArrayList allcodes = new ArrayList<String>();


        try {
//			H:\GIT\ASXCodes.txt
            //System.out.println("-------------------------asx file path-->"+asxpath);


            InputStream stream = StockadminApplication.class.getResourceAsStream("ASXCodes.txt");

//			Scanner scanner = new Scanner(new File(  asxpath ));

            Scanner scanner = new Scanner( stream );
            System.out.println("scannner ok --->");
            scanner.useDelimiter(",");
            //System.out.println("scannner ok1 --->");

            while(scanner.hasNext()){
                //	System.out.println("scannner a --->");

                allcodes.add( scanner.next().replaceAll("\\r|\\n", "").trim().toUpperCase() );
            }
            System.out.println("scannner doene --->");

            scanner.close();
        } catch (Exception e) {
            System.out.println(" " + e);
        }

        System.out.println(" getAllCodeFileToRun codes " +allcodes.size() );
        return 	allcodes;



    }
//	@Bean
//	public LocalDate lastdatadate(){
//		System.out.println("-----------------lastdatadate " );
//
//		List<CoreData>  data = datarepo. findTop1ByOrderByDateDesc();
//		LocalDate date = data.get(0).getDate();
//		System.out.println("LATEST DATE  " + date);
//		return date;
//
//	}


//	@EnableWebMvc
//	@Configuration
//	public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {
//
//		private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB
//
//		@Bean
//		public InternalResourceViewResolver viewResolver() {
//			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//			//viewResolver.setViewClass(JstlView.class);
//			viewResolver.setPrefix("/WEB-INF/jsp/");
//			viewResolver.setSuffix(".jsp");
//			return viewResolver;
//		}
//
//
//		//upload.jsp
//		@Bean
//		public CommonsMultipartResolver multipartResolver() {
//
//			CommonsMultipartResolver cmr = new CommonsMultipartResolver();
//			cmr.setMaxUploadSize(maxUploadSizeInMb * 2);
//			cmr.setMaxUploadSizePerFile(maxUploadSizeInMb); //bytes
//			return cmr;
//
//		}
//
//	}

//
//	public class MyWebInitializer extends
//			AbstractAnnotationConfigDispatcherServletInitializer {
//
//		@Override
//		protected Class<?>[] getServletConfigClasses() {
//			return new Class[]{SpringWebMvcConfig.class};
//		}
//
//		@Override
//		protected String[] getServletMappings() {
//			return new String[]{"/"};
//		}
//
//		@Override
//		protected Class<?>[] getRootConfigClasses() {
//			return null;
//		}
//
//	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app){

        return app.sources(StockadminApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {

//		String names[] =context.getBeanDefinitionNames();
//		for (String name :names ) {
//		//	System.out.println("=======>"+name);
//			Object bean  = context.getBean(name);
//			if(bean.getClass().getPackage() != null){
//
//				if (bean.getClass().getPackage().getName().equals("com.mydana.alms.admin") ){
//					System.out.println("packageName =======>"+bean.getClass().getName());
//
//				}
//
//			}
//
//		}

//
//		ClassPathScanningCandidateComponentProvider scanner =
//				new ClassPathScanningCandidateComponentProvider(true);
//
//
//		for (BeanDefinition bd : scanner.findCandidateComponents("com.mydana.alms.admin")){
//			System.out.println("------packages------------------->"+bd.getBeanClassName());
//			System.out.println("------packages------------------->"+bd.  );
//
//			//System.out.println("------packages xx------------------->"+bd.get);
//
////			ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext) context).getBeanFactory();
////			Object bean = clbf.getSingleton(bd.getBeanClassName());
////			System.out.println("------packages xx------------------->"+bean );
////			System.out.println("------packages xx------------------->"+bean.getClass().getName());
//		}


    }

    public static void main(String[] args) {

        SpringApplication.run(StockadminApplication.class, args);
    }
}
