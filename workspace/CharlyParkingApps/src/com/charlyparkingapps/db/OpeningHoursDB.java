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
		db.execSQL ("INSERT INTO OpeningHours VALUES(2,0, 6, '00:00:00','23:59:59',2);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(3,0, 6, '00:00:00','23:59:59',3);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(4,0, 6, '00:00:00','23:59:59',4);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(5,0, 6, '00:00:00','23:59:59',5);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(6,0, 6, '00:00:00','23:59:59',6);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(7,0, 6, '00:00:00','23:59:59',7);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(8,0, 6, '00:00:00','23:59:59',8);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(9,0, 6, '00:00:00','23:59:59',9);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(10,0, 6, '00:00:00','23:59:59',10);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(11,0, 6, '00:00:00','23:59:59',11);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(12,0, 6, '00:00:00','23:59:59',12);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(13,0, 6, '00:00:00','23:59:59',13);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(14,0, 6, '00:00:00','23:59:59',14);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(15,0, 3, '00:00:00','22:59:59',15);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(16,4, 6, '00:00:00','23:59:59',15);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(17,0, 6, '07:00:00','20:59:59',16);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(18,0, 6, '06:00:00','20:59:59',17);");
		db.execSQL ("INSERT INTO OpeningHours VALUES(19,0, 6, '00:00:00','20:59:59',18);");

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
