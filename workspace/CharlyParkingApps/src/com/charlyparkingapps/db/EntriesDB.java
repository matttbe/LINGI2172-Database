package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Entries;
import com.charlyparkingapps.db.object.Model;

public class EntriesDB extends ObjectRepository {
	
	// Singleton stuff
	private static EntriesDB sInstance;
	public static EntriesDB getInstance() { return sInstance; }
	public static EntriesDB init(Context context) {
		sInstance = new EntriesDB(context);
		return sInstance;
	}
	
	public static final String[] ALL_COLUMNS = { "entryId", "parking",
			"latitude", "longitude" };

	public EntriesDB(Context context) {
		super(context);
	}
	
	@Override
	public String getTablename() {
		return "Entries";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Entries("
				+ "entryId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "parking INTEGER NOT NULL, "
				+ "latitude DOUBLE NOT NULL, "
				+ "longitude DOUBLE NOT NULL, "
				+ "FOREIGN KEY(parking) REFERENCES Parking(parkingId)"
				+ ")";
	}

	@Override
	public void populate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkConstraint(Model entite) {
		return true;
	}

	@Override
	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();

		do {

			Entries entries = new Entries(c);

			liste.add(entries);
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
		return new Entries(cursor);
	}

}
