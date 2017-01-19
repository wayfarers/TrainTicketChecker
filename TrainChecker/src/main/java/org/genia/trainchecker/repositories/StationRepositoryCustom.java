package org.genia.trainchecker.repositories;

import org.genia.trainchecker.core.UzStation;
import org.genia.trainchecker.entities.Station;

public interface StationRepositoryCustom {
    Station getStation(UzStation coreStation);
    Station getStation(String stationName);
}
