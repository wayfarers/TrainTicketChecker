package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.Station;

public interface StationRepositoryCustom {
	public Station getStation(org.genia.trainchecker.core.Station coreStation);
}
