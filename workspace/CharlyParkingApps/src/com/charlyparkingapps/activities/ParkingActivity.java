package com.charlyparkingapps.activities;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.HistoryDB;
import com.charlyparkingapps.db.OpeningHoursDB;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.object.History;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.OpeningHours;
import com.charlyparkingapps.db.object.Parking;
import com.charlyparkingapps.db.object.User;

public class ParkingActivity extends Activity {

	public static final String KEY_PARKING = "parkingID";
	private  Parking parking;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking);
		// Show the Up button in the action bar.
		setupActionBar();
		user = ((CharlyApplication) getApplication ()).getCurrentUser ();
		int id = getIntent().getIntExtra(KEY_PARKING, 0);
		if (id > 0)
			loadParking(id);
	}

	private void loadParking(int id) {
		ParkingDB pdb = ParkingDB.getInstance ();
		pdb.open (false);
		parking = (Parking) pdb.getById (id);
		parking.loadAddress ();
		pdb.close ();
		TextView tv1 = (TextView) findViewById(R.id.parking_name);
		tv1.setText (parking.getName ());
		TextView tv2 = (TextView) findViewById(R.id.parking_street_number);
		if (parking.getAddress() != null) {
			tv2.setText(parking.getAddress().getStreet() + " "
					+ parking.getAddress().getNumber());

			TextView tv3 = (TextView) findViewById(R.id.parking_zip_city);
			tv3.setText(parking.getAddress().getZip() + " "
					+ parking.getAddress().getCity());
			TextView tv4 = (TextView) findViewById(R.id.parking_country);
			tv4.setText(parking.getAddress().getCountry());
		}

		TextView defibrilator = (TextView) findViewById(R.id.defibrilator_parking);
		String defString = (this.parking.isDefibrillator()) ? getString(R.string.yes)
				: getString(R.string.no);
		defibrilator.setText(defibrilator.getText() + " : " + defString);

		TextView tv5 = (TextView) findViewById(R.id.freePlaces);
		tv5.setText ("Free places : " + parking.getFreePlaces ()
				+ " under a total of: " + parking.getTotalPlaces () + " places");

		ProgressBar pb = (ProgressBar) findViewById (R.id.freePlacesProgressBar);
		pb.setMax (parking.getTotalPlaces ());
		pb.setProgress(parking.getTotalPlaces() - parking.getFreePlaces());
		user.getMyFavoriteCar ();
		HistoryDB hdb = HistoryDB.getInstance ();
		hdb.open (false);
		if (user.getMyFavoriteCar () == null)
		{
			((TextView) findViewById (R.id.no_car))
					.setVisibility (View.VISIBLE);
		}
		else
		{
			List<Model> l = hdb.getAllCurrentHistoriesOrdered (user,
					user.getMyFavoriteCar ());
			if (l.size () == 0)
			{
				Button park = (Button) findViewById (R.id.action_use_parking);
				park.setVisibility (View.VISIBLE);
				park.setOnClickListener (new OnClickListener ()
				{

					@Override
					public void onClick (View v)
					{
						addInHistory ();
					}
				});
			}
			else
			{
				if (((History) l.get (0)).getParkingId () == parking
						.getParkingId ())
				{
					Button endPark = (Button) findViewById (R.id.action_use_parking_end);
					endPark.setVisibility (View.VISIBLE);
					endPark.setOnClickListener (new OnClickListener ()
					{

						@Override
						public void onClick (View v)
						{
							leaveParking ();
						}
					});
				}
			}




		}

		GridView gridView = (GridView) findViewById (R.id.opening_hours_grid);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
				android.R.layout.simple_list_item_1, buildOpeningHoursList ());

		gridView.setAdapter (adapter);
		hdb.close ();

	}

	private String[] buildOpeningHoursList ()
	{
		String[] list = new String[7 * 3];
		list[0] = getString (R.string.monday);
		list[3] = getString (R.string.tuesday);
		list[6] = getString(R.string.wednesday);
		list[9] = getString(R.string.thursday);
		list[12] = getString(R.string.friday);
		list[15] = getString(R.string.saturday);
		list[18] = getString(R.string.sunday);
		OpeningHoursDB odb = OpeningHoursDB.getInstance ();
		odb.open (false);
		for (int i = 0; i < 7; i++) {
			List<Model> l = odb.getOpeningHoursForParkingADay (parking, i);
			if (l.size() <= 0) {
				list[(i * 3) + 1] = getString(R.string.closed);
				list[(i * 3) + 2] = "-";
			} else {
				list[(i * 3) + 1] = ((OpeningHours) l.get(0)).getHourStart()
						.getHours ()
						+ ":"
						+ ((OpeningHours) l.get (0)).getHourStart ()
								.getMinutes ();
				list[(i * 3) + 2] = ((OpeningHours) l.get(0)).getHourEnd()
						.getHours ()
						+ ":"
						+ ((OpeningHours) l.get (0)).getHourEnd ()
								.getMinutes ();
			}
		}
		
		odb.close ();
		
		return list;
		
	}

	private void leaveParking ()
	{
		HistoryDB hdb = HistoryDB.getInstance ();
		hdb.open (true);
		History h = ((History) hdb.getAllCurrentHistoriesOrdered (user,
				user.getMyFavoriteCar ()).get (0));

		h.setEnd (new Date ());
		hdb.update (h);
		hdb.close ();
	}

	private void addInHistory ()
	{
		History h = new History (user.getMyFavoriteCar ().getCarId (),
				new Date (), null, parking.getParkingId (), user.getId (), 0);
		HistoryDB hdb = HistoryDB.getInstance ();
		hdb.open (true);
		hdb.save (h);
		hdb.close ();
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
