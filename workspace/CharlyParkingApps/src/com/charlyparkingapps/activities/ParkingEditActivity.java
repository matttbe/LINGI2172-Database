package com.charlyparkingapps.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyparkingapps.R;
import com.charlyparkingapps.db.AddressDB;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.object.Parking;

public class ParkingEditActivity extends Activity implements OnClickListener {

	private EditText mName;
	private EditText mStreet;
	private EditText mNum;
	private EditText mZip;
	private EditText mCity;
	private EditText mCountry;
	private CheckBox mDefibrilator;
	private CheckBox mDisabled;
	private TextView mPlaces;
	private Button mPlus;
	private Button mMinus;
	private Button mAddParking;

	private Parking mParking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_edit);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		this.mName = (EditText) findViewById(R.id.parking_edit_name);
		this.mStreet = (EditText) findViewById(R.id.parking_edit_street);
		this.mNum = (EditText) findViewById(R.id.parking_edit_num);
		this.mZip = (EditText) findViewById(R.id.parking_edit_zip);
		this.mCity = (EditText) findViewById(R.id.parking_edit_city);
		this.mCountry = (EditText) findViewById(R.id.parking_edit_country);

		this.mDefibrilator = (CheckBox) findViewById(R.id.parking_edit_defibrilator_cb);
		this.mDisabled = (CheckBox) findViewById(R.id.parking_edit_disabled_cb);
		
		this.mPlaces = (TextView) findViewById(R.id.parking_edit_places);
		
		this.mPlus = (Button) findViewById(R.id.places_plus);
		this.mPlus.setOnClickListener(this);
		this.mMinus = (Button) findViewById(R.id.places_minus);
		this.mMinus.setOnClickListener(this);
		this.mAddParking = (Button) findViewById(R.id.add_parking);
		this.mAddParking.setOnClickListener(this);
		
		getParking();

		if(this.mParking == null) {
			
		} else {
			this.mName.setText(this.mParking.getName());
			this.mStreet.setText(this.mParking.getAddress().getStreet());
			this.mNum.setText(this.mParking.getAddress().getNumber());
			this.mZip.setText(this.mParking.getAddress().getZip());
			this.mCity.setText(this.mParking.getAddress().getCity());
			this.mCountry.setText(this.mParking.getAddress().getCountry());

			this.mDefibrilator.setChecked(this.mParking.isDefibrillator());
			//TODO => this.mDisabled.setChecked(this.mParking.isDisabled());
			this.mPlaces.setText(""+this.mParking.getTotalPlaces());
		}
	}

	public void onStop() {
		if (mParking != null) {
			this.mParking.getAddress().setStreet(
					this.mStreet.getText().toString());
			this.mParking.getAddress().setNumber(
					Integer.parseInt(this.mNum.getText().toString()));
			this.mParking.getAddress().setZip(
					Integer.parseInt(this.mZip.getText().toString()));
			this.mParking.getAddress().setCity(this.mCity.getText().toString());
			this.mParking.getAddress().setCountry(
					this.mCountry.getText().toString());

			AddressDB addressDB = AddressDB.getInstance();
			addressDB.open(true);
			addressDB.update(this.mParking.getAddress());
			addressDB.close();

			this.mParking.setName(this.mName.getText().toString());
			this.mParking.setDefibrillator(this.mDefibrilator.isChecked());
			// this.mParking.setDisabled(this.mDisabled.isChecked());
			this.mParking.setTotalPlaces(Integer.parseInt(this.mPlaces
					.getText().toString()));

			ParkingDB parkingDB = ParkingDB.getInstance();
			parkingDB.open(true);
			parkingDB.update(this.mParking);
			parkingDB.close();
			Toast.makeText(this, R.string.modifs_saved, Toast.LENGTH_LONG)
					.show();
		}
		super.onStop();
	}

	public void getParking() {
		// TODO
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.places_minus:
			this.mPlaces
					.setText(""
							+ (Integer.parseInt(this.mPlaces.getText()
									.toString()) - 1));
			break;
		case R.id.places_plus:
			this.mPlaces
					.setText(""
							+ (Integer.parseInt(this.mPlaces.getText()
									.toString()) + 1));
			break;
		case R.id.add_parking:
			// TODO
			break;
		}

	}

}
