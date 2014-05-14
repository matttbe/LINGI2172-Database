package com.charlyparkingapps;

import android.app.ActionBar;
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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.charlyparkingapps.db.object.Car;

public class CarEditActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {

	private Car mCar;

	private EditText mCarName;
	private RadioGroup mFuelRadio;
	private TextView mCarHeight;
	private Button mPlusHeight;
	private Button mMinusHeight;

	private boolean mRemoved = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_edit);

		// TODO get the car and updat field with good infos (put car id in the
		// intent)

		this.mCarName = (EditText) findViewById(R.id.car_name_edit);
		this.mFuelRadio = (RadioGroup) findViewById(R.id.radio_fuel);
		this.mFuelRadio.setOnCheckedChangeListener(this);
		this.mCarHeight = (EditText) findViewById(R.id.car_edit_height);
		this.mPlusHeight = (Button) findViewById(R.id.height_plus);
		this.mPlusHeight.setOnClickListener(this);
		this.mMinusHeight = (Button) findViewById(R.id.height_minus);
		this.mMinusHeight.setOnClickListener(this);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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
							// TODO : delete the car form DB
							mRemoved = true;
							onBackPressed(); // return to the previous activity
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		}
			
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (!mRemoved) {
			// TODO : add modifications to the db !
		}
		super.onBackPressed();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int pos) {
		// TODO Auto-generated method stub

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
		}

	}

}
