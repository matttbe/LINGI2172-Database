package com.charlyparkingapps.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.CarDB;
import com.charlyparkingapps.db.object.Car;

public class CarEditActivity extends Activity implements OnClickListener {

	public static final String KEY_CAR_ID = "carID";
	public static final String KEY_CAR_SERIAL = "carSerial";

	private Car mCar;

	private EditText mCarName;
	private RadioGroup mFuelRadio;
	private TextView mCarHeight;
	private Button mPlusHeight;
	private Button mMinusHeight;
	private Button mAddCar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_edit);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		this.initView();

		getCar();

		// Add a new car
		if (mCar == null) {
			this.mAddCar.setVisibility(View.VISIBLE);
		}
		// Edit an existing car
		else {
			mCarName.setText(mCar.getName());
			System.out.println("Check: " + (mCar.getFuelId() - 1));
			RadioButton fuelButton = (RadioButton) mFuelRadio.getChildAt(mCar
					.getFuelId() - 1); // mFuelRadio.check() doesn't work...
			if (fuelButton != null)
				fuelButton.setChecked(true);
			mCarHeight.setText(String.valueOf(mCar.getHeight()));
		}
	}

	private void getCar() {
		int carId = getIntent().getIntExtra(KEY_CAR_ID, 0);

		if (carId > 0) {
			CarDB carDB = CarDB.getInstance();
			carDB.open(false);
			this.mCar = (Car) carDB.getById(carId);
			carDB.close();
			return;
		}

		this.mCar = (Car) getIntent().getSerializableExtra(KEY_CAR_SERIAL);
	}

	private void initView() {
		mCarName = (EditText) findViewById(R.id.car_name_edit);
		mFuelRadio = (RadioGroup) findViewById(R.id.radio_fuel);
		mCarHeight = (TextView) findViewById(R.id.car_edit_height);
		mPlusHeight = (Button) findViewById(R.id.height_plus);
		mPlusHeight.setOnClickListener(this);
		mMinusHeight = (Button) findViewById(R.id.height_minus);
		mMinusHeight.setOnClickListener(this);
		mAddCar = (Button) findViewById(R.id.add_car);
		mAddCar.setOnClickListener(this);
	}

	private int getSelectedFuelNb() {
		return mFuelRadio.indexOfChild(findViewById(mFuelRadio
				.getCheckedRadioButtonId())) + 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.car_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id) {
		case R.id.action_settings :
			return true;
		case android.R.id.home:
			this.onBackPressed();
			break;
		case R.id.action_remove_car:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.are_you_sure);
			builder.setTitle(R.string.remove_car);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (mCar == null)
								return;
							CarDB carDB = CarDB.getInstance();
							carDB.open(false);
							carDB.delete(mCar.getCarId());
							carDB.close();
							onBackPressed(); // return to the previous activity
						}
					});
			builder.setNegativeButton(R.string.no,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							return;
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		}
			
		return super.onOptionsItemSelected(item);
	}

	public void onStop() {
		if (mCar != null) {
			this.mCar.setHeight(Integer.parseInt(this.mCarHeight.getText()
					.toString()));
			this.mCar.setName(this.mCarName.getText().toString());

			this.mCar.setFuelId(getSelectedFuelNb());
			CarDB carDB = CarDB.getInstance();
			carDB.open(true);
			carDB.update(this.mCar);
			carDB.close();
			Toast.makeText(this, R.string.modifs_saved, Toast.LENGTH_LONG)
					.show();
		}
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.height_minus:
			this.mCarHeight.setText(""
							+ (Integer.parseInt(this.mCarHeight.getText()
									.toString()) - 1));
			break;
		case R.id.height_plus:
			this.mCarHeight
					.setText(""
							+ (Integer.parseInt(this.mCarHeight.getText()
									.toString()) + 1));
			break;
		case R.id.add_car:
			CarDB carDB = CarDB.getInstance();
			carDB.open(true);
			carDB.save(new Car(
					Integer.parseInt(mCarHeight.getText().toString()),
					this.mCarName.getText().toString(),
 getSelectedFuelNb(),
					((CharlyApplication) getApplication()).getCurrentUser()
							.getId(),
					0.0,
					0.0));
			carDB.close();
			this.onBackPressed();
			break;
		}

	}

}
