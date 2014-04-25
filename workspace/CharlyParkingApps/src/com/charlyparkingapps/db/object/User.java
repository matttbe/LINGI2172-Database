package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class User {
	
	public static final String[] ALL_COLUMNS={"userId","type","username"};
	
	private int id;
	private String username;
	private int type;
	
	
	public User(String usernameParam, int typeParam){
		this.setUsername(usernameParam);
		this.setType(typeParam);
	}


	public User(Cursor c) {
		this.setId(c.getInt(0));
		this.setType(c.getInt(1));
		this.setUsername(c.getString(2));
	}
	
	public String getByInt(int i){
		switch(i){
			case 0:
				return String.valueOf(getId());
			case 1:
				return String.valueOf(getType());
			case 2:
				return getUsername();
		}
		return String.valueOf(getId());		
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

}
