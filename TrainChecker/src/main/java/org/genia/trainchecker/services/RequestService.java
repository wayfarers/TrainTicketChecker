package org.genia.trainchecker.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.genia.trainchecker.converters.RequestConverter;
import org.genia.trainchecker.core.TicketsRequest;
import org.genia.trainchecker.core.TicketsResponse;
import org.genia.trainchecker.core.Train;
import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.entities.UserRequest;
import org.genia.trainchecker.repositories.TicketsResponseRepository;
import org.genia.trainchecker.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

	@Inject
	private TrainTicketChecker checker;
	@Inject
	private UserRepository userRepository;
	@Inject
	RequestConverter converter;
	@Inject
	private TicketsResponseRepository responseRepository;

	public TicketsResponse sendRequest(String fromStation, String toStation, String dt) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dt.substring(0, 10));
		TicketsRequest request = new TicketsRequest();
		request.setFrom(checker.getStationsAsMap().get(fromStation));
		request.setTill(checker.getStationsAsMap().get(toStation));
		request.setDate(date);
		TicketsResponse response = checker.checkTickets(request);
		for (Train train : response.getTrains()) {
			System.out.printf("%s\t%s - %s, %d free places total%n", train.getNum(), train.getFrom().getName(),
					train.getTill().getName(), train.getTotalPlaces());
		}
		return response;
	}

	public TicketsResponse sendRequest(TicketsRequest request) {
		return checker.checkTickets(request);
	}

	public void sendActiveRequests() {
		List<UserRequest> userRequests = userRepository.findActive();
		HashSet<org.genia.trainchecker.entities.TicketsRequest> ticketsRequests = new HashSet<>();
		for (UserRequest userRequest : userRequests) {
			ticketsRequests.add(userRequest.getRequest());
		}

		for (org.genia.trainchecker.entities.TicketsRequest ticketsRequest : ticketsRequests) {
			org.genia.trainchecker.core.TicketsResponse currentResponse = sendRequest(converter.toCore(ticketsRequest));
			org.genia.trainchecker.entities.TicketsResponse response = converter.convertToEntity(currentResponse);
			response.setTicketsRequest(ticketsRequest);
			System.out.println("Response details:\n");
			System.out.printf("Requested direction: %s - %s%n", ticketsRequest.getFrom().getStationName(),
					ticketsRequest.getTo().getStationName());
			if (currentResponse.isError()) {
				System.out.println(currentResponse.getErrorDescription());
			} else {
				for (Train train : currentResponse.getTrains()) {
					System.out.printf("%s\t%s - %s, %d free places total%n", train.getNum(), train.getFrom().getName(),
							train.getTill().getName(), train.getTotalPlaces());
				}
			}
			responseRepository.save(response);
		}
	}
}
