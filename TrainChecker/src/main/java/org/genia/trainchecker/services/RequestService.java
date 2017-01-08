package org.genia.trainchecker.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.genia.trainchecker.converters.RequestConverter;
import org.genia.trainchecker.core.UzTicketsRequest;
import org.genia.trainchecker.core.UzTicketsResponse;
import org.genia.trainchecker.core.UzTrain;
import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.StationRepositoryCustom;
import org.genia.trainchecker.repositories.TicketsRequestRepository;
import org.genia.trainchecker.repositories.TicketsResponseRepository;
import org.genia.trainchecker.repositories.UserRepository;
import org.genia.trainchecker.repositories.UserRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
	
	final static Logger logger = LoggerFactory.getLogger(RequestService.class);
	
	@Inject
	private NotificationService notificationService;
	@Inject
	private TrainTicketChecker checker;
	@Inject
	private UserRepository userRepository;
	@Inject
	private UserRequestRepository userRequestRepository;
	@Inject
	RequestConverter converter;
	@Inject
	private TicketsResponseRepository responseRepository;
	@Inject
	public StationRepositoryCustom stationRepo;
	@Inject
	public TicketsRequestRepository requestRepository;
	@Inject
	private UserService userService;
	@Inject
	private TicketsResponseRepository ticketsResponseRepository;

	public UzTicketsResponse sendRequest(String fromStation, String toStation, String dt) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dt.substring(0, 10));
		UzTicketsRequest request = new UzTicketsRequest();
		request.setFrom(checker.getStationsAsMap().get(fromStation));
		request.setTill(checker.getStationsAsMap().get(toStation));
		request.setDate(date);
		UzTicketsResponse response = checker.checkTickets(request);
		for (UzTrain train : response.getTrains()) {
			System.out.printf("%s\t%s - %s, %d free places total%n", train.getNum(), train.getFrom().getName(),
					train.getTill().getName(), train.getTotalPlaces());
		}
		return response;
	}

	public UzTicketsResponse sendRequest(UzTicketsRequest request) {
		return checker.checkTickets(request);
	}

	public void sendActiveRequests() {
		logger.info("Sending active requests...");
		List<UserRequest> userRequests = userRepository.findActive();
		HashSet<org.genia.trainchecker.entities.TicketsRequest> ticketsRequests = new HashSet<>();
		for (UserRequest userRequest : userRequests) {
			ticketsRequests.add(userRequest.getRequest());
		}
		Date dateNow = Calendar.getInstance(TimeZone.getTimeZone("Europe/Kiev")).getTime();
		
		int count = 0;
		for (org.genia.trainchecker.entities.TicketsRequest ticketsRequest : ticketsRequests) {
			if (!ticketsRequest.getTripDate().before(makeMidnight(dateNow))) {
				count++;
				long time = System.currentTimeMillis();
				UzTicketsResponse currentResponse = sendRequest(converter.toCore(ticketsRequest));
				long latency = System.currentTimeMillis() - time;
				org.genia.trainchecker.entities.TicketsResponse response = converter.convertToEntity(currentResponse);
				response.setTicketsRequest(ticketsRequest);
				logger.info("Response details:");
				logger.info("Requested direction: {} - {}", ticketsRequest.getFrom().getStationName(),
						ticketsRequest.getTo().getStationName());
				if (currentResponse.isError()) {
					logger.info(currentResponse.getErrorDescription());
				} else {
					for (UzTrain train : currentResponse.getTrains()) {
						logger.info("{}\t{} - {}, {} free places total", train.getNum(), train.getFrom().getName(),
								train.getTill().getName(), train.getTotalPlaces());
					}
				}
				logger.info("\n");
				response.setRequestLatency(latency);
				responseRepository.save(response);
			}
		}
		String countStr = count < 2 ? "was send." : "were send.";
		logger.info("Sending active requests complete. " + count + " request(s) total " + countStr);
		notificationService.sendNotifications();
	}
	
	public static Date makeMidnight(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public String createAlert(String fromStation, String toStation, String trainNum, Date tripDate, String placeTypes) {
		org.genia.trainchecker.entities.TicketsRequest request = new org.genia.trainchecker.entities.TicketsRequest();
		request.setFrom(stationRepo.getStation(fromStation));
		request.setTo(stationRepo.getStation(toStation));
		request.setTripDate(tripDate);
		if (request.getFrom() == null || request.getTo() == null) {
			return "Помилка: невірно вибрані станції. Будь ласка, перевірте.";
		}
		requestRepository.save(request);
		UserRequest userRequest = new UserRequest();
		userRequest.setRequest(request);
		userRequest.setTrainNum(trainNum);
		userRequest.setPlaceTypes(placeTypes);
		userRequest.setUser(userService.getCurrentLoggedInUser());
		userRequest.setActive(true);
		userRequestRepository.save(userRequest);
		return "Запит створено!";
	}
	
	public org.genia.trainchecker.entities.TicketsResponse getLastResponse(Integer ticketRequestId) {
		List<org.genia.trainchecker.entities.TicketsResponse> response = ticketsResponseRepository.findFirst1ByTicketsRequestIdOrderByTimeDesc(ticketRequestId, new PageRequest(0, 1));
		if (response.isEmpty()) {
			return null;
		}
		return response.get(0);
	}
	
	@Transactional
	public List<UserRequest> getUserRequests() {
		List<UserRequest> requests = userRequestRepository.findByUserId(userService.getCurrentLoggedInUser().getId());
		for (UserRequest userRequest : requests) {
			userRequest.getRequest().setLastResponse(getLastResponse(userRequest.getRequest().getId()));
		}
		return requests;
	}
}
