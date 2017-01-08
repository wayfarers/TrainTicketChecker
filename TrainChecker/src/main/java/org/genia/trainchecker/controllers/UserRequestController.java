package org.genia.trainchecker.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.genia.trainchecker.config.JsonOptions;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.UserRequestRepository;
import org.genia.trainchecker.services.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userRequests")
public class UserRequestController {

    @Inject
    private UserRequestRepository requestRepository;
    @Inject
    private RequestService requestService;

    @RequestMapping("/getUserRequests")
    public @ResponseBody List<UserRequest> getUserRequests() {
        JsonOptions.ignore("needResponses");
        List<UserRequest> requests =  requestService.getUserRequests();
        Collections.sort(requests, new Comparator<UserRequest>() {
            @Override
            public int compare(UserRequest o1, UserRequest o2) {
                return (-1) * o1.getRequest().getTripDate().compareTo(o2.getRequest().getTripDate());
            }
        });

        return requests;
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
