package org.genia.trainchecker.repositories;

import org.genia.trainchecker.core.UzTrain;
import org.genia.trainchecker.entities.Train;

public interface TrainRepositoryCustom {
	public Train getTrain(UzTrain coreTrain);
}
