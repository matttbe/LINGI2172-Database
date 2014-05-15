package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;

public class ParkingDB extends ObjectRepository {

	// Singleton stuff
	private static ParkingDB sInstance;
	public static ParkingDB getInstance() { return sInstance; }
	public static ParkingDB init(Context context) {
		sInstance = new ParkingDB(context);
		return sInstance;
	}


	private static final String[] ALL_COLUMNS = { "parkingId", "name",
		"defibrillator", "totalPlaces", "freePlaces", "maxHeight", "disable" };

	private static final double ONE_METER = 0.00000898 * 1.05; // with extras

	public ParkingDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "Parking";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Parking("
					+ "parkingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ "name varchar(20) NOT NULL, "
					+ "defibrillator BOOL DEFAULT 0, "
					+ "totalPlaces INTEGER CHECK (totalPlaces > 0), "
					+ "freePlaces INTEGER CHECK("
				+ "freePlaces <= totalPlaces AND freePlaces >= 0"
					+ ") DEFAULT 0, "
					+ "maxHeight INTEGER CHECK (maxHeight > 0), "
					+ "disable BOOL DEFAULT 0"
				+ ")";
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Parking VALUES(1,'Sainte Barbe',1,150,10,999300,0);");
		db.execSQL("INSERT INTO Parking VALUES(2,'P22',0,75,65,185,1);");
		db.execSQL("INSERT INTO Address VALUES(0,1,'Place Sainte Barbe',1,'Louvain-la-Neuve',1348,'BE',50.667408,4.62202);");
		db.execSQL("INSERT INTO Parking VALUES(3,'Hotel de Ville',0,150,10,110,1);");
		db.execSQL("INSERT INTO Address VALUES(1,3,'Place de Hotel de Ville',0,'Saint-Quentin',02100,'FR',0.0,0.0);");
		db.execSQL("INSERT INTO Parking VALUES(4,'Saleya',1,170,170,310,0);");
		db.execSQL("INSERT INTO Address VALUES(2,4,'Cours Saleya',0, 06300, 'Nice', 'FR',0.0,0.0);");
		db.execSQL("INSERT INTO Parking VALUES(5,'Acropolis - Jean Bouin',0,70,15,10,1);");
		db.execSQL("INSERT INTO Address VALUES(3,5,'Place du XVe Corps',0,'Nice',06000, 'FR', 0.0,0.0);");
		db.execSQL("INSERT INTO Parking VALUES(6,'Palais de Justice',0,70,150,10,0);");
		db.execSQL("INSERT INTO Address VALUES(4,6,'Place du Palais de Justice',0, 'Nice', 06000, FR, 0.0, 0.0);");
		db.execSQL("INSERT INTO Parking VALUES(7,'Pink Paradise',0,1,0,180,0);");
		db.execSQL("INSERT INTO Address VALUES(5,7, 'Rue de Ponthieu',59, 'Paris', 75008, FR, 0.0,0.0);");
		/*
		 * Barla Rue Auguste Gal 06300 Nice FRANCE Massena Place Masséna 06000
		 * Nice FRANCE Louvre 20, Boulevard Victor Hugo 06000 Nice FRANCE Lenval
		 * 57, Avenue de la Californie 06200 Nice FRANCE
		 */
	}

	@Override
	public boolean checkConstraint(Model entite) {
		Parking parking = (Parking) entite;
		return parking.getTotalPlaces() > 0
				&& parking.getFreePlaces() < parking.getTotalPlaces()
				&& parking.getFreePlaces() >= 0 && parking.getMaxHeight() >= 0;
	}


	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();
		do {
			Parking parking = new Parking(c);
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

		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS,
				"lat > ? AND lat < ? AND lon > ? AND lon < ?",
				new String[] { String.valueOf(latMin), String.valueOf(latMax),
			String.valueOf(lonMin), String.valueOf(lonMax) }, null,
			null, null);

		return this.convertCursorToListObject(cursor);
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
		return new Parking(cursor);
	}

}
