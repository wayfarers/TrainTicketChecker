package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Integer> {

}
