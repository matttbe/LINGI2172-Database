package com.charlyparkingapps.activities;

import java.io.Serializable;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.Parking;
import com.charlyparkingapps.listeners.DrawerListListener;
import com.charlyparkingapps.listeners.SearchListener;
import com.charlyparkingapps.map.MapCamera;
import com.charlyparkingapps.map.MapMarkers;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends Activity implements LocationListener {

	// Menu
	private ListView mMenuList;
	private DrawerLayout mDrawerLayout;
	private String[] mEntries;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;

	// Maps
	private LocationManager locationManager;
	private GoogleMap map;
	private MapMarkers markers;

	private boolean isUsingGPS = false;
	private boolean isUsingNetwork = false;
	private boolean isFirstPosition = true;
	private boolean canMoveCamera = true;
	private MapDisplay mapDisplay = MapDisplay.DEFAULT;

	private enum MapDisplay {
		DEFAULT, PARKING, PARKINGS_LIST, CAR, CARS_LIST;
	}

	public static final String KEY_PARKING = "parking";
	public static final String KEY_CAR = "car";
	public static final String KEY_PARKINGS_LIST = "parkingList";
	public static final String KEY_CARS_LIST = "carList";

	private static final int GPS_MIN_TIME_UPDATE = 5 * 60 * 1000; // 5min
	private static final int NETWORK_MIN_TIME_UPDATE = 10 * 60 * 1000; // 10min
	private static final int MIN_DISTANCE_UPDATE = 100; // 100 meters

	// ________________ LISTERNER ACTIVITY

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initMenu();
		initMap();
		displayInitItem();
	}

	private void initMenu() {
		mEntries = getResources().getStringArray(R.array.drawer_array);
		mMenuList = (ListView) findViewById(R.id.drawer_menu_list);

		// Set the adapter for the list view
		mMenuList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mEntries));
		this.mMenuList.setOnItemClickListener(new DrawerListListener(this));

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer_dark, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(R.string.app_name);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.menu);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	private void initMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setIndoorEnabled(true);
		map.setBuildingsEnabled(true);
		map.setMyLocationEnabled(true);
		map.setOnCameraChangeListener(onCameraChangeListener);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setMyLocationButtonEnabled(true);

		markers = new MapMarkers (map);

		OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent intent;
				// this is very ugly but good for a DB view point :-)
				String markerTitle = marker.getTitle();
				markerTitle = markerTitle.substring(0, markerTitle.indexOf(' '));
				int keyID = Integer.parseInt(markerTitle);

				switch (mapDisplay) {
				case DEFAULT:
				case PARKING:
				case PARKINGS_LIST:
				default:
					intent = new Intent(MainActivity.this,
							ParkingActivity.class);
					intent.putExtra(ParkingActivity.KEY_PARKING, keyID);
					startActivity(intent);
					break;

				case CAR:
				case CARS_LIST:
					intent = new Intent(MainActivity.this,
							CarEditActivity.class);
					intent.putExtra(CarEditActivity.KEY_CAR_ID, keyID);
					startActivity(intent);
					break;
				}
			}
		};
		map.setOnInfoWindowClickListener(onInfoWindowClickListener);
	}

	private void displayInitItem() {
		/*// How to use it:
		Parking parking = new Parking("Sainte Barb", true, 354, 241, 150, true);
		indent.putExtra(MainActivity.KEY_PARKING, parking);
		*/
	
		Bundle extras = getIntent().getExtras();
		if (extras == null)
			return;

		canMoveCamera = false;
		Serializable serial;
		if ((serial = extras.getSerializable(KEY_PARKING)) != null) {
			Parking parking = (Parking) serial;
			markers.showParking(parking, true);
			mapDisplay = MapDisplay.PARKING;
		}
		else if ((serial = extras.getSerializable(KEY_PARKINGS_LIST)) != null) {
			@SuppressWarnings("unchecked")
			List<Parking> parkings = (List<Parking>) serial;
			markers.showParking(parkings);
			mapDisplay = MapDisplay.PARKINGS_LIST;
		}
		else if ((serial = extras.getSerializable(KEY_CAR)) != null) {
			Car car = (Car) extras.getSerializable(KEY_CAR);
			markers.showCar(car, true);
			mapDisplay = MapDisplay.CAR;
		}
		else if ((serial = extras.getSerializable(KEY_CARS_LIST)) != null) {
			@SuppressWarnings("unchecked")
			List<Car> cars = (List<Car>) extras.getSerializable(KEY_CARS_LIST);
			markers.showCar(cars);
			mapDisplay = MapDisplay.CARS_LIST;
			mapDisplay = MapDisplay.CARS_LIST;
		}
		else
			canMoveCamera = true;
	}

	@Override
	public void onResume() {
		super.onResume();

		// Obtention de la référence du service
		locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);

		// Si le GPS est disponible, on s'y abonne
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			registerGPS();
		else if (locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			registerNetwork();
		else
			Toast.makeText(this, "GPS not available", Toast.LENGTH_LONG).show();

		// center around the last know location
		Location lastKnowLocation = null;
		lastKnowLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lastKnowLocation == null) {
			lastKnowLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			// TODO: if NULL => getLastLocation from settings
		}

		if (lastKnowLocation != null) {
			if (canMoveCamera)
				MapCamera.moveCamera (map, lastKnowLocation,
						MapCamera.ZOOM_INIT);
			markers.updateMarkers ();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterLocation();
	}

	// ________________ LISTERNER FOR THE MENU

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// menu.a
		
		//For the search
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setOnQueryTextListener(new SearchListener(this));
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_logout:
			((CharlyApplication) getApplication()).logOut();
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// _________________ GPS

	private void registerGPS() {
		Log.d("GPS", "GPS registered");
		isUsingGPS = true;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				GPS_MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);
	}

	private void registerNetwork() {
		Log.d("GPS", "Network registered");
		isUsingNetwork = true;
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, NETWORK_MIN_TIME_UPDATE,
				MIN_DISTANCE_UPDATE, this);
	}

	private void unregisterLocation() {
		Log.d("GPS", "unregistered");
		isUsingGPS = isUsingNetwork = false;
		isFirstPosition = true; // next update will reposition the camera
		locationManager.removeUpdates(this);
	}

	// ________ LISTENER MAP

	private OnCameraChangeListener onCameraChangeListener = new OnCameraChangeListener() {

		@Override
		public void onCameraChange(CameraPosition position) {
			Log.d("GPS", "onCameraChange: " + position.target + " ; zoom: "
					+ position.zoom + " ; dist: "
					+ MapCamera.radiusDistanceCovered(map).getRadius()
					+ " meters");
			markers.updateMarkers();
		}
	};

	@Override
	public void onLocationChanged(final Location location) {
		if (canMoveCamera && isFirstPosition) {
			MapCamera.moveCamera(map, location, MapCamera.ZOOM_GPS);
			isFirstPosition = false;
			markers.updateMarkers();
		}
	}

	@Override
	public void onProviderDisabled(final String provider) {
		Log.d("GPS", "provider disabled " + provider);
		// GPS no longer available
		if ((isUsingGPS && provider.equals(LocationManager.GPS_PROVIDER))
				|| (isUsingNetwork && provider
						.equals(LocationManager.NETWORK_PROVIDER)))
			unregisterLocation();
	}

	@Override
	public void onProviderEnabled(final String provider) {
		Log.d("GPS", "provider enabled " + provider);
		// yeah, GPS is back
		if (isUsingGPS && provider.equals(LocationManager.GPS_PROVIDER))
			registerGPS();
		else if (isUsingNetwork
				&& provider.equals(LocationManager.NETWORK_PROVIDER))
			registerNetwork();
	}

	@Override
	public void onStatusChanged(final String provider, final int status,
			final Bundle extras) {
		Log.d("GPS", "status changed " + provider + ": " + status);
	}
	
	
	// Return from search query
	public void centerMap(LatLng loc) {
		MapCamera.moveCamera(map, loc, MapCamera.ZOOM_GPS);
		markers.updateMarkers();
	}
}
