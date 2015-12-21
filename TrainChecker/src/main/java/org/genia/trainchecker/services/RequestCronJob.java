package org.genia.trainchecker.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.genia.trainchecker.converters.RequestConverter;
import org.genia.trainchecker.entities.TicketsRequest;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.TicketsResponseRepository;
import org.genia.trainchecker.repositories.UserRepository;
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
