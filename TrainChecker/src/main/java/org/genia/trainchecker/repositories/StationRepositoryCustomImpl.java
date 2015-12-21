package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StationRepositoryCustomImpl implements StationRepositoryCustom {

	@Autowired
	public StationRepository repository;
	
	@Override
	public Station getStation(org.genia.trainchecker.core.Station coreStation) {
		Station station = repository.getByStationIdUz(coreStation.getStationId());
		if (station == null) {
			station = new Station();
			station.setStationIdUz(coreStation.getStationId());
			station.setStationName(coreStation.getName());
			repository.save(station);
		}
		return station;
	}
}
