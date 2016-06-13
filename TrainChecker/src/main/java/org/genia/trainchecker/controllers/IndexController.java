package org.genia.trainchecker.controllers;

import java.security.Principal;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.genia.trainchecker.entities.NewUser;
import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Inject
	private UserService userService;

    @RequestMapping
    public String getIndexPage() {
        return "index";
    }
    
    @RequestMapping("/user")
    public @ResponseBody Principal user(Principal user) {
    	return user;
    }
    
    @RequestMapping("/userInfo")
    public @ResponseBody User getUserInfo() {
    	User userInfo = new User();
    	User loggedUser = userService.getCurrentLoggedInUser();
    	if (loggedUser != null) {
    		userInfo.setLogin(loggedUser.getLogin());
    		userInfo.setName(loggedUser.getName());
    		userInfo.setEmail(loggedUser.getEmail());
    		userInfo.setRole(loggedUser.getRole());
    	}
    	return userInfo;
    }
    
    @RequestMapping("/login")
    public String loginMe() {
        return "login";
    }
    
    @RequestMapping("/logout")
    public String getOutOfHere() {
        return "logout";
    }
    
    @RequestMapping("/requestReset")
    public @ResponseBody String generateResetLink(String login) throws JsonProcessingException {
    	if (StringUtils.isEmpty(login)) {
    		return new ObjectMapper().writer().writeValueAsString("no_user");
    	}
    	
    	int code = userService.generateResetLink(login);
    	
    	String msg;
    	
    	switch (code) {
		case 0:
			msg = "ok";
			break;
		case 1:
			msg = "no_user";
			break;
		case 2:
			msg = "failed";
			break;
		default:
			msg = "failed";
		}
    	
    	return new ObjectMapper().writer().writeValueAsString(msg);
    }
    
    @RequestMapping("/checkLink")
    public @ResponseBody Integer checkLink(String tk) {
    	User user = userService.findUserByToken(tk);
    	if(user != null) {
    		return user.getId();
    	}
        return -1;
    }
    
    @RequestMapping("/setNewPass")
    public @ResponseBody Integer setNewPass(String tk, String pw) {
    	User user = userService.findUserByToken(tk);
    	if(user != null) {
    		user.setPassword(pw);
    		user.setPassResetToken(null);
    		userService.saveUser(user);
    		return 0;
    	}
        return -1;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody int registerUser(@RequestBody NewUser newUser) {
    	if (!checkUsername(newUser.getUsername())) {
    		return 1;
    	}
    	User user = userService.createUser(newUser);
    	if (user != null && user.getId() != null && user.getId() != 0) {
    		return 0;
    	} else {
    		return -1;
    	}
    }
    
    private boolean checkUsername(String username) {
    	return userService.checkUsernameAvailability(username);
    }
}
