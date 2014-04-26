package com.charlyparkingapps.db;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.ObjectRepository;
import com.charlyparkingapps.db.object.Address;
import com.charlyparkingapps.db.object.Parking;

public class AddressDB extends ObjectRepository {
	
	private Address address;
	
	private Context context;
	
	public AddressDB(Context context) {
		this.context=context;
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
		return "CREATE TABLE Address(parking INTEGER NOT NULL, street TEXT NOT NULL, number INTEGER NOT NULL, city TEXT NOT NULL, zip INTEGER NOT NULL, country TEXT NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId))";
	}

	@Override
	public boolean checkConstraint() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> ConvertCursorToListObject(Cursor c) {
		// TODO Auto-generated method stub
		return null;
	}

}
