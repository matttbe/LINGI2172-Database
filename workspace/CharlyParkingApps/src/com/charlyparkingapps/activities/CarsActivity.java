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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.CarDB;
import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.User;

public class CarsActivity extends Activity {

	private RadioButton mSelectedRB;
	private Car favoriteCar;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car);
		// Show the Up button in the action bar.
		setupActionBar();

		listView = (ListView) findViewById(R.id.cars_list);

		// TODO: getintent().getIntent().getSerializableExtra(LIST);
	}

	protected void onStart() {
		super.onStart();
		addUserCars();
	}

	private void addUserCars() {
		User user = ((CharlyApplication) getApplication()).getCurrentUser();
		favoriteCar = user.getMyFavoriteCar();
		int userId = user.getId();
		CarDB carDB = CarDB.getInstance();
		carDB.open(false);
		List<Model> allCars = CarDB.getInstance().getAllCars(userId);
		carDB.close();
		addCarsInList(allCars);
	}

	private void addCarsInList(List<Model> cars) {
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.cars_item_view, cars, favoriteCar.getCarId());

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

		private HashMap<Model, Integer> mIdMap = new HashMap<Model, Integer>();
		private int favoriteCarID;

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<Model> cars, int favoriteCarID) {
			super(context, textViewResourceId, cars);
			this.favoriteCarID = favoriteCarID;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.cars_item_view, parent,
					false);

			// _____________ TEXT

			TextView textViewFirstLine = (TextView) rowView
					.findViewById(R.id.cars_item_firstLine);
			TextView textViewSecondLine = (TextView) rowView
					.findViewById(R.id.cars_item_secondLine);

			OnClickListener onClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Car car = (Car) getItem(position);
					Intent intent = new Intent(CarsActivity.this,
							CarEditActivity.class);
					intent.putExtra(CarEditActivity.KEY_CAR_SERIAL, car);
					startActivity(intent);
				}
			};
			textViewFirstLine.setOnClickListener(onClickListener);
			textViewSecondLine.setOnClickListener(onClickListener);

			// __________ RADIO

			RadioButton selected = (RadioButton) rowView
					.findViewById(R.id.selected_car);
			selected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// we click on another button: uncheck the previous one
					if (mSelectedRB != null) {
						mSelectedRB.setChecked(false);
					}
					mSelectedRB = (RadioButton) buttonView;

					Car mCar = (Car) getItem(position);
					User currentUser = ((CharlyApplication) getApplication())
							.getCurrentUser();
					currentUser.setMyFavoriteCar(mCar);
					UserDB userDB = UserDB.getInstance();
					userDB.open(true);
					userDB.update(currentUser);
					userDB.close();
				}
			});

			// ______________ ICON

			ImageButton mapButton = (ImageButton) rowView
					.findViewById(R.id.car_map);
			mapButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Car car = (Car) getItem(position);
					Intent intent = new Intent(CarsActivity.this,
							MainActivity.class);
					intent.putExtra(MainActivity.KEY_CAR, car);
					startActivity(intent);
				}
			});

			// ______________ DATA
			Car car = (Car) getItem(position);
			textViewFirstLine.setText(String.valueOf(car.getName()));
			textViewSecondLine.setText(getString(R.string.height_colon) + " "
					+ car.getHeight());

			// select car
			if (car.getCarId() == this.favoriteCarID)
				selected.setChecked(true);

			return rowView;
		}
	}
}
