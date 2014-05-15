package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class ForbiddenFuel implements Model {

	// CREATE TABLE "ForbiddenFuel" ("parking" INTEGER NOT NULL , "forbidden"
	// INTEGER NOT NULL );

	private int forbiddenFuelId;
	private int parking_id;
	private int forbiddent_id;

	public ForbiddenFuel(int parking_idParam, int forbiddent_idParam) {
		this.setParking_id(parking_idParam);
		this.setForbiddenFuelId(forbiddent_idParam);
	}

	public ForbiddenFuel(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getForbiddenFuelId());
		case 1:
			return String.valueOf(this.getParking_id());
		case 2:
			return String.valueOf(this.getForbiddent_id());
		default:
			return String.valueOf(this.getForbiddenFuelId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setForbiddenFuelId(c.getInt(0));
		this.setParking_id(c.getInt(1));
		this.setForbiddenFuelId(c.getInt(2));
		return this;
	}

	public int getForbiddenFuelId() {
		return forbiddenFuelId;
	}

	public void setForbiddenFuelId(int forbiddenFuelId) {
		this.forbiddenFuelId = forbiddenFuelId;
	}

	public int getParking_id() {
		return parking_id;
	}

	public void setParking_id(int parking_id) {
		this.parking_id = parking_id;
	}

	public int getForbiddent_id() {
		return forbiddent_id;
	}

	public void setForbiddent_id(int forbiddent_id) {
		this.forbiddent_id = forbiddent_id;
	}

}
