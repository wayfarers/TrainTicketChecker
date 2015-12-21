package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StationRepository extends JpaRepository<Station, Integer>{
	public Station getByStationIdUz(String stationIdUz);
}
