package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.Train;

public interface TrainRepositoryCustom {
	public Train getTrain(org.genia.trainchecker.core.Train coreTrain);
}
