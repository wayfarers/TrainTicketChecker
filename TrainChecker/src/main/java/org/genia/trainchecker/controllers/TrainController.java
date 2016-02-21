package org.genia.trainchecker.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import org.genia.trainchecker.core.TicketsRequest;
import org.genia.trainchecker.core.TicketsResponse;
import org.genia.trainchecker.core.Train;
import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.services.CronExecutor;
import org.genia.trainchecker.services.RequestService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/stations")
public class TrainController {
	
	final static Logger logger = LoggerFactory.getLogger(TrainController.class);
	
	@Inject
	private TrainTicketChecker checker;
	@Inject
	private CronExecutor cronExecotor;
	@Inject
	public RequestService requestService;
	
	@RequestMapping("/stationlist.json")
	public @ResponseBody List<String> getAllStations() {
		if (checker.getAllStations() == null || checker.getAllStations().isEmpty()) {
			checker.init();
			System.out.println("Initiated");
		}
		 Set<String> set = checker.getStationsAsMap().keySet();
		 return new ArrayList<>(set);
	}
	
	@RequestMapping("/getStations")
	public @ResponseBody List<String> getStations(String rq) {
		List<String> majorStations = new ArrayList<>();
		majorStations.add("Київ");
		majorStations.add("Одеса");
		majorStations.add("Львів");
		majorStations.add("Харків");
		Locale russian = new Locale("RU");
		List<String> list = new ArrayList<>();
		
		for (String majorStation : majorStations) {
			if (majorStation.toLowerCase(russian).startsWith(rq.toLowerCase(russian))) {
				list.add(majorStation);
			}
		}
		
		for (String string : checker.getStationsAsMap().keySet()) {
			if (string.toLowerCase(russian).startsWith(rq.toLowerCase(russian)) && !list.contains(string)) {
				list.add(string);
				if(list.size() == 10)
					return list;
			}
		}
		return list;
	}
	
	@RequestMapping("/sendRequest")
	public @ResponseBody TicketsResponse sendRequest(String fromStation, String toStation, String dt) throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dt);
		TicketsRequest request = new TicketsRequest();
		request.setFrom(checker.getStationsAsMap().get(fromStation));
		request.setTill(checker.getStationsAsMap().get(toStation));
		request.setDate(date);
		TicketsResponse response = checker.checkTickets(request);
		for (Train train : response.getTrains()) {
			System.out.printf("%s\t%s - %s, %d free places total%n", train.getNum(), 
					train.getFrom().getName(), train.getTill().getName(), train.getTotalPlaces());
		}
		return response;
	}
	
	@RequestMapping("/createAlert")
	public @ResponseBody String createAlert(String fromStation, String toStation, String tripDate, String placeTypes) throws ParseException, JsonProcessingException {
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(tripDate);
		String msg = requestService.createAlert(fromStation, toStation, null, date);
		return new ObjectMapper().writer().writeValueAsString(msg);
	}
	
	@RequestMapping("/startJob")
	public @ResponseBody void startJob() throws SchedulerException {
		System.out.println("start");
		cronExecotor.startJob();
	}
	
	@RequestMapping("/stopJob")
	public @ResponseBody void stopJob() throws SchedulerException {
		System.out.println("stopped");
		cronExecotor.stopJob();
	}
}
