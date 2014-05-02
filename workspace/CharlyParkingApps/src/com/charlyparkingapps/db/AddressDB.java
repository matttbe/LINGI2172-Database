package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Address;
import com.charlyparkingapps.db.object.Model;

public class AddressDB extends ObjectRepository {

	// Singleton stuff
	private static AddressDB sInstance;
	public static AddressDB getInstance() { return sInstance; }
	public static AddressDB init(Context context) {
		sInstance = new AddressDB(context);
		return sInstance;
	}


	private static final String[] ALL_COLUMNS = { "addressId", "parking",
			"street", "number", "city", "zip", "country", "latitude",
			"longitude" };

	public AddressDB(Context context) {
		super(context);
	}

	@Override
	public String getTablename() {
		return "Address";
	}

	@Override
	public String getCreateRequest() {
		return "CREATE TABLE Address("
					+ "addressId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ "parking INTEGER NOT NULL, "
					+ "street TEXT NOT NULL, "
					+ "number INTEGER NOT NULL, "
					+ "city TEXT NOT NULL, "
					+ "zip INTEGER NOT NULL, "
					+ "country TEXT NOT NULL, "
					+ "latitude DOUBLE NOT NULL, "
					+ "longitude DOUBLE NOT NULL, "
					+ "FOREIGN KEY(parking) REFERENCES Parking(parkingId)"
				+ ")";
	}

	@Override
	public void populate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
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

			Address address = new Address(c);

			liste.add(address);
		} while (c.moveToNext());

		c.close();

		return liste;
	}

	public Address getByIdParking(int id) {
		Cursor cursor = myBDD.query(getTablename(), ALL_COLUMNS, ALL_COLUMNS[1]
				+ "=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor.moveToFirst())
			return new Address(cursor);
		else
			return null;
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
		return new Address(cursor);
	}

}
