package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class User implements Model {

	private int id;
	private String username;
	private String password;
	private UserType type;

	public User(String usernameParam, UserType typeParam, String passwordParam) {
		this.setUsername(usernameParam);
		this.setType(typeParam);
		this.setPassword(passwordParam);
	}

	public User(Cursor c) {
		createFromCursor(c);
	}

	public Model createFromCursor(Cursor c) {
		this.setId(c.getInt(0));
		this.setUsername (c.getString (1));
		this.setType (UserType.values ()[c.getInt (2)]);

		return this;
	}

	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(getId());
		case 1:
			return getUsername();
		case 2:
			return String.valueOf(getType().ordinal());
		case 3:
			return String.valueOf(getPassword());

		}
		return String.valueOf(getId());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public enum UserType {
		USER, OWNER, ADMIN;
	}
}
