package org.genia.trainchecker.repositories;

import java.util.List;

import org.genia.trainchecker.entities.TicketsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketsResponseRepository extends JpaRepository<TicketsResponse, Integer> {
	@Query("select r from TicketsResponse r where r.ticketsRequest.id = ?1 order by time desc")
	public List<TicketsResponse> findFirst1ByTicketsRequestIdOrderByTimeDesc(Integer ticketRequestId,  Pageable pageable);
}
