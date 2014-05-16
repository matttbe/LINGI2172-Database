package com.charlyparkingapps.activities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.HistoryDB;
import com.charlyparkingapps.db.object.History;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.User;


public class HistoryActivity extends Activity
{

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_history);
		// Show the Up button in the action bar.
		setupActionBar ();

		addUserHistory ();
	}

	private void addUserHistory ()
	{
		User user = ((CharlyApplication) getApplication ()).getCurrentUser ();
		// int userId = user.getId ();
		HistoryDB hdb = HistoryDB.getInstance ();
		hdb.open (false);
		List<Model> allHistory = hdb
				.getAllHistoriesOrdered (user);
		List<Model> allCurrentHist = hdb.getAllCurrentHistoriesOrdered (user);
		hdb.close ();
		addHistoryInList (allHistory, allCurrentHist);
	}

	private void addHistoryInList (List<Model> history, List<Model> current)
	{
		final ListView listView = (ListView) findViewById (R.id.history_list);
		final StableArrayAdapter adapter = new StableArrayAdapter (this,
				R.layout.history_cell, history);

		listView.setAdapter (adapter);

		listView.setOnItemClickListener (new AdapterView.OnItemClickListener ()
		{

			@Override
			public void onItemClick (AdapterView<?> parent, final View view,
					int position, long id)
			{
				History h = (History) adapter.getItem (position);

				Intent intent = new Intent (HistoryActivity.this,
						MainActivity.class);
				intent.putExtra (MainActivity.KEY_PARKING, h.getParking ());
				startActivity (intent);

			}
		});

		final ListView listView2 = (ListView) findViewById (R.id.stationnary_cars_list);
		final StableArrayAdapter2 adapter2 = new StableArrayAdapter2 (this,
				R.layout.stationnary_car_cell, current);

		listView2.setAdapter (adapter2);

		listView2
				.setOnItemClickListener (new AdapterView.OnItemClickListener ()
				{

					@Override
					public void onItemClick (AdapterView<?> parent,
							final View view, int position, long id)
					{
						History h = (History) adapter.getItem (position);

						Intent intent = new Intent (HistoryActivity.this,
								MainActivity.class);
						intent.putExtra (MainActivity.KEY_PARKING,
								h.getParking ());
						startActivity (intent);
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
		getMenuInflater ().inflate (R.menu.history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId ())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask (this);
				return true;
		}
		return super.onOptionsItemSelected (item);
	}

	private class StableArrayAdapter extends ArrayAdapter<Model>
	{

		HashMap<Model, Integer> mIdMap = new HashMap<Model, Integer> ();

		public StableArrayAdapter (Context context, int textViewResourceId,
				List<Model> history)
		{
			super (context, textViewResourceId, history);
			for (int i = 0; i < history.size (); ++i)
			{
				mIdMap.put (history.get (i), i);
			}
		}

		@Override
		public long getItemId (int position)
		{
			Model item = getItem (position);
			return mIdMap.get (item);
		}

		@Override
		public View getView (int position, View convertView,
				ViewGroup parent)
		{
			LayoutInflater inflater = (LayoutInflater) getContext ()
					.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate (R.layout.history_cell, parent,
					false);
			TextView textViewFirstLine = (TextView) rowView
					.findViewById (R.id.cell_history_parking);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById (R.id.cell_history_car);
			TextView textViewThirdLine = (TextView) rowView
					.findViewById (R.id.cell_history_date);
			TextView textViewFourthLine = (TextView) rowView
					.findViewById (R.id.cell_history_time);

			final History h = (History) getItem (position);
			textViewFirstLine.setText (getString (R.string.parking) + " "
					+ h.getParking ().getName ());
			textViewSecondLine.setText (getString (R.string.car) + " "
					+ h.getCar ().getName ());
			textViewThirdLine.setText (getString (R.string.time) + " "
					+ (h.getEnd ().getHours () - h.getStart ().getHours ())
					+ " " + getString (R.string.hour_time_separator) + " "
					+ (h.getEnd ().getMinutes () - h.getStart ().getMinutes ())
					% 60 + " " + getString (R.string.minutes_time_separator));

			RatingBar rb = (RatingBar) rowView
					.findViewById (R.id.cell_history_rate);
			if (h.getFeedback () > 0)
			{
				rb.setRating (h.getFeedback ());

			}
			rb.setOnRatingBarChangeListener (new OnRatingBarChangeListener ()
			{

				@Override
				public void onRatingChanged (RatingBar ratingBar, float rating,
						boolean fromUser)
				{
					h.setFeedback ((int) rating);
					HistoryDB hdb = HistoryDB.getInstance ();
					hdb.open (true);
					hdb.update (h);

					hdb.close ();
				}
			});

			return rowView;
		}
	}

	private class StableArrayAdapter2 extends ArrayAdapter<Model>
	{

		HashMap<Model, Integer> mIdMap = new HashMap<Model, Integer> ();

		public StableArrayAdapter2 (Context context, int textViewResourceId,
				List<Model> history)
		{
			super (context, textViewResourceId, history);
			for (int i = 0; i < history.size (); ++i)
			{
				mIdMap.put (history.get (i), i);
			}
		}

		@Override
		public long getItemId (int position)
		{
			Model item = getItem (position);
			return mIdMap.get (item);
		}

		@Override
		public View getView (int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = (LayoutInflater) getContext ()
					.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate (R.layout.stationnary_car_cell,
					parent,
					false);
			TextView textViewFirstLine = (TextView) rowView
					.findViewById (R.id.cell_stationnary_parking);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById (R.id.cell_stationnary_car);
			TextView textViewThirdLine = (TextView) rowView
					.findViewById (R.id.cell_stationnary_from);
			TextView textViewFourthLine = (TextView) rowView
					.findViewById (R.id.cell_stationnary_hourly_rate);

			History h = (History) getItem (position);
			Date now = new Date();

			textViewFirstLine.setText (getString (R.string.parking) + " "
					+ h.getParking ().getName ());
			textViewSecondLine.setText (getString (R.string.car) + " "
					+ h.getCar ().getName ());
			textViewThirdLine.setText (getString (R.string.from) + " "
					+ (now.getHours () - h.getStart ().getHours ())+ " "
					+ getString (R.string.hour_time_separator)+ " "
					+ (now.getMinutes () - h.getStart ().getMinutes ()) % 60
					+ " " + getString (R.string.minutes_time_separator));


			return rowView;
		}
	}
}
