package com.charlyparkingapps.db.object;

import android.content.Context;
import android.database.Cursor;

import com.charlyparkingapps.db.ParkingDB;

public class Address {
	
	public static final String[] ALL_COLUMNS = {"parking", "street", "number", "city", "zip", "country" };
	
	private int parkingID;
	private String street;
	private int number;
	private String city;
	private int zip;
	private String country;
	
	private Parking parking;
	private boolean isLoaded=false;
	
	private Context context;
	
	public Address(Context contextParam, int parkingIDParam, String streetParam, int numberParam, String cityParam, int zipParam, String countryParam){
		this.context=contextParam;
		this.setParkingID(parkingIDParam);
		this.setStreet(streetParam);
		this.setNumber(numberParam);
		this.setCity(cityParam);
		this.setZip(zipParam);
		this.setCountry(countryParam);		
	}
	
	public Address(Cursor c, Context context) {
		this.context=context;
		this.setParkingID(c.getInt(0));
		this.setStreet(c.getString(1));
		this.setNumber(c.getInt(2));
		this.setCity(c.getString(3));
		this.setZip(c.getInt(4));
		this.setCountry(c.getString(5));	
	}

	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.getParkingID());
		case 1:
			return this.getStreet();
		case 2:
			return String.valueOf(this.getNumber());
		case 3:
			return this.getCity();
		case 4:
			return String.valueOf(this.getZip());
		case 5:
			return this.getCountry();

		}
		return String.valueOf(this.getParkingID());
	}
	
	
	public void loadParking(){
		ParkingDB p = new ParkingDB(context);
		p.Open();
		this.setParking((Parking)p.GetById(this.getParkingID()));
		this.parking.setAddress(this);
		p.Close();
		isLoaded=true;
	}
	public Parking getParking() {
		if(!isLoaded){
			loadParking();
		}
		return this.parking;
			
	}
	public void setParking(Parking parking) {
		isLoaded=true;
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

}
