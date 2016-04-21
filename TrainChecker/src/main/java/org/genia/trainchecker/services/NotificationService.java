package org.genia.trainchecker.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.genia.trainchecker.core.PlaceType;
import org.genia.trainchecker.entities.Place;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.TicketsResponseItem;
import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	final static Logger logger = LoggerFactory.getLogger(NotificationService.class);
	
	@Inject
	private UserRepository userRepository;
	
	Properties creds; 
	
	public NotificationService() {
		loadMailCredentials();
	}
	
	private void loadMailCredentials() {
		creds = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try(InputStream resourceStream = loader.getResourceAsStream("mailCredentials.properties")) {
			creds.load(resourceStream);
		} catch (FileNotFoundException e) {
			logger.error("Mail credentials not found.");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error while loading credentials.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 1. Get all active user requests
	 * 2. Get two last tickets responses for each user request
	 * 3. Compare according to (future) filter
	 * 4. If there are some positive changes - send an email to the user. 
	 * 
	 */
	@Transactional
	public void sendNotifications() {
		List<UserRequest> activeRequests = userRepository.findActive();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		for (UserRequest userRequest : activeRequests) {
			if (!userRequest.isExpired() && userRequest.getRequest().getResponses().size() != 0) {
				Collections.sort(userRequest.getRequest().getResponses(), new Comparator<TicketsResponse>() {
					@Override
					public int compare(TicketsResponse o1, TicketsResponse o2) {
						return o1.getTime().compareTo(o2.getTime()) * (-1);
					}
				});
				
				TicketsResponse last = null;
				TicketsResponse previous = null;
				
				try {
					last = userRequest.getRequest().getResponses().get(0);
					previous = userRequest.getRequest().getResponses().get(1);
				} catch (IndexOutOfBoundsException e) {
					//doing nothing, here can be only one response in list.
				}
				
				//TODO: implement compearing according to a filter.
				
				if (getPlacesCount(last, userRequest) > getPlacesCount(previous, userRequest)) {
					//TODO: send appropriate notification
					String emailSubject = "TrainAlert: New tickets available!";
					String emailBody = "Here are new tickets for direction " + userRequest.getRequest().getFrom().getStationName() + " - " + userRequest.getRequest().getTo().getStationName() 
							+ ", on " + dateFormat.format(userRequest.getRequest().getTripDate()) + "\n\n";
					
					for (TicketsResponseItem item : last.getItems()) {
						if (StringUtils.isEmpty(userRequest.getTrainNum())
								|| StringUtils.containsIgnoreCase(item.getTrain().getTrainNum(), userRequest.getTrainNum())) {
							emailBody += "Train #" + item.getTrain().getTrainNum() + "\n";
							for (Place place : item.getAvailablePlaces()) {
								if (StringUtils.isEmpty(userRequest.getPlaceTypes()) || 
										userRequest.getPlaceTypesAsList().contains(PlaceType.ANY) || 
										userRequest.getPlaceTypesAsList().contains(place.getPlaceType())) {
									emailBody += "Place: " + place.getTitle() + ", available: " + place.getPlacesAvailable() + "\n";
								}
							}
						}
						emailBody += "\n";
					}
					MailUtils.sendEmail(creds, userRequest.getUser().getEmail(), emailSubject, emailBody);
				}
			}
		}
		
	}
	
	private static int getPlacesCount(TicketsResponse response, UserRequest userRequest) {
		int totalPlaces = 0;
		if (response == null || response.getErrorDescription() != null) {
			return 0;
		}
		
		String requestedTrainNum = userRequest.getTrainNum();
		
		for (TicketsResponseItem item : response.getItems()) {
			if (StringUtils.isEmpty(requestedTrainNum) || requestedTrainNum.equals("ANY")
					|| StringUtils.containsIgnoreCase(item.getTrain().getTrainNum(), requestedTrainNum)) {
				for (Place place : item.getAvailablePlaces()) {
					if (userRequest.getPlaceTypesAsList().contains(PlaceType.ANY) 
							|| userRequest.getPlaceTypesAsList().contains(place.getPlaceType())) {
						totalPlaces += place.getPlacesAvailable();
					}
				}
			}
		}
		return totalPlaces;
	}
	
	/**
	 * Method sends a reset-password link to the specified user. If a resetPassToken is not specified for the user, email won't be sent.
	 * @param user
	 */
	public void sendResetPassLink(User user) {
		String link = "http://http://trainalert.midnighters.net/#/newPass?tk=" + user.getPassResetToken();
		String emailSubject = "TrainAlert Reset Your Password";
		String emailBody = "Hello, " + user.getName() + "!\n\n";
		emailBody += "This email was sent automatically by system in response to your request to reset your password.\n";
		emailBody += "To reset your password and access your account, use the following link:\n";
		emailBody += link + "\n";
		emailBody += "\nThak you,\nTrainAlert team";
		
		MailUtils.sendEmail(creds, user.getEmail(), emailSubject, emailBody);
	}
}
