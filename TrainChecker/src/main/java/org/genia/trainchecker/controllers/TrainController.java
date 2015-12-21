package org.genia.trainchecker.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.genia.trainchecker.core.TicketsRequest;
import org.genia.trainchecker.core.TicketsResponse;
import org.genia.trainchecker.core.Train;
import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.services.CronExecutor;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stations")
public class TrainController {
	
	@Autowired
	private TrainTicketChecker checker;
	
	@Inject
	private CronExecutor cronExecotor;
	
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
		List<String> list = new ArrayList<>();
		for (String string : checker.getStationsAsMap().keySet()) {
			if (string.toLowerCase(Locale.ROOT).startsWith(rq.toLowerCase(Locale.ROOT))) {
				list.add(string);
				if(list.size() == 10)
					return list;
			}
		}
		return list;
	}
	
	@RequestMapping("/home")
	public ModelAndView getHomePage() {
		Map<String, String> model = new HashMap<>();
		model.put("username", "Genia");
		return new ModelAndView("index", model);
	}
	
	@RequestMapping("/layout")
    public String getTrainPartialPage(ModelMap modelMap) {
        return "stations/layout";
    }
	
	@RequestMapping("/sendRequest")
	public @ResponseBody TicketsResponse sendRequest(String fromStation, String toStation, String dt) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dt.substring(0, 10));
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
