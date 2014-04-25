package com.charlyparkingapps.db.object;

public class User {
	
	private String username;
	
	
	public User(String usernameParam){
		this.setUsername(usernameParam);
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

}
