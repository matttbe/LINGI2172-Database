package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Address;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.ObjectRepository;

public class AddressDB extends ObjectRepository {

	public static final String[] ALL_COLUMNS = { "addressId", "parking",
			"street", "number", "city", "zip", "country", "latitude",
			"longitude" };

	private Context context;

	public AddressDB(Context context) {
		this.context = context;
		sqLiteOpenHelper = new CharlyAppHelper(context, null);
	}

	public AddressDB() {

	}

	@Override
	public String getTablename() {
		return "Address";
	}

	@Override
	public String getRequete() {
		return "CREATE TABLE Address(addressId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,parking INTEGER NOT NULL, street TEXT NOT NULL, number INTEGER NOT NULL, city TEXT NOT NULL, zip INTEGER NOT NULL, country TEXT NOT NULL, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId))";
	}

	@Override
	public void populate(SQLiteDatabase db) {
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

			Address address = new Address(c, this.getContext());

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
			return new Address(cursor, context);
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
	public Model createFromCursor(Cursor cursor, Context context) {
		return new Address(cursor, context);
	}

}
