package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.ObjectRepository;
import com.charlyparkingapps.db.object.Parking;

public class ParkingDB extends ObjectRepository {

	private Parking parking = new Parking();

	private static final double ONE_METER = 0.00000898 * 1.05; // with extras

	private Context context;

	public ParkingDB(Context context) {
		this.context = context;
		sqLiteOpenHelper = new CharlyAppHelper(context, null);
	}

	public ParkingDB() {
	}

	@Override
	public String getTablename() {
		return "Parking";
	}

	@Override
	public String getRequete() {
		return "CREATE TABLE Parking(parkingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name varchar(20) NOT NULL, defibrillator BOOL DEFAULT 0, totalPlaces INTEGER CHECK (totalPlaces > 0),freePlaces INTEGER CHECK(freePlaces < totalPlaces AND freePlaces >= 0) DEFAULT totalPlaces, maxHeight INTEGER CHECK (maxHeight > 0))";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean checkConstraint(Model entite) {
		Parking parking = (Parking) entite;
		return parking.getTotalPlaces() > 0
				&& parking.getFreePlaces() < parking.getTotalPlaces()
				&& parking.getFreePlaces() >= 0 && parking.getMaxHeight() >= 0;
	}

	@Override
	public Model getObject() {
		return this.parking;
	}

	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();
		do {
			Parking parking = new Parking(context, c);
			liste.add(parking);
		} while (c.moveToNext());
		c.close();
		return liste;
	}

	public List<Model> getParkings(double latitude, double longitude,
			float radius) {
		double diffLat = ONE_METER * radius;
		double latMin = latitude - diffLat;
		double latMax = latitude + diffLat;
		double lonMin = longitude - diffLat / Math.cos(latMin);
		double lonMax = longitude + diffLat / Math.cos(latMax);

		Cursor cursor = myBDD.query(getTablename(), Parking.ALL_COLUMNS,
				"lat > ? AND lat < ? AND lon > ? AND lon < ?",
				new String[] { String.valueOf(latMin), String.valueOf(latMax),
						String.valueOf(lonMin), String.valueOf(lonMax) }, null,
				null, null);

		return this.convertCursorToListObject(cursor);
	}

}
