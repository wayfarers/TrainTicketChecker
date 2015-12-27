package org.genia.trainchecker.repositories;

import javax.inject.Inject;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.entities.Station;
import org.springframework.stereotype.Repository;

@Repository
public class StationRepositoryCustomImpl implements StationRepositoryCustom {

	@Inject
	public StationRepository repository;
	@Inject
	private TrainTicketChecker checker;
	
	@Override
	public Station getStation(org.genia.trainchecker.core.Station coreStation) {
//		Station station = repository.getByStationIdUz(coreStation.getStationId());	//TODO: different stations can have same UzId, f.e. in different lang.
		Station station = repository.getByStationName(coreStation.getName());
		if (station == null) {
			station = new Station();
			station.setStationIdUz(checker.getStationsAsMap().get(coreStation.getName()).getStationId());
			station.setStationName(coreStation.getName());
			repository.save(station);
		}
		return station;
	}
}
