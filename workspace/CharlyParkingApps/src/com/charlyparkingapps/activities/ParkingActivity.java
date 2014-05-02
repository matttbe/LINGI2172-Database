package com.charlyparkingapps.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.charlyparkingapps.R;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.object.Parking;

public class ParkingActivity extends Activity {

	public static final String KEY_PARKING = "parkingID";
	private  Parking parking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking);
		// Show the Up button in the action bar.
		setupActionBar();

		int id = getIntent().getIntExtra(KEY_PARKING, 0);
		if (id > 0)
			loadParking(id);
	}

	private void loadParking(int id) {
		System.out.println("Should display parking id: " + id); // TODO
		ParkingDB pdb = new ParkingDB (this.getBaseContext ());
		pdb.open ();
		parking = (Parking) pdb.getById (id);
		parking.loadAddress ();
		pdb.close ();
		TextView tv1 = (TextView) findViewById(R.id.parking_name);
		tv1.setText (parking.getName ());
		TextView tv2 = (TextView) findViewById(R.id.parking_street_number);
		tv2.setText (parking.getAddress ().getStreet () + " "+ parking.getAddress ().getNumber ());

		TextView tv3 = (TextView) findViewById(R.id.parking_zip_city);
		tv3.setText (parking.getAddress ().getZip () + " "+ parking.getAddress ().getCity ());
		TextView tv4 = (TextView) findViewById(R.id.parking_country);
		tv4.setText (parking.getAddress ().getCountry ());
		
		TextView tv5 = (TextView) findViewById(R.id.schedule);
		tv4.setText ("Free places : " + parking.getFreePlaces () + " under a total of: " + parking.getTotalPlaces () + " places" );
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
