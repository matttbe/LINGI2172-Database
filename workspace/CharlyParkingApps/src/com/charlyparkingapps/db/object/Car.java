package com.charlyparkingapps.db.object;

import android.database.Cursor;

import com.charlyparkingapps.db.FuelDB;
import com.charlyparkingapps.db.UserDB;

public class Car implements Model {

	private int carId;
	private int height = 0;
	private int fuelId = 0;
	private int userId;
	private Double latitude;
	private Double longitude;

	private User user;
	private Fuel fuel;

	public Car(int heightParam, int fuelIdParam, int userIdParam,
			Double latitudeParam, Double longitudeParam) {
		this.setHeight(heightParam);
		this.setFuelId(fuelIdParam);
		this.setUserId(userIdParam);
		this.setLatitude(latitudeParam);
		this.setLongitude(longitudeParam);
	}

	public Car(Cursor c) {
		createFromCursor(c);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getCarId());
		case 1:
			return String.valueOf(this.getHeight());
		case 2:
			return String.valueOf(this.getFuelId());
		case 3:
			return String.valueOf(this.getUserId());
		case 4:
			return String.valueOf(this.getLatitude());
		case 5:
			return String.valueOf(this.getLongitude());
		default:
			return String.valueOf(this.getCarId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setCarId(c.getInt(0));
		this.setHeight(c.getInt(1));
		this.setFuelId(c.getInt(2));
		this.setUserId(c.getInt(3));
		this.setLatitude(c.getDouble(4));
		this.setLongitude(c.getDouble(5));
		return this;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFuelId() {
		return fuelId;
	}

	private void setFuelId(int fuel) {
		this.fuelId = fuel;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public void loadUser() {
		UserDB p = UserDB.getInstance();
		p.open(false);
		this.user = ((User) p.getById(this.getUserId()));
		p.close();
	}

	public User getUser() {
		if (this.user == null) {
			loadUser();
		}
		return this.user;
	}

	public void loadFuel() {
		FuelDB p = FuelDB.getInstance();
		p.open(false);
		this.fuel = ((Fuel) p.getById(this.getFuelId()));
		p.close();
	}

	public Fuel getFuel() {
		if (this.fuel == null) {
			loadUser();
		}
		return this.fuel;
	}

}
