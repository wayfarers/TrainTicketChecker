package org.genia.trainchecker.converters;

import java.util.Calendar;

import javax.inject.Named;

import org.genia.trainchecker.core.*;
import org.genia.trainchecker.entities.*;
import org.genia.trainchecker.repositories.StationRepositoryCustom;
import org.genia.trainchecker.repositories.TrainRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class RequestConverter {
	
	@Autowired
	public StationRepositoryCustom stationRepo;
	
	@Autowired
	public TrainRepositoryCustom trainRepo;
	
	public TicketsRequest convertToEntity(UzTicketsRequest coreRequest) {
		TicketsRequest request = new TicketsRequest();
		request.setFrom(coreRequest.getFrom());
		request.setTo(coreRequest.getTill());
		request.setTripDate(coreRequest.getDate());
		request.setPlaceType(coreRequest.getPlaceType());
		return request;
	}
	
	public UzTicketsRequest toCore(TicketsRequest request) {
		UzTicketsRequest coreRequest = new UzTicketsRequest();
		coreRequest.setFrom(convertToCore(request.getFrom()));
		coreRequest.setTill(convertToCore(request.getTo()));
		coreRequest.setDate(request.getTripDate());
		coreRequest.setPlaceType(request.getPlaceType());	//TODO: move PlaceType from TicketsRequest to UserRequest
		return coreRequest;
	}
	
	public UzStation convertToCore(Station station) {
		UzStation coreStation = new UzStation();
		coreStation.setName(station.getStationName());
		coreStation.setStationId(station.getStationIdUz());
		return coreStation;
	}
	
	public Station toEntity(UzStation coreStation) {
		return stationRepo.getStation(coreStation);
	}
	
	public TicketsResponse convertToEntity(UzTicketsResponse coreResponse) {
		TicketsResponse response = new TicketsResponse();
		response.setData(coreResponse.getData());
		response.setErrorDescription(coreResponse.getErrorDescription());
		response.setTime(Calendar.getInstance().getTime());
//		response.setTicketsRequest(ticketsRequest);
		
		for (UzTrain train : coreResponse.getTrains()) {
			TicketsResponseItem item = new TicketsResponseItem();
			item.setTicketsResponse(response);
			item.setTrain(trainRepo.getTrain(train));
			item.setReservationError(train.getReserveError());
			for (UzPlace corePlace : train.getPlaces()) {
				Place place = new Place();
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
