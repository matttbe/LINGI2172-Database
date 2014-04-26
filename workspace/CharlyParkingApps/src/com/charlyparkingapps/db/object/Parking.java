package com.charlyparkingapps.db.object;

import com.charlyparkingapps.db.AddressDB;

import android.content.Context;
import android.database.Cursor;

public class Parking implements Model{
	
	private int parkingId;
	private String name;
	private boolean defibrillator;
	private int totalPlaces;
	private int freePlaces;
	private int maxHeight;
	
	private Address address;
	private boolean isLoaded=false;
	
	private Context context;
	
	public static final String[] ALL_COLUMNS = { "parkingId", "name", "defibrillator", "totalPlaces", "freePlaces", "maxHeight" };
	
	public Parking(Context contextParam, String nameParam, boolean defibrillatorParam, int totalPlacesParam, int freePlacesParam, int maxHeightParam){
		this.context=contextParam;
		this.name=nameParam;
		this.defibrillator=defibrillatorParam;
		this.totalPlaces=totalPlacesParam;
		this.freePlaces=freePlacesParam;
		this.maxHeight=maxHeightParam;
	}
	
	public Parking(Cursor c, Context context) {
		this.context=context;
		this.parkingId=c.getInt(0);
		this.name=c.getString(1);
		this.defibrillator=c.getString(2).equals("true");
		this.totalPlaces=c.getInt(3);
		this.freePlaces=c.getInt(4);
		this.maxHeight=c.getInt(5);
	}
	
	public Parking(){
		
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

	public void setAddress(Address address) {
		this.address=address;
	}
	
	public void loadAddress(){
		isLoaded=true;
		AddressDB ad=new AddressDB(context);
		ad.Open();
		address=ad.GetByIdParking(this.getParkingId());
		
	}
	
	public Address getAddress() {
		if(!isLoaded){
			loadAddress();
		}
		return address;
	}

	@Override
	public String[] getAll_Columns() {
		return ALL_COLUMNS;
	}

	@Override
	public Model createFromCursor(Cursor c, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueColumn() {
		return ALL_COLUMNS[0];
	}

}
