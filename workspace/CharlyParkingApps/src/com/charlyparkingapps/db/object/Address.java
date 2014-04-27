package com.charlyparkingapps.db.object;

import android.content.Context;
import android.database.Cursor;

import com.charlyparkingapps.db.ParkingDB;

public class Address implements Model {

	public static final String[] ALL_COLUMNS = { "addressId", "parking",
			"street", "number", "city", "zip", "country" };

	private int AddressID;
	private int parkingID;
	private String street;
	private int number;
	private String city;
	private int zip;
	private String country;

	private Parking parking;

	private Context context;

	public Address(Context contextParam, int parkingIDParam,
			String streetParam, int numberParam, String cityParam,
			int zipParam, String countryParam) {
		this.context = contextParam;
		this.setParkingID(parkingIDParam);
		this.setStreet(streetParam);
		this.setNumber(numberParam);
		this.setCity(cityParam);
		this.setZip(zipParam);
		this.setCountry(countryParam);
	}

	public Address(Cursor c, Context context) {
		createFromCursor(c, context);
	}

	public Address() {

	}

	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getAddressID());
		case 1:
			return String.valueOf(this.getParkingID());
		case 2:
			return this.getStreet();
		case 3:
			return String.valueOf(this.getNumber());
		case 4:
			return this.getCity();
		case 5:
			return String.valueOf(this.getZip());
		case 6:
			return this.getCountry();

		}
		return String.valueOf(this.getParkingID());
	}

	public void loadParking() {
		ParkingDB p = new ParkingDB(context);
		p.open();
		this.setParking((Parking) p.getById(this.getParkingID()));
		this.parking.setAddress(this);
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getParkingID() {
		return parkingID;
	}

	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}

	@Override
	public String[] getAllColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public String getUniqueColumn() {
		return ALL_COLUMNS[0];
	}

	@Override
	public Model createFromCursor(Cursor c, Context context) {
		this.context = context;
		this.setAddressID(c.getInt(0));
		this.setParkingID(c.getInt(1));
		this.setStreet(c.getString(2));
		this.setNumber(c.getInt(3));
		this.setCity(c.getString(4));
		this.setZip(c.getInt(5));
		this.setCountry(c.getString(6));
		return this;
	}

	public int getAddressID() {
		return AddressID;
	}

	public void setAddressID(int addressID) {
		AddressID = addressID;
	}

}
