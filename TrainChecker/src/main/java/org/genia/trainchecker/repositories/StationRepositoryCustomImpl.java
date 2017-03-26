package org.genia.trainchecker.repositories;

import javax.inject.Inject;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.genia.trainchecker.core.UzStation;
import org.genia.trainchecker.entities.Station;
import org.springframework.stereotype.Repository;

@Repository
public class StationRepositoryCustomImpl implements StationRepositoryCustom {

    @Inject
    public StationRepository repository;
    @Inject
    private TrainTicketChecker checker;

    @Override
    public Station getStation(UzStation coreStation) {
        // TODO: 17.01.2017 this method not only gets station but also saves it. Rename or refactor.

        // TODO: 17.01.2017 Big copy/paste fragment
        // Find by name because different stations can have same UzId, f.e. in different lang.
        Station station = repository.getByStationName(coreStation.getName());
        if (station == null) {
            station = new Station();
            UzStation coreStationCorrect = checker.getStationsAsMap().get(coreStation.getName());
            if (coreStationCorrect != null) {
                station.setStationIdUz("" + coreStationCorrect.getStationId());
            } else {
                // station name from request can be absent in the station list. So it don't have UzId.
                station.setStationIdUz("invalid");
            }

            station.setStationName(coreStation.getName());
            repository.save(station);
        }
        return station;
    }

    @Override
    public Station getStation(String stationName) {
        // TODO: 17.01.2017 this method not only gets station but also saves it. Rename or refactor.

        Station station = repository.getByStationName(stationName);
        if (station == null) {
            station = new Station();
            station.setStationName(stationName);
            UzStation coreStationCorrect = checker.getStationsAsMap().get(stationName);
            if (coreStationCorrect != null) {
                station.setStationIdUz("" + coreStationCorrect.getStationId());
            }
            repository.save(station);
        }
        return station;
    }
}
