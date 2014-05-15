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
import com.charlyparkingapps.db.CarDB;
import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.Model;

public class CarsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car);
		// Show the Up button in the action bar.
		setupActionBar();

		// TODO: getintent().getIntent().getSerializableExtra(LIST);
		addUserCars();
	}

	private void addUserCars() {
		int userId = ((CharlyApplication) getApplication()).getCurrentUser()
				.getId();
		CarDB carDB = CarDB.getInstance();
		carDB.open(false);
		List<Model> allCars = CarDB.getInstance().getAllCars(userId);
		carDB.close();
		addCarsInList(allCars);
	}

	private void addCarsInList(List<Model> cars) {
		final ListView listView = (ListView) findViewById(R.id.cars_list);
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.cars_item_view, cars);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				Car car = (Car) adapter.getItem(position);
				Intent intent = new Intent(CarsActivity.this,
						CarEditActivity.class);
				intent.putExtra(CarEditActivity.KEY_CAR_SERIAL, car);
				startActivity(intent);
			}
		});
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
		getMenuInflater().inflate(R.menu.car, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_add_car:
			Intent intent = new Intent(this, CarEditActivity.class);
			this.startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class StableArrayAdapter extends ArrayAdapter<Model> {

		HashMap<Model, Integer> mIdMap = new HashMap<Model, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<Model> cars) {
			super(context, textViewResourceId, cars);
			for (int i = 0; i < cars.size(); ++i) {
				mIdMap.put(cars.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			Model item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.cars_item_view, parent,
					false);
			TextView textViewFirstLine = (TextView) rowView
					.findViewById(R.id.cars_item_firstLine);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById(R.id.cars_item_secondLine);
			
			Car car = (Car) getItem(position);
			textViewFirstLine.setText(String.valueOf(car.getName()));
			textViewSecondLine.setText(getString(R.string.height_colon) + " "
					+ car.getHeight());

			return rowView;
		}
	}
}
