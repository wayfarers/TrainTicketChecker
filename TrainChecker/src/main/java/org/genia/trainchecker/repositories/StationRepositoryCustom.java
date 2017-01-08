package org.genia.trainchecker.repositories;

import org.genia.trainchecker.core.UzStation;
import org.genia.trainchecker.entities.Station;

public interface StationRepositoryCustom {
	public Station getStation(UzStation coreStation);
	public Station getStation(String stationName);
}
