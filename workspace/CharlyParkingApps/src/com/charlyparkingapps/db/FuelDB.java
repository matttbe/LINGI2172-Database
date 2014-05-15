package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Fuel;
import com.charlyparkingapps.db.object.Model;

public class FuelDB extends ObjectRepository {
	// Singleton stuff
	private static FuelDB sInstance;

	public static FuelDB getInstance() {
		return sInstance;
	}

	public static FuelDB init(Context context) {
		sInstance = new FuelDB(context);
		return sInstance;
	}

	private static final String[] ALL_COLUMNS = { "fuelId", "name" };

	public FuelDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "Fuel";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Fuel (fuelId INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT NOT NULL  UNIQUE );";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Fuel VALUES(1,'Diesel');"
				+ "INSERT INTO Fuel VALUES(2,'Gasoline');"
				+ "INSERT INTO Fuel VALUES(3,'LPG');"
				+ "INSERT INTO Fuel VALUES(4,'Ethanol');");

	}

	public Fuel getFuelByName(String name) {
		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS, "name=?",
				new String[] { name }, null,
				null, null);
		return new Fuel(cursor);
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
		return new Fuel(cursor);
	}

}
