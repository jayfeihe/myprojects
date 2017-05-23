package com.tera.batch.saleRefuse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SaleReTimeFreezeTest {
	public static void main(String[] args) throws Exception {
			
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:/META-INF/spring/batch/jobs/*.xml",
				"classpath*:/org/springframework/batch/admin/web/resources/webapp-config.xml",
				"file:WebRoot/WEB-INF/batchTestDaoContext.xml");
		
		JobLauncher jobLauncher=(JobLauncher) context.getBean("jobLauncher");
		JobRegistry jobRegistry=(JobRegistry) context.getBean("jobRegistry");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("saleReTimeFreezeJob"), param);
	}
}
