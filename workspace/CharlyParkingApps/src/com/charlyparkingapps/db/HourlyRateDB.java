package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.History;
import com.charlyparkingapps.db.object.HourlyRate;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;

public class HourlyRateDB extends ObjectRepository {
	// Singleton stuff
	private static HourlyRateDB sInstance;

	public static HourlyRateDB getInstance() {
		return sInstance;
	}

	public static HourlyRateDB init(Context context) {
		sInstance = new HourlyRateDB(context);
		return sInstance;
	}
	
	public final static String[] ALL_COLUMNS = { "hourlyrateId", "start",
			"end", "cost", "parking" };

	// CREATE TABLE "HourlyRate" ("start" TIME PRIMARY KEY NOT NULL DEFAULT
	// (null) ,"end" TIME NOT NULL DEFAULT (null) ,"cost" FLOAT NOT NULL DEFAULT
	// (null) ,"parking" INTEGER NOT NULL )

	public HourlyRateDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "HourlyRate";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE HourlyRate ("
				+ "hourlyrateId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ "start TIME NOT NULL DEFAULT (null), "
				+ "end TIME DEFAULT (null), "
				+ "cost FLOAT NOT NULL DEFAULT (null),"
				+ "parking INTEGER NOT NULL );";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',1.35,1);");
		db.execSQL("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.1,1);");
		db.execSQL("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,1);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',1.50,2);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.25,2);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,2);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',3,3);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.1,3);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,3);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',1.80,4);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.50,4);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,4);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,5);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,5);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,5);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,6);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,6);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,6);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,7);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,7);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,7);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,8);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,8);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,8);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,9);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,9);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,9);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,10);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,10);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,10);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,11);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,11);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,11);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,12);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,12);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,12);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,13);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,13);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,13);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,14);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,14);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,14);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,15);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,15);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,15);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,16);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,16);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,16);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,17);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,17);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,17);");

		db.execSQL ("INSERT INTO HourlyRate VALUES(1,'00:00:00','01:00:00',2.10,18);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(2,'01:00:01','03:00:00',1.5,18);");
		db.execSQL ("INSERT INTO HourlyRate VALUES(3,'03:00:01','24:00:00',1,18);");

	}

	public List<Model> getAllHourlyRateForParking(Parking parking) {
		Cursor cursor = myBDD.query(getTablename(), getAllColumns(),
				"parking=?",
				new String[] { String.valueOf(parking.getParkingId()) }, null,
				null, "start");

		return convertCursorToListObject(cursor);
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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

			HourlyRate model = new HourlyRate(c);

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
