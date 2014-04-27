package com.charlyparkingapps.db.object;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.charlyparkingapps.db.AddressDB;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Parking implements Model {

	private int parkingId;
	private String name;
	private boolean defibrillator;
	private int totalPlaces;
	private int freePlaces;
	private int maxHeight;

	private Address address;

	public static final String[] ALL_COLUMNS = { "parkingId", "name",
			"defibrillator", "totalPlaces", "freePlaces", "maxHeight" };

	private Marker marker = null;
	private LatLng location;

	private Context context;

	public Parking(Context context, String nameParam,
			boolean defibrillatorParam, int totalPlacesParam,
			int freePlacesParam, int maxHeightParam) {
		this.context = context;
		this.name = nameParam;
		this.defibrillator = defibrillatorParam;
		this.totalPlaces = totalPlacesParam;
		this.freePlaces = freePlacesParam;
		this.maxHeight = maxHeightParam;
	}

	public Parking(Context context, Cursor c) {
		this.context = context;
		this.parkingId = c.getInt(0);
		this.name = c.getString(1);
		this.defibrillator = c.getString(2).equals("true");
		this.totalPlaces = c.getInt(3);
		this.freePlaces = c.getInt(4);
		this.maxHeight = c.getInt(5);
	}

	public Parking() {
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
		this.address = address;
	}

	public void loadAddress() {
		AddressDB ad = new AddressDB(context);
		ad.Open();
		address = ad.GetByIdParking(this.getParkingId());
	}

	public Address getAddress() {
		if (address == null) {
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

	// ___________________ MARKERS

	/**
	 * @return the location of the parking (one entry or the center of it)
	 */
	public LatLng getLocation() {
		if (location == null)
			// TODO
			location = new LatLng(0, 0);
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
		return null;
	}

	/**
	 * @return a MarkerOptions needed for creating a marker
	 */
	private MarkerOptions getMarkerOptions() {
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.title(getName());
		markerOptions.position(getLocation());
		// TODO: add description
		// markerOptions.snippet (TEXT);
		return markerOptions;
	}

	/**
	 * Add a marker with the info of this item if it doesn't exist yet
	 * 
	 * @param map
	 *            where the marker will be added
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
