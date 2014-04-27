package com.charlyparkingapps.map;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public class MapCamera
{
	public static final float ZOOM_INIT = 10;
	public static final float ZOOM_GPS = 12;
	public static final float ZOOM_OBJECT = 14;

	/**
	 * Move camera to a location with a custom zoom
	 * @param location the center position
	 * @param zoom between 2 and 21, see CameraUpdateFactory.newLatLngZoom
	 * zoomLevel - At zoomLevel 1, the equator of the earth is 256 pixels long.
	 * Each successive zoom level is magnified by a factor of 2.
	 */
	public static void moveCamera(GoogleMap map, Location location, float zoom) {
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		moveCamera(map, latLng, zoom);
	}

	/**
	 * Move camera to a location with a custom zoom
	 * @param latLng the center position
	 * @param zoom between 2 and 21, see CameraUpdateFactory.newLatLngZoom
	 * zoomLevel - At zoomLevel 1, the equator of the earth is 256 pixels long.
	 * Each successive zoom level is magnified by a factor of 2.
	 */
	public static void moveCamera(GoogleMap map, LatLng latLng, float zoom) {
		Log.d("GPS", "move camera to " + latLng.latitude + " "
				+ latLng.longitude);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
	}

	/**
	 * Get the radius covered by current view
	 * @param map, the current map
	 * @return MarersParams with a location, a radius and null as request
	 */
	public static MarkersParams radiusDistanceCovered(GoogleMap map) {
		/*
		 * TODO: note: compared only to one side is maybe not the best solution
		 * e.g: if the device is on the horizontal orientation
		 */
		VisibleRegion region = map.getProjection().getVisibleRegion();
		double leftLongitude = region.latLngBounds.northeast.longitude;
		LatLng centerCoord = region.latLngBounds.getCenter();

		Location left = new Location("left");
		left.setLatitude(centerCoord.latitude);
		left.setLongitude(leftLongitude);

		Location center = new Location("center");
		center.setLatitude(centerCoord.latitude);
		center.setLongitude(centerCoord.longitude);

		return new MarkersParams(center, center.distanceTo(left));
	}
}