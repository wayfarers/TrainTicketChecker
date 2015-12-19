package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {

}
