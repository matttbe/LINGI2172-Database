package com.charlyparkingapps.db.object;

import android.database.Cursor;

import com.charlyparkingapps.db.ParkingDB;

public class Localisation implements Model {


	private int localisationID;
	private int parkingID;
	private Double latitude;
	private Double longitude;

	private Parking parking;

	public Localisation(int parkingIDParam, Double latitudeParam,
			Double longitudeParam) {
		this.setParkingID(parkingIDParam);
		this.setLatitude(latitudeParam);
		this.setLongitude(longitudeParam);
	}

	public Localisation(Cursor c) {
		createFromCursor(c);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getLocalisationID());
		case 1:
			return String.valueOf(this.getParkingID());
		case 2:
			return String.valueOf(this.getLatitude());
		case 3:
			return String.valueOf(this.getLongitude());
		}
		return String.valueOf(this.getLocalisationID());
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setParkingID(c.getInt(0));
		this.setLatitude(c.getDouble(1));
		this.setLongitude(c.getDouble(2));
		return this;
	}

	public int getLocalisationID() {
		return localisationID;
	}

	public void setLocalisationID(int localisationID) {
		this.localisationID = localisationID;
	}

	public int getParkingID() {
		return parkingID;
	}

	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void loadParking() {
		ParkingDB p = ParkingDB.getInstance();
		p.open();
		this.setParking((Parking) p.getById(this.getParkingID()));
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
