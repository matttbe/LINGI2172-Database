package com.charlyparkingapps.db.object;

import java.io.Serializable;

import android.database.Cursor;

import com.charlyparkingapps.db.FuelDB;
import com.charlyparkingapps.db.UserDB;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Car implements Model, Serializable {

	private static final long serialVersionUID = 3780068181871904047L;

	private int carId;
	private String name;
	private int height = 0;
	private int fuelId = 0;
	private int userId;
	private Double latitude;
	private Double longitude;

	private User user;
	private Fuel fuel;

	private Marker marker = null;
	private LatLng location;

	public Car(int heightParam, String nameParam, int fuelIdParam,
			int userIdParam,
			Double latitudeParam, Double longitudeParam) {
		this.setName(nameParam);
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
			return this.getName();
		case 2:
			return String.valueOf(this.getHeight());
		case 3:
			return String.valueOf(this.getFuelId());
		case 4:
			return String.valueOf(this.getUserId());
		case 5:
			return String.valueOf(this.getLatitude());
		case 6:
			return String.valueOf(this.getLongitude());
		default:
			return String.valueOf(this.getCarId());
		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setCarId(c.getInt(0));
		this.setName(c.getString(1));
		this.setHeight(c.getInt(2));
		this.setFuelId(c.getInt(3));
		this.setUserId(c.getInt(4));
		this.setLatitude(c.getDouble(5));
		this.setLongitude(c.getDouble(6));
		return this;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
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

	/**
	 * @return the location of the parking (the center of it) or null
	 */
	public LatLng getLocation() {
		if (location == null) {
			location = new LatLng(getLatitude(), getLongitude());
		}
		return location;
	}

	/**
	 * @return a MarkerOptions needed for creating a marker
	 */
	private MarkerOptions getMarkerOptions() {
		MarkerOptions markerOptions = new MarkerOptions();
		// we add the id on the title, a bit ugly but not for a DB view point
		markerOptions.title(getCarId() + " - " + getName());
		markerOptions.position(getLocation());
		// TODO: add description
		// markerOptions.snippet (TEXT);
		return markerOptions;
	}

	/**
	 * Add a marker with the info of this item if it doesn't exist yet
	 *
	 * @param map where the marker will be added
	 * @return the marker added to the map
	 */
	public void addMarkerToMap(GoogleMap map) {
		if (marker == null)
			marker = map.addMarker(getMarkerOptions());
	}

	/**
	 * @return the marker linked to this item
	 */
	public Marker getMarker() {
		return marker;
	}

	/**
	 * Remove the marker (if available) from the map and set it to null;
	 */
	public void removeMarker() {
		if (marker != null) {
			marker.remove();
			marker = null;
		}
	}
}
