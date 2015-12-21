package org.genia.trainchecker.converters;

import java.util.Calendar;

import javax.inject.Named;

import org.genia.trainchecker.core.Place;
import org.genia.trainchecker.core.Station;
import org.genia.trainchecker.core.Train;
import org.genia.trainchecker.entities.TicketsRequest;
import org.genia.trainchecker.entities.TicketsResponse;
import org.genia.trainchecker.entities.TicketsResponseItem;
import org.genia.trainchecker.repositories.StationRepositoryCustom;
import org.genia.trainchecker.repositories.TrainRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class RequestConverter {
	
	@Autowired
	public StationRepositoryCustom stationRepo;
	
	@Autowired
	public TrainRepositoryCustom trainRepo;
	
	public TicketsRequest convertToEntity(org.genia.trainchecker.core.TicketsRequest coreRequest) {
		TicketsRequest request = new TicketsRequest();
		request.setFrom(coreRequest.getFrom());
		request.setTo(coreRequest.getTill());
		request.setTripDate(coreRequest.getDate());
		request.setPlaceType(coreRequest.getPlaceType());
		return request;
	}
	
	public org.genia.trainchecker.core.TicketsRequest toCore(TicketsRequest request) {
		org.genia.trainchecker.core.TicketsRequest coreRequest = new org.genia.trainchecker.core.TicketsRequest();
		coreRequest.setFrom(convertToCore(request.getFrom()));
		coreRequest.setTill(convertToCore(request.getTo()));
		coreRequest.setDate(request.getTripDate());
		coreRequest.setPlaceType(request.getPlaceType());	//TODO: move PlaceType from TicketsRequest to UserRequest
		return coreRequest;
	}
	
	public Station convertToCore(org.genia.trainchecker.entities.Station station) {
		Station coreStation = new Station();
		coreStation.setName(station.getStationName());
		coreStation.setStationId(station.getStationIdUz());
		return coreStation;
	}
	
	public org.genia.trainchecker.entities.Station toEntity(Station coreStation) {
		return stationRepo.getStation(coreStation);
	}
	
	public TicketsResponse convertToEntity(org.genia.trainchecker.core.TicketsResponse coreResponse) {
		TicketsResponse response = new TicketsResponse();
		response.setData(coreResponse.getData());
		response.setErrorDescription(coreResponse.getErrorDescription());
		response.setRequestLatency(0);										//TODO: implement latency
		response.setTime(Calendar.getInstance().getTime());
//		response.setTicketsRequest(ticketsRequest);
		
		for (Train train : coreResponse.getTrains()) {
			TicketsResponseItem item = new TicketsResponseItem();
			item.setTicketsResponse(response);
			item.setTrain(trainRepo.getTrain(train));
			item.setReservationError(train.getReserveError());
			for (Place corePlace : train.getPlaces()) {
				org.genia.trainchecker.entities.Place place = new org.genia.trainchecker.entities.Place();
				place.setPlaceType(corePlace.getPlaceType());
				place.setTicketsResponseItem(item);
				place.setPlacesAvailable(corePlace.getPlaces());
				place.setTitle(corePlace.getTitle());
				item.getAvailablePlaces().add(place);
			}
			response.getItems().add(item);
		}
		
		return response;
	}
}
