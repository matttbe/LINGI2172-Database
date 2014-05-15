package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Corner;
import com.charlyparkingapps.db.object.Model;

public class CornerDB extends ObjectRepository {

	// Singleton stuff
	private static CornerDB sInstance;

	public static CornerDB getInstance() {
		return sInstance;
	}

	public static CornerDB init(Context context) {
		sInstance = new CornerDB(context);
		return sInstance;
	}

	public CornerDB(Context context) {
		super(context);
	}

	private static final String[] ALL_COLUMNS = { "cornerId", "parking",
			"latitude", "longitude" };

	@Override
	public String getTablename() {
		return "Corners";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Corners (cornerId INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, parking INTEGER NOT NULL, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId));";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Corners VALUES(0, 1, 50.667905, 4.621993);");
		db.execSQL("INSERT INTO Corners VALUES(0, 1, 50.667876, 4.621063);");
		db.execSQL("INSERT INTO Corners VALUES(0, 1, 50.667556, 4.621084);");
		db.execSQL("INSERT INTO Corners VALUES(0, 1, 50.667299, 4.621462);");
		db.execSQL("INSERT INTO Corners VALUES(0, 1, 50.667408, 4.62202););");
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

			Corner model = new Corner(c);

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
		return new Corner(cursor);
	}

}
