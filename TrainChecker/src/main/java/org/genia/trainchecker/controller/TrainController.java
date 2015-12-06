package org.genia.trainchecker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

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
}
