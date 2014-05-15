package com.charlyparkingapps.db.object;

import android.database.Cursor;

import com.charlyparkingapps.db.FuelDB;
import com.charlyparkingapps.db.ParkingDB;

public class ForbiddenFuel implements Model {

	// CREATE TABLE "ForbiddenFuel" ("parking" INTEGER NOT NULL , "forbidden"
	// INTEGER NOT NULL );

	private int forbiddenFuelId;
	private int parkingId;
	private int forbiddentId;
	
	private Parking parking;
	private Fuel fuel;

	public ForbiddenFuel(int parkingIdParam, int forbiddentIdParam) {
		this.setParkingId(parkingIdParam);
		this.setForbiddenFuelId(forbiddentIdParam);
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
			return String.valueOf(this.getParkingId());
		case 2:
			return String.valueOf(this.getForbiddentId());
		default:
			return String.valueOf(this.getForbiddenFuelId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setForbiddenFuelId(c.getInt(0));
		this.setParkingId(c.getInt(1));
		this.setForbiddenFuelId(c.getInt(2));
		return this;
	}

	public int getForbiddenFuelId() {
		return forbiddenFuelId;
	}

	public void setForbiddenFuelId(int forbiddenFuelId) {
		this.forbiddenFuelId = forbiddenFuelId;
	}

	public int getParkingId() {
		return parkingId;
	}

	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}

	public int getForbiddentId() {
		return forbiddentId;
	}

	public void setForbiddentId(int forbiddentId) {
		this.forbiddentId = forbiddentId;
	}

	public void loadParking() {
		ParkingDB p = ParkingDB.getInstance();
		p.open(false);
		this.setParking((Parking) p.getById(this.getParkingId()));
		p.close();
	}

	public Parking getParking() {
		if (parking == null) {
			loadParking();
		}
		return this.parking;

	}

	public void loadFuel() {
		FuelDB p = FuelDB.getInstance();
		p.open(false);
		this.setFuel((Fuel) p.getById(this.getForbiddentId()));
		p.close();
	}


	public void setParking(Parking parking) {
		this.parking = parking;
		this.setParkingId(parking.getParkingId());
	}

	public Fuel getFuel() {
		if (fuel == null) {
			loadFuel();
		}
		return this.fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
		this.setForbiddentId(fuel.getFuelId());
	}

}
