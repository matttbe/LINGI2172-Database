package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.Model;

public class CarDB extends ObjectRepository {

	// Singleton stuff
	private static CarDB sInstance;

	public static CarDB getInstance() {
		return sInstance;
	}

	public static CarDB init(Context context) {
		sInstance = new CarDB(context);
		return sInstance;
	}

	private static final String[] ALL_COLUMNS = { "carId", "height", "fuel",
			"user", "latitude", "longitude" };

	public CarDB(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTablename() {
		return "Car";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Car (carId INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , height INTEGER DEFAULT 0, fuel INTEGER DEFAULT 0, user INTEGER NOT NULL, latitude DOUBLE, longitude DOUBLE, FOREIGN KEY(user) REFERENCES User(userId), FOREIGN KEY(fuel) REFERENCES Fuel(fuelId));";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Car VALUES(1,162,1,1,NULL,NULL); "
				+ "INSERT INTO Car VALUES(2,165,3,1,50.667408,4.62202);"
				+ "INSERT INTO Car VALUES(3,0,0,2,50.667408,4.62202);");

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

			Car model = new Car(c);

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
		return new Car(cursor);
	}

}
