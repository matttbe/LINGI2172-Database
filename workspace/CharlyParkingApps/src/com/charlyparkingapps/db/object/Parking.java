package com.charlyparkingapps.db.object;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;

import com.charlyparkingapps.db.AddressDB;
import com.charlyparkingapps.db.UserDB;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Parking implements Model, Serializable {

	private static final long serialVersionUID = -5541291087437633614L;

	private int parkingId;
	private String name;
	private boolean defibrillator;
	private int totalPlaces;
	private int freePlaces;
	private int maxHeight;
	private boolean disable;
	private int ownerId;

	private Address address;
	private User owner;

	private Marker marker = null;
	private LatLng location;

	public Parking(String nameParam, boolean defibrillatorParam,
			int totalPlacesParam, int freePlacesParam, int maxHeightParam,
			boolean disable, int ownerId)
	{
		this.name = nameParam;
		this.defibrillator = defibrillatorParam;
		this.totalPlaces = totalPlacesParam;
		this.freePlaces = freePlacesParam;
		this.maxHeight = maxHeightParam;
		this.disable = disable;
		this.ownerId = ownerId;
	}

	public Parking(Cursor c) {
		this.createFromCursor(c);
	}

	public String getByInt(int i) {
		switch (i) {
			case 0:
				return String.valueOf (this.parkingId);
			case 1:
				return this.name;
			case 2:
				return String.valueOf (this.defibrillator);
			case 3:
				return String.valueOf (this.totalPlaces);
			case 4:
				return String.valueOf (this.freePlaces);
			case 5:
				return String.valueOf (this.maxHeight);
			case 6:
				return String.valueOf (this.disable);
			case 7:
				return String.valueOf (this.ownerId);
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

	public boolean getHasPlacesForDisabledPeople() {
		return disable;
	}

	public void setHasPlacesForDisabledPeople(boolean disable) {
		this.disable = disable;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getOwner ()
	{
		if (this.owner == null)
		{
			loadUser ();
		}
		return this.owner;
	}

	public void setOwner (User o)
	{
		owner = o;
	}

	public int getOwnerId ()
	{
		return this.ownerId;
	}

	public void setOwnerId (int id)
	{
		this.ownerId = id;
	}

	public void loadAddress() {
		AddressDB ad = AddressDB.getInstance();
		ad.open(false);
		address = ad.getByIdParking(this.getParkingId());
		ad.close();
	}
	
	public void loadUser ()
	{
		UserDB udb = UserDB.getInstance ();
		udb.open (false);
		owner = (User) udb.getById (ownerId);
		udb.close ();
	}

	public Address getAddress() {
		if (address == null) {
			loadAddress();
		}
		return address;
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.parkingId = c.getInt(0);
		this.name = c.getString(1);
		this.defibrillator = c.getString(2).equals("true");
		this.totalPlaces = c.getInt(3);
		this.freePlaces = c.getInt(4);
		this.maxHeight = c.getInt(5);
		this.disable = c.getString(6).equals("true");
		this.ownerId = c.getInt (7);
		return this;
	}


	// ___________________ MARKERS

	/**
	 * @return the location of the parking (the center of it) or null
	 */
	public LatLng getLocation() {
		if (location == null) {
			this.getAddress();
			if (this.address != null)
				location = new LatLng(this.address.getLatitude(),
						this.address.getLongitude());
			else {
				location = new LatLng(50.6676 + ((double)freePlaces)/1000, 4.6215 + ((double)freePlaces)/1000); // TODO remove example
			}
		}
		return location;
	}

	/**
	 * @return a list of all locations (all entries) or null
	 */
	public List<LatLng> getAllEntries() {
		// TODO => new ArrayList<LatLng>();
		return null;
	}

	/**
	 * @return a list of all corners of the parking or null
	 */
	public List<LatLng> getAllCorners() {
		// TODO?? => ORDER BY id
		// TODO: remove example
		LinkedList<LatLng> corners = new LinkedList<LatLng>();
		corners.add(new LatLng(50.667905, 4.621993));
		corners.add(new LatLng(50.667876, 4.621063));
		corners.add(new LatLng(50.667556, 4.621084));
		corners.add(new LatLng(50.667299, 4.621462));
		corners.add(new LatLng(50.667408, 4.622020));
		return corners;
	}

	/**
	 * @return a MarkerOptions needed for creating a marker
	 */
	private MarkerOptions getMarkerOptions() {
		MarkerOptions markerOptions = new MarkerOptions();
		// we add the id on the title, a bit ugly but not for a DB view point
		markerOptions.title(getParkingId() + " - " + getName());
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
