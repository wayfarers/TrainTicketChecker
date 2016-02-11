package org.genia.trainchecker.controllers;

import java.security.Principal;

import javax.inject.Inject;

import org.genia.trainchecker.entities.NewUser;
import org.genia.trainchecker.entities.User;
import org.genia.trainchecker.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @RequestMapping("/login")
    public String loginMe() {
        return "login";
    }
    
    @RequestMapping("/logout")
    public String getOutOfHere() {
        return "logout";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody boolean registerUser(@RequestBody NewUser newUser) {
    	System.out.println(newUser.toString());
    	User user = userService.createUser(newUser);
    	if (user != null && user.getId() != null && user.getId() != 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
