package com.charlyparkingapps.map;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.util.SparseArray;

import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.Parking;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapMarkers {
	private GoogleMap map;
	private SparseArray<Parking> parkingsArray;

	private static final int MAXSIZE = 50;

	public MapMarkers(GoogleMap map) {
		this.map = map;
		this.parkingsArray = new SparseArray<Parking>();
	}

	/**
	 * Remove all markers, etc. from the Map and this object
	 */
	public void clearMap() {
		for (int i = 0; i < parkingsArray.size(); i++)
			parkingsArray.valueAt(i).removeMarker();
		parkingsArray.clear();
		map.clear();
	}

	private ArrayList<Parking> getMarkersForLocation(Location location,
			float radius) {
		Log.d("Markers", "== getMarkers: " + location + " " + radius);
		// TODO: DB => ParkingDB.getParkings(location.getLatitude(),
		// location.getLongitude(), radius)
		ArrayList<Parking> markers = new ArrayList<Parking>(); // TODO: rm
		return markers;
	}

	private ArrayList<Parking> getMarkersForLocation() {
		MarkersParams locationInfo = MapCamera.radiusDistanceCovered(map);
		return getMarkersForLocation(locationInfo.getLocation(),
				locationInfo.getRadius());
	}

	/**
	 * Update the list of markers in the list
	 */
	public void updateMarkers() {
		ArrayList<Parking> items = getMarkersForLocation();
		for (Parking item : items) {
			int id = item.getParkingId();
			Parking inHashItem = parkingsArray.get(id);
			parkingsArray.delete(id);
			// if the item already exists, update the position
			if (inHashItem != null) {
				Log.d("Markers", "An old item");
				parkingsArray.put(id, inHashItem); // put it at the end
			} else {
				Log.d("Markers", "A new item");
				item.addMarkerToMap(map);
				parkingsArray.put(id, item);
			}
		}

		// remove extras
		removeOldMarkers();
	}

	private void removeOldMarkers() {
		// Remove extras
		int nbToRm = parkingsArray.size() - MAXSIZE;
		if (nbToRm > 0) {
			int keysToRm[] = new int[nbToRm];
			for (int i = 0; i < parkingsArray.size(); i++) {
				nbToRm--;
				keysToRm[nbToRm] = parkingsArray.keyAt(i);
				parkingsArray.get(keysToRm[nbToRm]).removeMarker();
				if (nbToRm == 0)
					break;
			}
			for (int id : keysToRm)
				parkingsArray.delete(id);
		}
	}

	/**
	 * Show the parking on the map with all entries and corners
	 *
	 * @param parking parking to show
	 * @param onlyOne clear map and move the camera on top of the parking
	 */
	public void showParking(Parking parking, boolean onlyOne) {
		if (onlyOne)
			clearMap();

		parking.addMarkerToMap(map);
		if (onlyOne)
			parking.getMarker().showInfoWindow();

		if (onlyOne) {
			LatLng center = parking.getLocation();
			MapCamera.moveCamera(map, center, MapCamera.ZOOM_OBJECT);
		}

		List<LatLng> points = parking.getAllCorners();
		if (points != null) {
			PolygonOptions polygon = new PolygonOptions();
			polygon.addAll(points);
			polygon.fillColor(Color.argb(50, 25, 25, 150));
			polygon.strokeColor(Color.LTGRAY);
			map.addPolygon(polygon);
		}

		points = parking.getAllEntries();
		if (points != null) {
			for (LatLng position : points) {
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(position);
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				map.addMarker(markerOptions);
			}
		}
	}

	/**
	 * Show a list of parkings on the map with all entries and corners
	 *
	 * @param parking
	 */
	public void showParking(List<Parking> parkings) {
		int size = parkings.size();
		if (size == 0)
			return;
		if (size == 1) {
			showParking(parkings.get(0), true);
			return;
		}

		clearMap();
		LatLngBounds.Builder positions = new LatLngBounds.Builder();
		for (Parking parking : parkings) {
			showParking(parking, false);
			positions.include(parking.getLocation());
		}
		MapCamera.moveCamera(map, positions.build());
	}

	/**
	 * Show one car on the map
	 * @param car to show
	 * @param onlyOne clear map and move the camera on top of the car
	 */
	public void showCar(Car car, boolean onlyOne) {
		if (onlyOne)
			clearMap();

		car.addMarkerToMap(map);
		if (onlyOne)
			car.getMarker().showInfoWindow();

		if (onlyOne) {
			LatLng center = car.getLocation();
			MapCamera.moveCamera(map, center, MapCamera.ZOOM_OBJECT);
		}
	}

	/**
	 * Show a list of cars on the map
	 *
	 * @param cars to show
	 */
	public void showCar(List<Car> cars) {
		int size = cars.size();
		if (size == 0)
			return;
		if (size == 1) {
			showCar(cars.get(0), true);
			return;
		}

		clearMap();
		LatLngBounds.Builder positions = new LatLngBounds.Builder();
		for (Car car : cars) {
			showCar(car, false);
			positions.include(car.getLocation());
		}
		MapCamera.moveCamera(map, positions.build());
	}
}
