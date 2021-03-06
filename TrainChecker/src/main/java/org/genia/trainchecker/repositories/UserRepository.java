package org.genia.trainchecker.repositories;

import java.util.List;

import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.entities.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM UserRequest ur where ur.active = true")
    public List<UserRequest> findActive();
    public User findByLogin(String login);
    public User findByPassResetToken(String token);
}
