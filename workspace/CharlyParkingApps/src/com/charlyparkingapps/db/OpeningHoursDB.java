package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.OpeningHours;
import com.charlyparkingapps.db.object.Parking;

public class OpeningHoursDB extends ObjectRepository {

	// CREATE TABLE OpeningHours(dayStart INTEGER NOT NULL CHECK (dayStart <= 6
	// AND dayStart >= 0),
	// dayEnd INTEGER NOT NULL CHECK (dayEnd <= 6 AND dayEnd >= 0),
	// hourStart TIME NOT NULL, hourEnd TIME NOT NULL, parking INTEGER NOT NULL,
	// FOREIGN KEY(parking) REFERENCES Parking(parkingId))

	// Singleton stuff
	private static OpeningHoursDB sInstance;

	public static OpeningHoursDB getInstance() {
		return sInstance;
	}

	public static OpeningHoursDB init(Context context) {
		sInstance = new OpeningHoursDB(context);
		return sInstance;
	}

	private static final String[] ALL_COLUMNS = { "parkingId", "name",
			"defibrillator", "totalPlaces", "freePlaces", "maxHeight",
			"disable", "user" };

	public OpeningHoursDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "OpeningHours";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE OpeningHours(dayStart INTEGER NOT NULL CHECK (dayStart <= 6 AND dayStart >= 0), "
				+ "dayEnd INTEGER NOT NULL CHECK (dayEnd <= 6 AND dayEnd >= 0),"
				+ "hourStart TIME NOT NULL, "
				+ "hourEnd TIME NOT NULL, "
				+ "parking INTEGER NOT NULL,"
				+ "FOREIGN KEY(parking) REFERENCES Parking(parkingId))";
	}

	public List<Model> getAllOpeningHoursForParking(Parking parking) {
		Cursor cursor = myBDD.query(getTablename(), getAllColumns(),
				"parking=?",
				new String[] { String.valueOf(parking.getParkingId()) }, null,
				null, "start");

		return convertCursorToListObject(cursor);
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO OpeningHours VALUES(1,0, 6, '00:00:00','23:59:59',1);");

	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public boolean checkConstraint(Model entite) {
		OpeningHours h = (OpeningHours) entite;
		return h.getDayStart() <= 6 && h.getDayStart() >= 0
				&& h.getDayEnd() <= 6 && h.getDayEnd() >= 0;
	}

	@Override
	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();
		do {
			OpeningHours model = new OpeningHours(c);
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
		return new OpeningHours(cursor);
	}

}
