package org.genia.trainchecker.controllers;

import java.util.List;

import javax.inject.Inject;

import org.genia.trainchecker.config.JsonOptions;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.TicketsResponseRepository;
import org.genia.trainchecker.repositories.UserRequestRepository;
import org.genia.trainchecker.services.RequestService;
import org.genia.trainchecker.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userRequests")
public class UserRequestController {
	
	@Inject
	private UserRequestRepository requestRepository;
	@Inject
	private UserService userService;
	@Inject
	private TicketsResponseRepository ticketsResponseRepository;
	@Inject 
	private RequestService requestService;
	
	@RequestMapping("/getUserRequests")
	public @ResponseBody List<UserRequest> getUserRequests() {
//		List<UserRequest> requests = requestRepository.findByUserId(userService.getCurrentLoggedInUser().getId());
		JsonOptions.ignore("needResponses");
		
		return requestService.getUserRequests();
	}
	
	@RequestMapping("/changeRequestStatus")
	public @ResponseBody void changeRequestStatus(String requestIdStr) {
		Integer requestId = Integer.parseInt(requestIdStr);
		UserRequest request = requestRepository.findOne(requestId);
		request.setActive(!request.getActive());
		requestRepository.save(request);
	}
	
	@RequestMapping("/lastResponse")
	public @ResponseBody TicketsResponse getLastResponse(Integer ticketRequestId) {
		return requestService.getLastResponse(ticketRequestId);
	}
	
}
