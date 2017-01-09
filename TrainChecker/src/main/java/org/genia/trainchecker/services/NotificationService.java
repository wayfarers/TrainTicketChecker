package org.genia.trainchecker.services;

import org.apache.commons.lang.StringUtils;
import org.genia.trainchecker.core.PlaceType;
import org.genia.trainchecker.entities.*;
import org.genia.trainchecker.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NotificationService {

    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private static final String PROJECT_SERVICE_URL = "http://trainalert.midnighters.net/";

    @Inject
    private UserRepository userRepository;

    private Properties creds;

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
    void sendNotifications() {
        List<UserRequest> activeRequests = userRepository.findActive();
        HashMap<User, List<UserRequest>> requestGroups = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (UserRequest userRequest : activeRequests) {
            if (!userRequest.isExpired() && userRequest.getRequest().getResponses().size() != 0) {
                if (!requestGroups.containsKey(userRequest.getUser())) {
                    requestGroups.put(userRequest.getUser(), new ArrayList<>(Collections.singletonList(userRequest)));
                } else {
                    requestGroups.get(userRequest.getUser()).add(userRequest);
                }
            }
        }

        for (User user : requestGroups.keySet()) {
            String emailSubject = "TrainAlert: Доступні нові квитки!";
            String emailBody = "";
            for (UserRequest userRequest : requestGroups.get(user)) {
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
                    //doing nothing, as here can be case when only one response in list is present.
                }

                //TODO: implement compearing according to a filter.

                if (getPlacesCount(last, userRequest) > getPlacesCount(previous, userRequest)) {
                    //TODO: send appropriate notification
                    emailBody += "<b>З'явилися нові квитки за напрямком " + userRequest.getRequest().getFrom().getStationName() + " - " + userRequest.getRequest().getTo().getStationName()
                            + ", на " + dateFormat.format(userRequest.getRequest().getTripDate()) + "</b><br /><ul>";

                    for (TicketsResponseItem item : last.getItems()) {
                        if (StringUtils.isEmpty(userRequest.getTrainNum())
                                || StringUtils.containsIgnoreCase(item.getTrain().getTrainNum(), userRequest.getTrainNum())) {
                            emailBody += "<li>Потяг № " + item.getTrain().getTrainNum() + "</li><ul>";
                            for (Place place : item.getAvailablePlaces()) {
                                if (StringUtils.isEmpty(userRequest.getPlaceTypes()) ||
                                        userRequest.getPlaceTypesAsList().contains(PlaceType.ANY) ||
                                        userRequest.getPlaceTypesAsList().contains(place.getPlaceType())) {
                                    emailBody += "<li>Місця: " + place.getTitle() + ", доступно: " + place.getPlacesAvailable() + "</li>";
                                }
                            }
                            emailBody += "</ul>";
                        }
                    }
                    emailBody += "</ul><br />";
                }
            }
            if (StringUtils.isNotBlank(emailBody)) {
                emailBody += "\n\n" + "Зайти на сторінку проекту: " + PROJECT_SERVICE_URL;
                emailBody += "<a href=" + PROJECT_SERVICE_URL + ">Зайти на сторінку сервісу</a>";
                emailBody = "<html><body>" + emailBody + "</body></html>";
                MailUtils.sendEmail(creds, user.getEmail(), emailSubject, emailBody, true);
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
     * @param user user to whom you want to send a link
     */
    void sendResetPassLink(User user) {
        // TODO: 09.01.2017 check, if email really won't be sent when resetPassToken is not specified
        String link = PROJECT_SERVICE_URL + "#/newPass?tk=" + user.getPassResetToken();
        String emailSubject = "TrainAlert: Reset Your Password";
        String emailBody = "Hello, " + user.getName() + "!\n\n";
        emailBody += "This email was sent automatically by system in response to your request to reset your password.\n";
        emailBody += "To reset your password and access your account, use the following link:\n";
        emailBody += link + "\n";
        emailBody += "\nThank you,\nTrainAlert team";

        MailUtils.sendEmail(creds, user.getEmail(), emailSubject, emailBody);
    }
}
