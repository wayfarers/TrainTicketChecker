package org.genia.trainchecker.repositories;

import java.util.List;

import org.genia.trainchecker.entities.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
	public List<UserRequest> findByUserId(Integer id);
}
