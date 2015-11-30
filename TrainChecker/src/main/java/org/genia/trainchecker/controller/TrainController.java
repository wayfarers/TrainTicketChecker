package org.genia.trainchecker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stations")
public class TrainController {
	
	@Autowired
	private TrainTicketChecker checker;
	
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
			if (string.startsWith(rq))
				list.add(string);
		}
		return list;
	}
	
	

}
