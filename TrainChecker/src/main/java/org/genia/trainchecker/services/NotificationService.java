package org.genia.trainchecker.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.genia.trainchecker.entities.Place;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.TicketsResponseItem;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Inject
	private UserRepository userRepository;
	
	public void sendNotifications() {
		/*
		 * 1. Get all active user requests
		 * 2. Get two last tickets responses for each user request
		 * 3. Compare according to (future) filter
		 * 4. If there are some positive changes - send an email to the user. 
		 * 
		 */
		
		List<UserRequest> activeRequests = userRepository.findActive();
		
		for (UserRequest userRequest : activeRequests) {
			Collections.sort(userRequest.getRequest().getResponses(), new Comparator<TicketsResponse>() {

				@Override
				public int compare(TicketsResponse o1, TicketsResponse o2) {
					return o1.getTime().compareTo(o2.getTime()) * (-1);
				}
				
			});
			
			TicketsResponse previous = userRequest.getRequest().getResponses().get(1);
			TicketsResponse last = userRequest.getRequest().getResponses().get(0);
			
			if (previous.getErrorDescription() != null) {
				if (last.getErrorDescription() != null) {
					//implement compearing according to a filter.
					if (userRequest.getTrainNum() != null) {
						int totalSitsPrevious = 0;
						int totalSitsLast = 0;
						
						for (TicketsResponseItem item : previous.getItems()) {
							if (item.getTrain().getTrainNum().equalsIgnoreCase(userRequest.getTrainNum())) {
								for (Place place : item.getAvailablePlaces()) {
									totalSitsPrevious += place.getPlacesAvailable();
								}
								break;
							}
						}
						
						for (TicketsResponseItem item : last.getItems()) {
							if (item.getTrain().getTrainNum().equalsIgnoreCase(userRequest.getTrainNum())) {
								for (Place place : item.getAvailablePlaces()) {
									totalSitsLast += place.getPlacesAvailable();
								}
								break;
							}
						}
						if (totalSitsLast > totalSitsPrevious) {
							//TODO: send appropriate notification
						}
					}
				}
			}
			
		}
		
	}
}
