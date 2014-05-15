package com.charlyparkingapps.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.AddressDB;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.object.Address;
import com.charlyparkingapps.db.object.Parking;

public class ParkingEditActivity extends Activity implements OnClickListener {

	public static final String KEY_PARKING_ID = "parkingID";
	public static final String KEY_PARKING_SERIAL = "parkingSerial";

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
		setContentView(R.layout.activity_parking_edit);
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
			this.mAddParking.setVisibility(View.VISIBLE);
		} else {
			this.mName.setText(this.mParking.getName());
			this.mStreet.setText(this.mParking.getAddress().getStreet());
			this.mNum.setText(this.mParking.getAddress().getNumber());
			this.mZip.setText(this.mParking.getAddress().getZip());
			this.mCity.setText(this.mParking.getAddress().getCity());
			this.mCountry.setText(this.mParking.getAddress().getCountry());

			this.mDefibrilator.setChecked(this.mParking.isDefibrillator());
			this.mDisabled.setChecked(this.mParking
					.getHasPlacesForDisabledPeople());
			this.mPlaces.setText(""+this.mParking.getTotalPlaces());
		}
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
		switch (id) {
		case R.id.action_settings:
			return true;
		case android.R.id.home:
			this.onBackPressed();
			break;
		case R.id.action_remove_parking:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.are_you_sure);
			builder.setTitle(R.string.remove_parking);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (mParking == null)
								return;
							ParkingDB parkingDB = ParkingDB.getInstance();
							parkingDB.open(false);
							parkingDB.delete(mParking.getParkingId());
							parkingDB.close();
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
			
		return true;
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

			Geocoder geocoder = new Geocoder(this);
			try {
				android.location.Address address = geocoder
						.getFromLocationName(
						this.mParking.getAddress().toString(), 1).get(0);
				this.mParking.getAddress().setLatitude(address.getLatitude());
				this.mParking.getAddress().setLongitude(address.getLongitude());
			} catch (IOException e) {
				e.printStackTrace();
			}

			AddressDB addressDB = AddressDB.getInstance();
			addressDB.open(true);
			addressDB.update(this.mParking.getAddress());
			addressDB.close();

			this.mParking.setName(this.mName.getText().toString());
			this.mParking.setDefibrillator(this.mDefibrilator.isChecked());
			this.mParking.setHasPlacesForDisabledPeople(this.mDisabled
					.isChecked());
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
		int parkingId = getIntent ().getIntExtra (KEY_PARKING_ID, 0);

		if (parkingId > 0)
		{
			ParkingDB parkingDB = ParkingDB.getInstance ();
			parkingDB.open (false);
			this.mParking = (Parking) parkingDB.getById (parkingId);
			parkingDB.close ();
			return;
		}

		this.mParking = (Parking) getIntent ().getSerializableExtra (
				KEY_PARKING_SERIAL);
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
			Address parkingAddress = new Address(mStreet.getText().toString(),
					Integer.parseInt(mNum.getText().toString()), mCity
							.getText().toString(), Integer.parseInt(mZip
							.getText().toString()), mCountry.getText()
							.toString());
			
			Geocoder geocoder = new Geocoder(this);
			try {
				android.location.Address address = geocoder
						.getFromLocationName(parkingAddress.toString(), 1).get(
								0);
				parkingAddress.setLatitude(address.getLatitude());
				parkingAddress.setLongitude(address.getLongitude());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			int userID = ((CharlyApplication) getApplication()).getCurrentUser().getId();
			Parking newParking = new Parking(mName.getText().toString(),
					mDefibrilator.isChecked(), Integer.parseInt(mPlaces
							.getText().toString()), Integer.parseInt(mPlaces
							.getText().toString()), 150, mDisabled.isChecked(),
					userID);

			ParkingDB parkingDB = ParkingDB.getInstance();
			parkingDB.open(true);
			long pid = parkingDB.save(newParking);
			

			parkingAddress.setParkingID((int) pid);
			newParking.setAddress(parkingAddress);

			parkingDB.update(newParking);
			parkingDB.close();

			AddressDB addressDB = AddressDB.getInstance();
			addressDB.open(true);
			addressDB.save(parkingAddress);
			addressDB.close();


			this.onBackPressed();
			break;
		}

	}

}
