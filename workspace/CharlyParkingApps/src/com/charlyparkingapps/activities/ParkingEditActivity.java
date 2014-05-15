package com.charlyparkingapps.activities;

import android.app.Activity;
import android.os.Bundle;

import com.charlyparkingapps.R;

public class ParkingEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_edit);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

}
