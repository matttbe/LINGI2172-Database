package com.charlyparkingapps.db.object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.charlyparkingapps.db.ParkingDB;

public class HourlyRate implements Model {

	// CREATE TABLE "HourlyRate" ("start" TIME PRIMARY KEY NOT NULL DEFAULT
	// (null) ,"end" TIME NOT NULL DEFAULT (null) ,"cost" FLOAT NOT NULL DEFAULT
	// (null) ,"parking" INTEGER NOT NULL )

	private int hourlyRateId;
	private Date start;
	private Date end;
	private float cost;
	private int parkingId;

	private Parking parking;

	@SuppressLint("SimpleDateFormat")
	DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public HourlyRate(Date startParam, Date endParam, float costParam,
			int parkingIdParam) {
		this.setStart(startParam);
		this.setEnd(endParam);
		this.setCost(costParam);
		this.setParkingId(parkingIdParam);
	}

	public HourlyRate(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getHourlyRateId());
		case 1:
			return this.getStart().toString();
		case 2:
			return this.getEnd().toString();
		case 3:
			return String.valueOf(this.getCost());
		case 4:
			return String.valueOf(this.getParkingId());
		default:
			return String.valueOf(this.getHourlyRateId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setHourlyRateId(c.getInt(0));
		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = iso8601Format.parse(c.getString(2));
			if (!c.isNull(3)) {
				dateEnd = iso8601Format.parse(c.getString(3));
			}
		} catch (ParseException e) {
			Log.e("History", "Error Parsing ISO8601", e);
		} finally {
			this.setStart(dateStart);
			this.setEnd(dateEnd);
		}
		this.setCost(c.getFloat(3));
		this.setParkingId(c.getInt(4));
		return this;
	}

	public int getHourlyRateId() {
		return hourlyRateId;
	}

	public void setHourlyRateId(int hourlyRateId) {
		this.hourlyRateId = hourlyRateId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
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
		this.setParkingId(parking.getParkingId());
	}

}
