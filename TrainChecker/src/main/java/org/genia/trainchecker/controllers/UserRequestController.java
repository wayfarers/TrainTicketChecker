package org.genia.trainchecker.controllers;

import java.util.List;

import javax.inject.Inject;

import org.genia.trainchecker.config.JsonOptions;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.UserRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userRequests")
public class UserRequestController {
	
	@Inject
	private UserRequestRepository requestRepository;
	
	@RequestMapping("/getUserRequests")
	public @ResponseBody List<UserRequest> getUserRequests() {
		List<UserRequest> requests = requestRepository.findByUserId(1);
		
		JsonOptions.ignore("needResponses");
		
		return requests;
	}
	
	@RequestMapping("/changeRequestStatus")
	public @ResponseBody void changeRequestStatus(String requestIdStr) {
		Integer requestId = Integer.parseInt(requestIdStr);
		UserRequest request = requestRepository.findOne(requestId);
		request.setActive(!request.getActive());
		requestRepository.save(request);
	}

}
