package com.charlyparkingapps.db;

import com.charlyparkingapps.db.object.User;

public class UserDB implements ObjectDB {
	
	private User user;

	@Override
	public String getTablename() {
		return "User";
	}

	@Override
	public String getRequete() {
		return "CREATE TABLE \"User\"(userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, type INTEGER NOT NULL "
				+ "CONSTRAINT chk_type CHECK (type IN (0, 1, 2)), "
				+ "username varchar(20) NOT NULL UNIQUE, password varchar(20) NOT NULL)";
	}

	@Override
	public boolean checkConstraint() {
		return user.getType()<3 && user.getType()>=0;
	}

	@Override
	public Object getObject() {
		return this.user;
	}

}
