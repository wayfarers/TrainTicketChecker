package org.genia.trainchecker.services;

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
	
	public User createUser(NewUser newUser) {
		User user = new User();
		user.setName(newUser.getUsername());
		user.setLogin(newUser.getUsername());
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());
		user.setRole(Role.USER);
		user = userRepository.save(user);
		return user;
	}
	
	public User getCurrentLoggedInUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByLogin(username);
		return user;
	}
}
