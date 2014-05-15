package com.charlyparkingapps.activities;

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
import android.widget.TextView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.HistoryDB;
import com.charlyparkingapps.db.object.Car;
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


	}

	private void addUserHistory ()
	{
		User user = ((CharlyApplication) getApplication ()).getCurrentUser ();
		// int userId = user.getId ();
		HistoryDB hdb = HistoryDB.getInstance ();
		hdb.open (false);
		List<Model> allHistory = HistoryDB.getInstance ()
				.getAllHistoriesOrdered (user);
		hdb.close ();
		addHistoryInList (allHistory);
	}

	private void addHistoryInList (List<Model> history)
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
				Car car = (Car) adapter.getItem (position);
				Intent intent = new Intent (HistoryActivity.this,
						CarEditActivity.class);
				intent.putExtra (CarEditActivity.KEY_CAR_SERIAL, car);
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
		public boolean hasStableIds ()
		{
			return true;
		}

		@Override
		public View getView (int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = (LayoutInflater) getContext ()
					.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate (R.layout.history_cell, parent,
					false);
			TextView textViewFirstLine = (TextView) rowView
					.findViewById (R.id.cell_history_parking);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById (R.id.cell_stationnary_car);

			History h = (History) getItem (position);
			textViewFirstLine.setText (String.valueOf (h.getParking ()
					.getName ()));
			textViewSecondLine.setText (getString (R.string.height_colon) + " "
					+ h.getCar ().getName ());

			return rowView;
		}
	}
}
