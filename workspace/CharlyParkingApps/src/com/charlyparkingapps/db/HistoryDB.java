package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Fuel;
import com.charlyparkingapps.db.object.History;
import com.charlyparkingapps.db.object.Model;

public class HistoryDB extends ObjectRepository {
	// Singleton stuff
	private static HistoryDB sInstance;

	public static HistoryDB getInstance() {
		return sInstance;
	}

	public static HistoryDB init(Context context) {
		sInstance = new HistoryDB(context);
		return sInstance;
	}

	private static final String[] ALL_COLUMNS = { "historyId", "user" };

	public HistoryDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "History";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE History (historyId INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , user INTEGER PRIMARY KEY NOT NULL ,start DATETIME NOT NULL DEFAULT (null) ,end DATETIME NOT NULL DEFAULT (null),parking INTEGER NOT NULL );";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO History VALUES(1,'2007-01-01 10:00:00','2007-01-01 11:00:00',1);");
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public List<Model> getAllOrdered() {
		Cursor cursor = myBDD.query(getTablename(), getAllColumns(), null,
				null, null, null, "start");

		return convertCursorToListObject(cursor);
	}

	@Override
	public boolean checkConstraint(Model entite) {
		History h = (History) entite;
		return h.getStart().before(h.getEnd());
	}

	@Override
	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();

		do {

			Fuel model = new Fuel(c);

			liste.add(model);
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
		return new History(cursor);
	}

}
