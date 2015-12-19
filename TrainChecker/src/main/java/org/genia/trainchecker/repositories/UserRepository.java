package org.genia.trainchecker.repositories;

import org.genia.trainchecker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
