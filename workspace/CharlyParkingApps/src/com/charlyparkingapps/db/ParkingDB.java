package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.charlyparkingapps.activities.FiltersActivity;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;
import com.charlyparkingapps.db.object.User;

public class ParkingDB extends ObjectRepository {

	// Singleton stuff
	private static ParkingDB sInstance;
	public static ParkingDB getInstance() { return sInstance; }
	public static ParkingDB init(Context context) {
		sInstance = new ParkingDB(context);
		return sInstance;
	}


	private static final String[] ALL_COLUMNS = { "parkingId", "name",
			"defibrillator", "totalPlaces", "freePlaces", "maxHeight",
			"disable", "user" };

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
				+ ") DEFAULT 0, " + "maxHeight INTEGER CHECK (maxHeight > 0), "
				+ "disable BOOL DEFAULT 0," + "user INTEGER"
				+ ")";
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void populate(SQLiteDatabase db) {
		/*
		 * "parkingId", "name","defibrillator", "totalPlaces", "freePlaces",
		 * "maxHeight","disable", "user"
		 */
		db.execSQL ("INSERT INTO Parking VALUES(1,'Sainte Barbe',1,150,10,250,0,1);");
		db.execSQL ("INSERT INTO Address VALUES(0,1,'Place Sainte Barbe',1,'Louvain-la-Neuve',1348,'BE',50.667408,4.62202);");
		db.execSQL ("INSERT INTO Parking VALUES(3,'Hotel de Ville',0,150,10,260,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(1,3,'Place de Hotel de Ville',0,'Saint-Quentin',02100,'FR',49.846122,3.287457);");
		db.execSQL ("INSERT INTO Parking VALUES(4,'Saleya',1,170,17,310,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(2,4,'Cours Saleya',0, 06300, 'Nice', 'FR',43.695607,7.4922699999934);");
		db.execSQL ("INSERT INTO Parking VALUES(5,'Acropolis - Jean Bouin',0,70,15,10,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(3,5,'Place du XVe Corps',0,'Nice',06000, 'FR', 43.7072969,7.28019119999999);");
		db.execSQL ("INSERT INTO Parking VALUES(6,'Palais de Justice',0,770,150,10,0,1);");
		db.execSQL ("INSERT INTO Address VALUES(4,6,'Place du Palais de Justice',0, 'Nice', 06000, 'FR', 43.696672, 7.273752000000059);");
		db.execSQL ("INSERT INTO Parking VALUES(7,'Pink Paradise',0,1,0,180,0,1);");
		db.execSQL ("INSERT INTO Address VALUES(5,7, 'Rue de Ponthieu',59, 'Paris', 75008, 'FR', 48.87214119999999,2.304941699999972);");
		db.execSQL ("INSERT INTO Parking VALUES(8,'City Parking',0,1,0,200,0,1);");
		db.execSQL ("INSERT INTO Address VALUES(6,8, 'Rue des Fossés',19, 'Braine-l Alleud', 1420, 'BE', 50.6836196,4.371213399999988);");
		db.execSQL ("INSERT INTO Parking VALUES(9,'Albertine Square',0,200,150,260,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(7,9, 'Place de la Justice',16, 'Bruxelles', 1000, 'BE', 50.8439031,4.3546111000000565);");
		db.execSQL ("INSERT INTO Parking VALUES(10,'Alhambra',0,200,150,300,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(8,10, 'Boulevard Emile Jacqmain',14, 'Bruxelles', 1000, 'BE',50.85251100000001,4.352997100000039);");
		db.execSQL ("INSERT INTO Parking VALUES(11,'Botanique',0,200,150,320,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(9,10, 'Boulevard du Jardin Botanique',29, 'Bruxelles', 1000, 'BE',50.85446100000001,4.36015969999994);");
		db.execSQL ("INSERT INTO Parking VALUES(12,'Centre',0,400,190,260,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(11,12, 'Rue du Damier',26, 'Bruxelles', 1000, 'BE',50.85320609999999,4.358081599999991);");
		db.execSQL ("INSERT INTO Parking VALUES(13,'City 2',0,500,239,270,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(12,13, 'Rue des Cendres',8, 'Bruxelles', 1000, 'BE',50.85465550000001,4.359839800000032);");
		db.execSQL ("INSERT INTO Parking VALUES(14,'Dansaert',0,457,96,290,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(13,14, 'Place du Nouveau Marché aux Grains',1, 'Bruxelles', 1000, 'BE',50.8507354,4.344914099999983);");
		db.execSQL ("INSERT INTO Parking VALUES(15,'Dansaert 2',0,190,150,280,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(14,15, 'Rue de Flandre',60, 'Bruxelles', 1000, 'BE',50.851665,4.34620689999997);");
		db.execSQL ("INSERT INTO Parking VALUES(16,'De Brouckère',0,500,150,300,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(15,16, 'Place De Brouckère',1, 'Bruxelles', 1000, 'BE',50.8510611,4.352438900000038);");
		db.execSQL ("INSERT INTO Parking VALUES(17,'Deux Portes',0,190,150,300,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(16,17, 'Boulevard de Waterloo',1, 'Bruxelles', 1000, 'BE',50.8388739,4.361384300000054);");
		db.execSQL ("INSERT INTO Parking VALUES(18,'Ecuyer',0,387,54,300,1,1);");
		db.execSQL ("INSERT INTO Address VALUES(17,18, 'Rue de l Ecuyer,',15, 'Bruxelles', 1000, 'BE',50.84927709999999,4.35346519999996);");
		/*
		 * Barla Rue Auguste Gal 06300 Nice FRANCE Massena Place Masséna 06000
		 * Nice FRANCE Louvre 20 , Boulevard Victor Hugo 06000 Nice FRANCE
		 * Lenval 57 , Avenue de la Californie 06200 Nice FRANCE
		 */

	}

	@Override
	public boolean checkConstraint(Model entite) {
		Parking parking = (Parking) entite;
		return parking.getTotalPlaces() > 0
				&& parking.getFreePlaces() < parking.getTotalPlaces()
				&& parking.getFreePlaces() >= 0 && parking.getMaxHeight() >= 0;
	}

	public List<Model> getMyParkings (User user)
	{
		Cursor cursor = myBDD.query (getTablename (), getAllColumns (),
				"user=?", new String[] { String.valueOf (user.getId ()) },
				null, null, null);

		return convertCursorToListObject (cursor);
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

	public List<Model> getParkingsWithFilters(Context context) {

		//1 = true, 0 = false
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String def = (prefs.getBoolean(FiltersActivity.DEFIBRILATOR_PREF,false)) ? "1":"0,1";
		String dis = (prefs.getBoolean(FiltersActivity.HANDICAPED_PREF, false)) ? "1":"0,1";

		String fuels = "";
		fuels += (prefs.getBoolean(FiltersActivity.FUEL_PREF,false)) ? "'Gasoline',":"";
		fuels += (prefs.getBoolean(FiltersActivity.DIESEL_PREF,false)) ? "'Diesel',":"";
		fuels += (prefs.getBoolean(FiltersActivity.LPG_PREF,false)) ? "'LPG',":"";
		fuels += (prefs.getBoolean(FiltersActivity.ETHANOL_PREF,false)) ? "'Ethanol',":"";
		fuels = fuels.length()>0 ? fuels.substring(0, fuels.length()-1) : "";
		
		String getFuels = fuels.length() > 0 ? "AND (P.parkingId = FF.parking) AND F.name NOT IN ("
				+ fuels + ") "
				: "";
		
		String totPlaces = "AND totalPlaces >= "
				+ prefs.getInt(FiltersActivity.TOTALPLACES_PREF, 0) + " ";

		String freePlaces = "AND freePlaces >= "
				+ prefs.getInt(FiltersActivity.FREEPLACES_PREF, 0) + " ";

		String oneFree = (prefs.getBoolean(FiltersActivity.ONEFREESPOT_PREF,
				false)) ? "AND freePlaces < totalPlaces " : "";

		Cursor cursor = myBDD.rawQuery(
				"SELECT * FROM Parking P, ForbiddenFuel FF, Fuel F WHERE (P.parkingId = FF.parking OR P.parkingId != FF.parking) AND defibrillator IN (" + def
								+ ") AND disable IN ("
								+ dis
								+ ") "
								+ getFuels + totPlaces + freePlaces + oneFree, null);
		/*
		 * Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS,
		 * "defibrillator IN ("+def+") AND disable IN ("+dis+")", null, null,
		 * null, null);
		 */
		
		
		System.out.println((prefs.getBoolean(FiltersActivity.HANDICAPED_PREF, false) ? "1":"0,1"));
		System.out.println(cursor);
		return this.convertCursorToListObject(cursor);
	}

	public List<Model> getParkingsWithFreePlaces() {
		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS,
				"freePlaces < totalPlaces", null, null, null, null);

		return this.convertCursorToListObject(cursor);
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
