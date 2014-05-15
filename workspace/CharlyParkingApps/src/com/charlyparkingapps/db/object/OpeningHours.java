package com.charlyparkingapps.db.object;

import java.sql.Time;

import android.database.Cursor;

import com.charlyparkingapps.db.ParkingDB;

public class OpeningHours implements Model {

	// CREATE TABLE OpeningHours(dayStart INTEGER NOT NULL CHECK (dayStart <= 6
	// AND dayStart >= 0),
	// dayEnd INTEGER NOT NULL CHECK (dayEnd <= 6 AND dayEnd >= 0),
	// hourStart TIME NOT NULL, hourEnd TIME NOT NULL, parking INTEGER NOT NULL,
	// FOREIGN KEY(parking) REFERENCES Parking(parkingId))

	private int openingsHoursId;
	private int dayStart;
	private int dayEnd;
	private Time hourStart;
	private Time hourEnd;
	private int parkingId;

	private Parking parking;

	public OpeningHours(int dayStartParam, int dayEndParam,
			Time hourStartParam, Time hourEndParam, int ParkingIdParam) {
		this.setDayStart(dayStartParam);
		this.setDayEnd(dayEndParam);
		this.setHourStart(hourStartParam);
		this.setHourEnd(hourEndParam);
		this.setParkingId(ParkingIdParam);
	}

	public OpeningHours(Cursor cursor) {
		this.createFromCursor(cursor);
	}


	@Override
	public String getByInt(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model createFromCursor(Cursor c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOpeningsHoursId() {
		return openingsHoursId;
	}

	public void setOpeningsHoursId(int openingsHoursId) {
		this.openingsHoursId = openingsHoursId;
	}

	public int getDayStart() {
		return dayStart;
	}

	public void setDayStart(int dayStart) {
		this.dayStart = dayStart;
	}

	public int getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(int dayEnd) {
		this.dayEnd = dayEnd;
	}

	public Time getHourStart() {
		return hourStart;
	}

	public void setHourStart(Time hourStart) {
		this.hourStart = hourStart;
	}

	public Time getHourEnd() {
		return hourEnd;
	}

	public void setHourEnd(Time hourEnd) {
		this.hourEnd = hourEnd;
	}

	public int getParkingId() {
		return parkingId;
	}

	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
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

	public void setParking(Parking parking) {
		this.parking = parking;
	}

}
