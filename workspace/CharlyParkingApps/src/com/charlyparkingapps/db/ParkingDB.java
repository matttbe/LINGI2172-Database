package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.ObjectRepository;
import com.charlyparkingapps.db.object.Parking;

public class ParkingDB extends ObjectRepository {
	
	private Parking parking;
	
	private Context context;
	
	public ParkingDB(Context context) {
		this.context=context;
		sqLiteOpenHelper = new CharlyAppHelper(context, null);
	}

	public ParkingDB() {
		
	}

	@Override
	public void Save(Object entite) {
		Parking parking = (Parking) entite;
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < Parking.ALL_COLUMNS.length; i++) {
			contentValues.put(Parking.ALL_COLUMNS[i], parking.getByInt(i));
		}
		maBDD.insert(getTablename(), null, contentValues);
	}

	@Override
	public void Update(Object entite) {
		Parking parking = (Parking) entite;
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < Parking.ALL_COLUMNS.length; i++) {
			contentValues.put(Parking.ALL_COLUMNS[i], parking.getByInt(i));
		}

		maBDD.update(getTablename(), contentValues, Parking.ALL_COLUMNS[0] + "=?",
				new String[] { String.valueOf(parking.getParkingId()) });

	}

	@Override
	public void Delete(int id) {
		maBDD.delete(getTablename(), Parking.ALL_COLUMNS[0] + "=?",
				new String[] { String.valueOf(id) });
	}

	@Override
	public String getTablename() {
		return "Parking";
	}

	@Override
	public String getRequete() {
		return "CREATE TABLE Parking(parkingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name varchar(20) NOT NULL, defibrillator BOOL DEFAULT 0, totalPlaces INTEGER CHECK (totalPlaces > 0),freePlaces INTEGER CHECK(freePlaces < totalPlaces AND freePlaces >= 0) DEFAULT totalPlaces, maxHeight INTEGER CHECK (maxHeight > 0))";
	}

	@Override
	public boolean checkConstraint() {
		return parking.getTotalPlaces() > 0 && parking.getFreePlaces() < parking.getTotalPlaces() && parking.getFreePlaces() >= 0 && parking.getMaxHeight() >=0;
	}

	@Override
	public Model getObject() {
		return this.parking;
	}
	
	
	public List<Object> ConvertCursorToListObject(Cursor c) {
		List<Object> liste = new ArrayList<Object>();
		if (c.getCount() == 0)
			return liste;
		c.moveToFirst();
		do {
			Parking parking = new Parking(c,context);
			liste.add(parking);
		} while (c.moveToNext());
		c.close();
		return liste;
	}


}
