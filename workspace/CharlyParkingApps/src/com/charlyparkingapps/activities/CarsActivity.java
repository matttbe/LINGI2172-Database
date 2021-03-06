package com.charlyparkingapps.activities;

import java.util.ArrayList;
import java.util.Date;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.CarDB;
import com.charlyparkingapps.db.HistoryDB;
import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.Car;
import com.charlyparkingapps.db.object.History;
import com.charlyparkingapps.db.object.Model;
import com.charlyparkingapps.db.object.Parking;
import com.charlyparkingapps.db.object.User;

public class CarsActivity extends Activity {

	private RadioButton mSelectedRB;
	private Car favoriteCar;
	private ArrayList<Model> allCars;

	private User user;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car);
		// Show the Up button in the action bar.
		setupActionBar();

		listView = (ListView) findViewById(R.id.cars_list);

		// TODO: getintent().getIntent().getSerializableExtra(LIST);
		addUserCars();
	}

	private void addUserCars() {
		user = ((CharlyApplication) getApplication()).getCurrentUser();
		favoriteCar = user.getMyFavoriteCar();
		int userId = user.getId();
		CarDB carDB = CarDB.getInstance();
		carDB.open(false);
		allCars = new ArrayList<Model>(CarDB.getInstance().getAllCars(userId));
		carDB.close();
		addCarsInList(allCars);
	}

	private void addCarsInList(List<Model> cars) {
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.cars_item_view, cars,
				favoriteCar != null ? favoriteCar.getCarId() : 0);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				Car car = (Car) adapter.getItem(position);
				Intent intent = new Intent(CarsActivity.this,
						CarEditActivity.class);
				intent.putExtra(CarEditActivity.KEY_CAR_SERIAL, car);
				startActivityForResult(intent, 0);
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
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_add_car:
			intent = new Intent(this, CarEditActivity.class);
			this.startActivityForResult(intent, 0);
			break;
		case R.id.action_show_all_cars_map:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra(MainActivity.KEY_CARS_LIST, allCars);
			this.startActivity(intent);
			break;
		case R.id.action_show_parked_cars_map:
			HistoryDB historyDB = HistoryDB.getInstance();
			historyDB.open(false);
			List<Model> currentHistory = historyDB
					.getAllCurrentHistoriesOrdered(user);
			historyDB.close();
			if (currentHistory.size() > 0) {
				ArrayList<Car> cars = new ArrayList<Car>();
				for (Model model : currentHistory) {
					cars.add(((History) model).getCar());
				}
				intent = new Intent(this, MainActivity.class);
				intent.putExtra(MainActivity.KEY_CARS_LIST, cars);
				this.startActivity(intent);
			}
			else
				Toast.makeText(this, "No parked cars", Toast.LENGTH_LONG)
						.show();
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
					startActivityForResult(intent, 0);
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
					user.setMyFavoriteCar(mCar);
					UserDB userDB = UserDB.getInstance();
					userDB.open(true);
					userDB.update(user);
					userDB.close();
				}
			});

			// ______________ MAP

			Button mapButton = (Button) rowView
					.findViewById(R.id.cars_row_car_map);
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

			// _____________ Parking
			Button parkingButton = (Button) rowView
					.findViewById(R.id.cars_row_parking);

			// ______________ DATA
			Car car = (Car) getItem(position);
			textViewFirstLine.setText(String.valueOf(car.getName()));
			String desc = getString(R.string.height_colon) + " "
					+ car.getHeight() + " ";

			HistoryDB historyDB = HistoryDB.getInstance();
			historyDB.open(false);
			List<Model> currentHistory = historyDB.getCurrentParking(car);
			historyDB.close();

			if (currentHistory.size() > 0) {
				final History history = (History) currentHistory.get(0);
				Date start = history.getStart();
				long diff = new Date().getTime() - start.getTime();
				desc += " - " + getTimeMinutes(diff);

				parkingButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Parking parking = history.getParking();
						Intent intent = new Intent(CarsActivity.this,
								MainActivity.class);
						intent.putExtra(MainActivity.KEY_PARKING, parking);
						startActivity(intent);
					}
				});
			}
 else
				parkingButton.setVisibility(View.GONE);
			textViewSecondLine.setText(desc);

			// select car
			if (car.getCarId() == this.favoriteCarID)
				selected.setChecked(true);

			return rowView;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		listView.setAdapter(null);
		addUserCars();
		System.out.println("Result");
	}

	public static String getTimeMinutes(long diff) {
		String desc = "";
		long diffDay = TimeUnit.MILLISECONDS.toDays(diff);
		if (diffDay > 0) {
			diff -= TimeUnit.MILLISECONDS.convert(diffDay, TimeUnit.DAYS);
			desc += diffDay + " days ";
		}
		long diffHour = TimeUnit.MILLISECONDS.toHours(diff);
		if (diffHour > 0) {
			diff -= TimeUnit.MILLISECONDS.convert(diffHour, TimeUnit.HOURS);
			desc += diffHour + " h ";
		}
		long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		if (diffMinutes > 0) {
			System.out.println(diff);
			diff -= TimeUnit.MILLISECONDS
					.convert(diffMinutes, TimeUnit.MINUTES);
			System.out.println(diff);
			desc += diffMinutes + " min ";
		}
		desc += TimeUnit.MILLISECONDS.toSeconds(diff) + " sec";
		return desc;
	}
}
