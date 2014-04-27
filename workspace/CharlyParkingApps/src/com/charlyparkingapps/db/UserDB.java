package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.ObjectRepository;
import com.charlyparkingapps.db.object.User;

public class UserDB extends ObjectRepository {

	private User user = new User();

	public UserDB(Context context) {
		this.setContext(context);
		sqLiteOpenHelper = new CharlyAppHelper(context, null);
	}

	public UserDB() {
	}

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
	public void populate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean checkConstraint(Model entite) {
		User user = (User) entite;
		return user.getType() < 3 && user.getType() >= 0;
	}

	@Override
	public Model getObject() {
		return this.user;
	}

	public boolean login(String mEmail, String password) {
		Cursor cursor = myBDD.query(getTablename(), User.ALL_COLUMNS,
				"username =? AND " + "password =?",
				new String[] { String.valueOf(mEmail), password }, null, null,
				null);

		return cursor.moveToFirst();
	}

	public User getByUsername(String username) {
		Cursor cursor = myBDD.query(getTablename(), User.ALL_COLUMNS,
				"username=?", new String[] { username }, null, null, null);

		if (cursor.moveToFirst())
			return new User(cursor, this.getContext());
		else
			return null;

	}

	public List<Object> convertCursorToListObject(Cursor c) {
		List<Object> liste = new ArrayList<Object>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();

		do {

			User user = new User(c, this.getContext());

			liste.add(user);
		} while (c.moveToNext());

		c.close();

		return liste;
	}

}
