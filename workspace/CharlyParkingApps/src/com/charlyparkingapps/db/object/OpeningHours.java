package com.charlyparkingapps.db.object;

import java.sql.Time;

import android.database.Cursor;
import android.util.Log;

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
		switch (i) {
		case 0:
			return String.valueOf(this.getOpeningsHoursId());
		case 1:
			return String.valueOf(this.getDayStart());
		case 2:
			return String.valueOf(this.getDayEnd());
		case 3:
			return this.getHourStart().toString();
		case 4:
			return this.getHourEnd().toString();
		case 5:
			return String.valueOf(this.getParkingId());
		default:
			return String.valueOf(this.getOpeningsHoursId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setOpeningsHoursId(c.getInt(0));
		this.setDayStart(c.getInt(1));
		this.setDayEnd(c.getInt(2));
		Time dateStart = null;
		Time dateEnd = null;
		try {
			dateStart = Time.valueOf(c.getString(3));
			if (!c.isNull(3)) {
				dateEnd = Time.valueOf(c.getString(4));
			}
		} catch (Exception e) {
			Log.e("History", "Error Parsing ISO8601", e);
		} finally {
			this.setHourStart(dateStart);
			this.setHourEnd(dateEnd);
		}
		this.setParkingId(c.getInt(5));
		return this;
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
