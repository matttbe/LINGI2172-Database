package com.charlyparkingapps.db.object;

import android.database.Cursor;

import com.charlyparkingapps.db.ParkingDB;
import com.google.android.gms.maps.model.LatLng;

public class Corner implements Model {


	private int cornerId;
	private int parkingId;
	private double latitude;
	private double longitude;

	private Parking parking;
	private LatLng location;

	public Corner(int parkingIdParam, double latitudeParam,
			double longitudeParam) {
		this.setParkingID(parkingIdParam);
		this.setLatitude(latitudeParam);
		this.setLongitude(longitudeParam);
	}

	public Corner(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getCornerId());
		case 1:
			return String.valueOf(this.getParkingID());
		case 2:
			return String.valueOf(this.getLatitude());
		case 3:
			return String.valueOf(this.getLongitude());
		default:
			return String.valueOf(this.getCornerId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setCornerId(c.getInt(0));
		this.setParkingID(c.getInt(1));
		this.setLatitude(c.getDouble(2));
		this.setLongitude(c.getDouble(3));
		return this;
	}

	public int getCornerId() {
		return cornerId;
	}

	public void setCornerId(int cornerId) {
		this.cornerId = cornerId;
	}

	public int getParkingID() {
		return parkingId;
	}

	public void setParkingID(int parkingId) {
		this.parkingId = parkingId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void loadParking() {
		ParkingDB p = ParkingDB.getInstance();
		p.open(false);
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

	/**
	 * @return the location of the parking (the center of it) or null
	 */
	public LatLng getLocation() {
		if (location == null) {
			location = new LatLng(getLatitude(), getLongitude());
		}
		return location;
	}
}
