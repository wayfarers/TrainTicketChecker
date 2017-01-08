package org.genia.trainchecker.services;

import java.util.UUID;

import javax.inject.Inject;

import org.genia.trainchecker.entities.NewUser;
import org.genia.trainchecker.entities.Role;
import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private NotificationService notificationService;

    public User createUser(NewUser newUser) {
        User user = new User();
        user.setName(newUser.getUsername());
        user.setLogin(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setRole(Role.USER);
        user.setEnabled(true);
        user = userRepository.save(user);
        return user;
    }

    public User getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(username);
    }

    /**
     * Generates reset-password link and sends in to user's email.
     * @param login
     * @return 0 if success, 1 if no such user found, 2 if other error.
     */
    public Integer generateResetLink(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            return 1;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setPassResetToken(uuid);
        userRepository.save(user);

        notificationService.sendResetPassLink(user);

        return 0;
    }

    public User findUserByToken(String token) {
        return userRepository.findByPassResetToken(token);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean checkUsernameAvailability(String username) {
        User user = userRepository.findByLogin(username);
        return user == null;
    }
}
