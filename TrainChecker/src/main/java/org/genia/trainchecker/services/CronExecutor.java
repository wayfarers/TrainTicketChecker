package org.genia.trainchecker.services;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Named
@PropertySource(value = "classpath:config.properties")
@Scope("singleton")
public class CronExecutor {
	JobDetail job;
	Trigger trigger;
	Scheduler scheduler;
	Integer interval;
	
	@Inject
	Environment env;
	
	@Inject
	RequestService reqService;
	
	@PostConstruct
	public void postConstruct() throws SchedulerException {
		if ((interval = env.getProperty("request_interval", Integer.class)) == null) {
			interval = 900;
		}
		// The job itself is not managed by Spring and cannot autowire fields.
		// Therefore, we pass the service to the job via JobDataMap.
		JobDataMap map = new JobDataMap();
		map.put("reqService", reqService);
		job = JobBuilder.newJob(RequestCronJob.class).withIdentity("TicketsRequestJob")
				.usingJobData(map)
				.build();
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("requestTrigger")
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(interval).repeatForever())
				.build();
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.clear();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
		
	}
	
	public void startJob() throws SchedulerException {
		
		if (scheduler.isInStandbyMode())
			scheduler.resumeJob(job.getKey());
		else
			scheduler.start();
	}
	
	public void stopJob() throws SchedulerException {
		scheduler.pauseJob(job.getKey());
	}
}
