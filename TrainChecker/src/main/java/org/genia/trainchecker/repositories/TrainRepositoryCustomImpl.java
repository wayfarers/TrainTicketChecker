package org.genia.trainchecker.repositories;

import javax.inject.Inject;

import org.genia.trainchecker.core.UzTrain;
import org.genia.trainchecker.entities.Train;
import org.springframework.stereotype.Repository;

@Repository
public class TrainRepositoryCustomImpl implements TrainRepositoryCustom {

    @Inject
    private TrainRepository trainRepo;

    @Inject
    private StationRepositoryCustom stationRepo;

    @Override
    public Train getTrain(UzTrain coreTrain) {
        Train train = trainRepo.findByTrainNum(coreTrain.getNum());
        if (train == null) {
            train = new Train();
            train.setTrainNum(coreTrain.getNum());
            train.setFrom(stationRepo.getStation(coreTrain.getFrom()));
            train.setTo(stationRepo.getStation(coreTrain.getTill()));
            train.setCategory(coreTrain.getCategory());
            train.setModel(coreTrain.getModel());
            trainRepo.saveAndFlush(train);
        }

        return train;
    }

}
