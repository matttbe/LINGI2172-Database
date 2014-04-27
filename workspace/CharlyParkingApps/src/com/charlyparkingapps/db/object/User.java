package com.charlyparkingapps.db.object;

import android.content.Context;
import android.database.Cursor;

public class User implements Model {

	public static final String[] ALL_COLUMNS = { "userId", "username", "type",
			"password" };

	private int id;
	private String username;
	private String password;
	private int type;

	public User(String usernameParam, int typeParam, String passwordParam) {
		this.setUsername(usernameParam);
		this.setType(typeParam);
		this.setPassword(passwordParam);
	}

	public User(Cursor c, Context context) {
		createFromCursor(c, context);
	}

	public User() {
	}

	public Model createFromCursor(Cursor c, Context context) {
		this.setId(c.getInt(0));
		this.setType(c.getInt(1));
		this.setUsername(c.getString(2));
		return this;
	}

	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(getId());
		case 1:
			return getUsername();
		case 2:
			return String.valueOf(getType());
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String[] getAllColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public String getUniqueColumn() {
		return this.getAllColumns()[0];
	}

}
