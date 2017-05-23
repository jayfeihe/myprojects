package com.tera.batch.scheduler;

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
public class RunScheduler {
	
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RunScheduler.class);
	
	@Autowired(required=false)
	private JobRegistry jobRegistry;

	@Autowired(required=false)
	private JobLauncher jobLauncher;
	
	public void autoMatchJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("autoMatchJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void autoMatchSplitJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("autoMatchSplitJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void handleJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("handleJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void daifuJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("daifuJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void daishouJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("daishouJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void paymentQueryJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("paymentQueryJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}

	public void repayPlanJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("repayPlanJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void cleanMatchJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("cleanMatchJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	/*public void jMProjectQueryJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("jMProjectQueryJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}*/
	
	public void initSeqJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("initSeqJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	
	public void assignJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("assignJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void saleReTimeFreezeJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("saleReTimeFreezeJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	/*public void jMSendDefaultInfoJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("JMSendDefaultInfoJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}*/
	
	public void collectionJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("collectionJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}
	
	public void msgJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("msgJob"), param);
		log.info("Job Params:" +dateParam);
		log.info("Exit Status:" + exec.getStatus());
	}

	public void sendMsgJobRun() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateParam = format.format(new Date());
		JobParameters param = new JobParametersBuilder().addString("timestamp", dateParam).toJobParameters();
		
		JobExecution exec = jobLauncher.run(jobRegistry.getJob("sendMsgJob"), param);
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