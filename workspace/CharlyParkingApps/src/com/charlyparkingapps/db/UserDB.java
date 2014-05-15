package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.User;

public class UserDB extends ObjectRepository {

	// Singleton stuff
	private static UserDB sInstance;
	public static UserDB getInstance() { return sInstance; }
	public static UserDB init(Context context) {
		sInstance = new UserDB(context);
		return sInstance;
	}


	private static final String[] ALL_COLUMNS = { "userId", "username", "type",
			"password", "favoriteCar" };

	public UserDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "User";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE User("
					+ "userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ "type INTEGER NOT NULL CONSTRAINT chk_type "
						+ "CHECK (type IN (0, 1, 2)), "
					+ "username varchar(20) NOT NULL UNIQUE, "
					+ "password varchar(20) NOT NULL"
 + "favoriteCar INT"
				+ ")";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO User VALUES(1, 1, 'user','abcd',0)");
		db.execSQL("INSERT INTO User VALUES(2, 2, 'user2','abcd',0)");
		db.execSQL("INSERT INTO User VALUES(3, 0, 'user3','abcd',0)");
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean checkConstraint(Model entite) {
		User user = (User) entite;
		return user.getType().ordinal() < 3 && user.getType().ordinal() >= 0;
	}

	public boolean login(String mEmail, String password) {
		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS,
				"username =? AND " + "password =?",
				new String[] { String.valueOf(mEmail), password }, null, null,
				null);

		return cursor.moveToFirst();
	}

	public User getByUsername(String username) {
		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS,
				"username=?", new String[] { username }, null, null, null);

		if (cursor.moveToFirst())
			return new User(cursor);
		else
			return null;

	}

	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();

		do {

			User user = new User(c);

			liste.add(user);
		} while (c.moveToNext());

		c.close();

		return liste;
	}

	@Override
	public String getUniqueColumn() {
		return ALL_COLUMNS[0];
	}

	@Override
	public String[] getAllColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public Model createFromCursor(Cursor cursor) {
		return new User(cursor);
	}

}
