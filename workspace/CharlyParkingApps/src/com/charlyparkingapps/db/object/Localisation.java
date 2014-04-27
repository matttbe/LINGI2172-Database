package com.charlyparkingapps.db.object;

import com.charlyparkingapps.db.ParkingDB;

import android.content.Context;
import android.database.Cursor;

public class Localisation implements Model {
	
	public static final String[] ALL_COLUMNS = {"localisationId","parking", "latitude", "longitude"};
	
	private int localisationID;
	private int parkingID;
	private Double latitude;
	private Double longitude;
	
	private Parking parking;
	private boolean isLoaded=false;
	
	private Context context;
	
	
	public Localisation(Context contextParam, int parkingIDParam, Double latitudeParam, Double longitudeParam){
		this.context=contextParam;
		this.setParkingID(parkingIDParam);
		this.setLatitude(latitudeParam);
		this.setLongitude(longitudeParam);
	}
	
	public Localisation(Cursor c, Context context) {
		createFromCursor(c, context);
	}
	
	public Localisation(){
		
	}

	@Override
	public String[] getAll_Columns() {
		return ALL_COLUMNS;
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
	public String getUniqueColumn() {
		return ALL_COLUMNS[0];
	}

	@Override
	public Model createFromCursor(Cursor c, Context context) {
		this.context=context;
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

	
	public void loadParking(){
		ParkingDB p = new ParkingDB(context);
		p.Open();
		this.setParking((Parking)p.GetById(this.getParkingID()));
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

}
