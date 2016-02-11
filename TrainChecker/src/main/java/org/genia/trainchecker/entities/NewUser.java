package org.genia.trainchecker.entities;

import javax.inject.Named;

@Named
public class NewUser {
	private String username;
	private String password;
	private String email;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String login) {
		this.username = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("Login: %s, Password: %s, Email: %s", username, password, email);
	}
}
