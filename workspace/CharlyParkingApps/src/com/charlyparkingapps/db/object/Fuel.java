package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class Fuel implements Model {

	private int fuelId;
	private String name = "";

	public Fuel(String nameParam) {
		this.setName(nameParam);
	}

	public Fuel(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getFuelId());
		case 1:
			return this.getName();
		default:
			return String.valueOf(this.getFuelId());
		}

	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setFuelId(c.getInt(0));
		this.setName(c.getString(1));
		return this;
	}

	public int getFuelId() {
		return fuelId;
	}

	public void setFuelId(int fuelId) {
		this.fuelId = fuelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
