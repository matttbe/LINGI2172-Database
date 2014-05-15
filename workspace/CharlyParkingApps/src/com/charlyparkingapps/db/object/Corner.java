package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class Corner implements Model {


	private int cornerId;
	private int parking_id;
	private double latitude;
	private double longitude;

	public Corner(int parking_idParam, double latitudeParam,
			double longitudeParam) {
		this.setParking_id(parking_idParam);
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
			return String.valueOf(this.getParking_id());
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
		this.setParking_id(c.getInt(1));
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

	public int getParking_id() {
		return parking_id;
	}

	public void setParking_id(int parking_id) {
		this.parking_id = parking_id;
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

}
