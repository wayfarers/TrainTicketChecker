package org.genia.trainchecker.controllers;

import javax.inject.Inject;

import org.genia.trainchecker.entities.PasswordUpdate;
import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Inject
	UserService userService;
	
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public @ResponseBody void updateUser(@RequestBody User userInfo) {
		User currentUser = userService.getCurrentLoggedInUser();
		currentUser.setEmail(userInfo.getEmail());
		userService.saveUser(currentUser);
	}
	
	@RequestMapping(value = "updatePass", method = RequestMethod.POST)
	public @ResponseBody Integer updatePassword(@RequestBody PasswordUpdate userInfo) {
		User currentUser = userService.getCurrentLoggedInUser();
		if (userInfo.getCurrentPassword().equals(currentUser.getPassword())) {
			currentUser.setPassword(userInfo.getNewPassword());
			userService.saveUser(currentUser);
			return 0;
		} else {
			return -1;
		}
	}
}
