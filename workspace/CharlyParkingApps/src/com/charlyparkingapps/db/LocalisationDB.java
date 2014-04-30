package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Localisation;
import com.charlyparkingapps.db.object.Model;

public class LocalisationDB extends ObjectRepository {
	
	// Singleton stuff
	private static LocalisationDB sInstance;
	public static LocalisationDB getInstance() { return sInstance; }
	public static LocalisationDB init(Context context) {
		sInstance = new LocalisationDB(context);
		return sInstance;
	}
	
	public static final String[] ALL_COLUMNS = { "localisationId", "parking",
			"latitude", "longitude" };

	public LocalisationDB(Context context) {
		super(context);
	}
	
	@Override
	public String getTablename() {
		return "Localisation";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Localisation("
				+ "localisationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "parking INTEGER NOT NULL, "
				+ "latitude DOUBLE NOT NULL, "
				+ "longitude DOUBLE NOT NULL, "
				+ "FOREIGN KEY(parking) REFERENCES Parking(parkingId)"
				+ ")";
	}

	@Override
	public void populate(SQLiteDatabase db) {
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

			Localisation localisation = new Localisation(c);

			liste.add(localisation);
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
		return new Localisation(cursor);
	}

}
