package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.ForbiddenFuel;
import com.charlyparkingapps.db.object.Fuel;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;

public class ForbiddenFuelDB extends ObjectRepository {

	// Singleton stuff
	private static ForbiddenFuelDB sInstance;

	public static ForbiddenFuelDB getInstance() {
		return sInstance;
	}

	public static ForbiddenFuelDB init(Context context) {
		sInstance = new ForbiddenFuelDB(context);
		return sInstance;
	}

	public static final String[] ALL_COLUMNS = { "forbiddenFuelId", "parking",
			"forbiddent" };

	public ForbiddenFuelDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "ForbiddenFuel";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE ForbiddenFuel (forbiddenFuelId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, parking INTEGER NOT NULL , forbidden INTEGER NOT NULL );";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO ForbiddenFuel VALUES(1,3);");
	}

	@Override
	public boolean checkConstraint(Model entite) {
		return true;
	}

	public List<Fuel> getForbiddentFuelforParking(Parking parking) {
		Cursor c = myBDD.query(getTablename(), getAllColumns(),
				"parking=?",
				new String[] { String.valueOf(parking.getParkingId()) }, null,
				null, null);

		List<Fuel> liste = new ArrayList<Fuel>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();
		do {
			ForbiddenFuel model = new ForbiddenFuel(c);
			liste.add(model.getFuel());
		} while (c.moveToNext());
		c.close();

		return liste;
	}

	@Override
	public List<Model> convertCursorToListObject(Cursor c) {
		List<Model> liste = new ArrayList<Model>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();

		do {

			ForbiddenFuel model = new ForbiddenFuel(c);

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
		return new ForbiddenFuel(cursor);
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
