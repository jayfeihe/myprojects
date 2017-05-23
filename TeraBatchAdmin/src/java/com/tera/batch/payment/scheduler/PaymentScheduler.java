package com.tera.batch.payment.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentScheduler {
	
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PaymentScheduler.class);
	
	@Autowired(required=false)
	private JobRegistry jobRegistry;

	@Autowired(required=false)
	private JobLauncher jobLauncher;
	
	public void autoMatchJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("paymentJob"), param);
//		JobExecution exec = jobLauncher.run(job, param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public JobRegistry getJobRegistry() {
		return jobRegistry;
	}

	public void setJobRegistry(JobRegistry jobRegistry) {
		this.jobRegistry = jobRegistry;
	}
	
}