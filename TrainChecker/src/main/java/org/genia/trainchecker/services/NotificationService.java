package org.genia.trainchecker.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
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
	
	/**
	 * 1. Get all active user requests
	 * 2. Get two last tickets responses for each user request
	 * 3. Compare according to (future) filter
	 * 4. If there are some positive changes - send an email to the user. 
	 * 
	 */
	public void sendNotifications() {
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
			
			//TODO: implement compearing according to a filter.
			
			
			if (getPlacesCount(last, userRequest) > getPlacesCount(previous, userRequest)) {
				//TODO: send appropriate notification
			}
		}
		
	}
	
	private static int getPlacesCount(TicketsResponse response, UserRequest userRequest) {
		int totalPlaces = 0;
		if (response.getErrorDescription() != null) {
			return 0;
		}
		
		for (TicketsResponseItem item : response.getItems()) {
			if (StringUtils.isEmpty(userRequest.getTrainNum())
					|| StringUtils.containsIgnoreCase(item.getTrain().getTrainNum(), userRequest.getTrainNum())) {
				for (Place place : item.getAvailablePlaces()) {
					if (userRequest.getPlaceTypesAsList().contains(place.getPlaceType())) {
						totalPlaces += place.getPlacesAvailable();
					}
				}
			}
		}
		return totalPlaces;
	}
	
	public void sendResetPassLink(String token) {
		String link = "http://localhost:8080/trainchecker/#/newPass?tk=" + token;
		//TODO: implement email of reset link
	}
}
