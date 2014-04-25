package com.charlyparkingapps.db.object;

public class User {
	
	private String username;
	private int type;
	
	
	public User(String usernameParam, int typeParam){
		this.setUsername(usernameParam);
		this.setType(typeParam);
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

}
