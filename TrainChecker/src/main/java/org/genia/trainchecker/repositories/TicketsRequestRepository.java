package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.TicketsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRequestRepository extends JpaRepository<TicketsRequest, Integer> {

}
