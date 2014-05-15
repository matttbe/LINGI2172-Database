package com.charlyparkingapps.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;
import com.charlyparkingapps.db.object.User;


public class ManagerActivity extends Activity
{
	private ArrayList<Model> allParkings;

	private User user;
	private ListView listView;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_manager);
		// Show the Up button in the action bar.
		setupActionBar ();

		listView = (ListView) findViewById (R.id.parking_list);

		// TODO: getintent().getIntent().getSerializableExtra(LIST);
		addMyParkings ();
	}

	private void addMyParkings ()
	{
		user = ((CharlyApplication) getApplication ()).getCurrentUser ();
		ParkingDB parkingDB = ParkingDB.getInstance ();
		parkingDB.open (false);
		allParkings = new ArrayList<Model> (parkingDB.getMyParkings (user));
		parkingDB.close ();
		addParkingInList (allParkings);
	}

	private void addParkingInList (List<Model> parkings)
	{
		final StableArrayAdapter adapter = new StableArrayAdapter (this,
				R.layout.parking_cell, parkings);

		listView.setAdapter (adapter);

		listView.setOnItemClickListener (new AdapterView.OnItemClickListener ()
		{

			@Override
			public void onItemClick (AdapterView<?> parent, final View view,
					int position, long id)
			{
				Parking parking = (Parking) adapter.getItem (position);
				Intent intent = new Intent (ManagerActivity.this,
						ParkingEditActivity.class);
				intent.putExtra (ParkingEditActivity.KEY_PARKING_ID,
						parking.getParkingId ());
				startActivityForResult (intent, 0);
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar ()
	{

		getActionBar ().setDisplayHomeAsUpEnabled (true);

	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		Intent intent;
		switch (item.getItemId ())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask (this);
				return true;
			case R.id.action_add_parking:
				intent = new Intent (this, ParkingEditActivity.class);
				this.startActivityForResult (intent, 0);
				break;
			case R.id.action_show_all_parkings_map:
				// TODO
				break;
		}
		return super.onOptionsItemSelected (item);
	}

	private class StableArrayAdapter extends ArrayAdapter<Model>
	{

		private HashMap<Model, Integer> mIdMap = new HashMap<Model, Integer> ();

		public StableArrayAdapter (Context context, int textViewResourceId,
				List<Model> parkings)
		{
			super (context, textViewResourceId, parkings);
			for (int i = 0; i < parkings.size (); ++i)
			{
				mIdMap.put (parkings.get (i), i);
			}
		}

		@Override
		public long getItemId (int position)
		{
			Model item = getItem (position);
			return mIdMap.get (item);
		}


		@Override
		public View getView (final int position, View convertView,
				ViewGroup parent)
		{
			LayoutInflater inflater = (LayoutInflater) getContext ()
					.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate (R.layout.parking_cell, parent,
					false);

			// _____________ TEXT

			TextView textViewFirstLine = (TextView) rowView
					.findViewById (R.id.cell_parking_name);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById (R.id.cell_parking_address);

			Parking p = (Parking) getItem (position);
			textViewFirstLine.setText (getString (R.string.parking) + " "
					+ p.getName ());
			textViewSecondLine.setText (getString (R.string.address) + " "
					+ p.getAddress ().toString ());

			OnClickListener onClickListener = new OnClickListener ()
			{
				@Override
				public void onClick (View v)
				{
					Parking parking = (Parking) getItem (position);
					Intent intent = new Intent (ManagerActivity.this,
							ParkingEditActivity.class);
					intent.putExtra (ParkingEditActivity.KEY_PARKING_SERIAL,
							parking);
					startActivityForResult (intent, 0);
				}
			};
			textViewFirstLine.setOnClickListener (onClickListener);
			textViewSecondLine.setOnClickListener (onClickListener);

			return rowView;
		}
	}



	public static String getTimeMinutes (long diff)
	{
		String desc = "";
		long diffDay = TimeUnit.MILLISECONDS.toDays (diff);
		if (diffDay > 0)
		{
			diff -= TimeUnit.MILLISECONDS.convert (diffDay, TimeUnit.DAYS);
			desc += diffDay + " days ";
		}
		long diffHour = TimeUnit.MILLISECONDS.toHours (diff);
		if (diffHour > 0)
		{
			diff -= TimeUnit.MILLISECONDS.convert (diffHour, TimeUnit.HOURS);
			desc += diffHour + " h ";
		}
		long diffMinutes = TimeUnit.MILLISECONDS.toMinutes (diff);
		if (diffMinutes > 0)
		{
			System.out.println (diff);
			diff -= TimeUnit.MILLISECONDS.convert (diffMinutes,
					TimeUnit.MINUTES);
			System.out.println (diff);
			desc += diffMinutes + " min ";
		}
		desc += TimeUnit.MILLISECONDS.toSeconds (diff) + " sec";
		return desc;
	}
}
