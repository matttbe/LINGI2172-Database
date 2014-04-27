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

	private Address address = new Address();

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
	public Model getObject() {
		return address;
	}

	@Override
	public List<Object> convertCursorToListObject(Cursor c) {
		List<Object> liste = new ArrayList<Object>();
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
		Cursor cursor = myBDD.query(getTablename(), address.getAllColumns(),
				address.getByInt(1) + "=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor.moveToFirst())
			return new Address(cursor, context);
		else
			return null;
	}

}
