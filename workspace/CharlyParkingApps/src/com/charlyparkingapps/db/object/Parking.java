package com.charlyparkingapps.db.object;

import android.database.Cursor;

public class Parking {
	
	private int parkingId;
	private String name;
	private boolean defibrillator;
	private int totalPlaces;
	private int freePlaces;
	private int maxHeight;
	
	public static final String[] ALL_COLUMNS = { "parkingId", "name", "defibrillator", "totalPlaces", "freePlaces", "maxHeight" };
	
	public Parking(String nameParam, boolean defibrillatorParam, int totalPlacesParam, int freePlacesParam, int maxHeightParam){
		this.name=nameParam;
		this.defibrillator=defibrillatorParam;
		this.totalPlaces=totalPlacesParam;
		this.freePlaces=freePlacesParam;
		this.maxHeight=maxHeightParam;
	}
	
	public Parking(Cursor c) {
		this.parkingId=c.getInt(0);
		this.name=c.getString(1);
		this.defibrillator=c.getString(2).equals("true");
		this.totalPlaces=c.getInt(3);
		this.freePlaces=c.getInt(4);
		this.maxHeight=c.getInt(5);
	}
	
	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(this.parkingId);
		case 1:
			return this.name;
		case 2:
			return String.valueOf(this.defibrillator);
		case 3:
			return String.valueOf(this.totalPlaces);
		case 4:
			return String.valueOf(this.freePlaces);
		case 5:
			return String.valueOf(this.maxHeight);

		}
		return String.valueOf(this.parkingId);
	}

	public int getParkingId() {
		return parkingId;
	}

	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefibrillator() {
		return defibrillator;
	}

	public void setDefibrillator(boolean defibrillator) {
		this.defibrillator = defibrillator;
	}

	public int getTotalPlaces() {
		return totalPlaces;
	}

	public void setTotalPlaces(int totalPlaces) {
		this.totalPlaces = totalPlaces;
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public static String[] getAllColumns() {
		return ALL_COLUMNS;
	}

}
