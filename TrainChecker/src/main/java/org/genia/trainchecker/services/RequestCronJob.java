package org.genia.trainchecker.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RequestCronJob implements Job {
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		RequestService reqService = (RequestService) context.getJobDetail().getJobDataMap().get("reqService");
		reqService.sendActiveRequests();
	}

}
