package com.charlyparkingapps.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.charlyparkingapps.R;

public class FiltersActivity extends Activity implements
		CheckBox.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener,
		View.OnClickListener, OnItemSelectedListener {

	// Preferences
	private SharedPreferences.Editor mEditor;
	private SharedPreferences mPreferences;

	// Interface objects
	// checkboxes
	private CheckBox mDefibrilatorCB;
	private CheckBox mHandicapedCB;
	private CheckBox mFuelCB;
	private CheckBox mDieselCB;
	private CheckBox mLPGCB;
	private CheckBox mEthanolCB;
	private CheckBox mOneFreeCB;

	// Seekbars
	private SeekBar mTotalPlacesSB;
	private SeekBar mFreePlacesSB;
	private SeekBar mPriceSB;

	// Buttons
	private Button mFavoriteButton;
	private Button mUsingButton;
	private Button mMineButton;
	private Button mMinus;
	private Button mPlus;

	// Spinner
	private Spinner mSortSpinner;

	// TextViews
	private TextView mTotalPlacesTV;
	private TextView mFreePlacesTV;
	private TextView mPriceTV;
	private TextView mRadius;

	// Strings for preferences
	public final static String DEFIBRILATOR_PREF = "defibrilator";
	public final static String HANDICAPED_PREF = "handicaped";
	public final static String FUEL_PREF = "fuel";
	public final static String DIESEL_PREF = "diesel";
	public final static String LPG_PREF = "lpg";
	public final static String ETHANOL_PREF = "ethanol";
	public final static String ONEFREESPOT_PREF = "one free";
	public final static String TOTALPLACES_PREF = "total places";
	public final static String FREEPLACES_PREF = "free places";
	public final static String SORT_PREF = "sorting";
	public final static String PRICE_PREF = "price";
	public final static String RADIUS_PREF = "radius";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_search);

		// Preferences
		this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		this.mEditor = mPreferences.edit();

		// Interfaces Objects
		// Checkboxes
		this.mDefibrilatorCB = (CheckBox) findViewById(R.id.defibrilator_cb);
		this.mDefibrilatorCB.setOnCheckedChangeListener(this);
		this.mDefibrilatorCB.setChecked(this.mPreferences.getBoolean(DEFIBRILATOR_PREF, false));
		this.mHandicapedCB = (CheckBox) findViewById(R.id.handicaped_cb);
		this.mHandicapedCB.setOnCheckedChangeListener(this);
		this.mHandicapedCB.setChecked(this.mPreferences.getBoolean(HANDICAPED_PREF, false));
		this.mFuelCB = (CheckBox) findViewById(R.id.fuel_cb);
		this.mFuelCB.setOnCheckedChangeListener(this);
		this.mFuelCB.setChecked(this.mPreferences.getBoolean(FUEL_PREF, false));
		this.mDieselCB = (CheckBox) findViewById(R.id.diesel_cb);
		this.mDieselCB.setOnCheckedChangeListener(this);
		this.mDieselCB.setChecked(this.mPreferences.getBoolean(DIESEL_PREF, false));
		this.mLPGCB = (CheckBox) findViewById(R.id.lpg_cb);
		this.mLPGCB.setOnCheckedChangeListener(this);
		this.mLPGCB.setChecked(this.mPreferences.getBoolean(LPG_PREF, false));;
		this.mEthanolCB = (CheckBox) findViewById(R.id.ethanol_cb);
		this.mEthanolCB.setOnCheckedChangeListener(this);
		this.mEthanolCB.setChecked(this.mPreferences.getBoolean(ETHANOL_PREF, false));
		this.mOneFreeCB = (CheckBox) findViewById(R.id.one_free_place_cb);
		this.mOneFreeCB.setOnCheckedChangeListener(this);
		this.mOneFreeCB.setChecked(this.mPreferences.getBoolean(ONEFREESPOT_PREF, false));
		
		// TextViews
		this.mTotalPlacesTV = (TextView) findViewById(R.id.total_places_tv);
		this.mFreePlacesTV = (TextView) findViewById(R.id.free_places_tv);
		this.mPriceTV = (TextView) findViewById(R.id.price_tv);
		this.mRadius = (TextView) findViewById(R.id.filter_radius);
		this.mRadius.setText("" + this.mPreferences.getInt(RADIUS_PREF, 100));

		// SeekBars
		this.mTotalPlacesSB = (SeekBar) findViewById(R.id.total_places_sb);
		this.mTotalPlacesSB.setOnSeekBarChangeListener(this);
		this.mTotalPlacesSB.setProgress(this.mPreferences.getInt(TOTALPLACES_PREF, 0));
		this.mFreePlacesSB = (SeekBar) findViewById(R.id.free_places_sb);
		this.mFreePlacesSB.setOnSeekBarChangeListener(this);
		this.mFreePlacesSB.setProgress(this.mPreferences.getInt(FREEPLACES_PREF, 0));
		this.mPriceSB = (SeekBar) findViewById(R.id.price_sb);
		this.mPriceSB.setOnSeekBarChangeListener(this);
		this.mPriceSB.setProgress(this.mPreferences.getInt(PRICE_PREF, 0));

		// Buttons
		this.mFavoriteButton = (Button) findViewById(R.id.favorites_button);
		this.mFavoriteButton.setOnClickListener(this);
		this.mUsingButton = (Button) findViewById(R.id.using_button);
		this.mUsingButton.setOnClickListener(this);
		this.mMineButton = (Button) findViewById(R.id.mine_button);
		this.mMineButton.setOnClickListener(this);
		this.mMinus = (Button) findViewById(R.id.radius_minus);
		this.mMinus.setOnClickListener(this);
		this.mPlus = (Button) findViewById(R.id.radius_plus);
		this.mPlus.setOnClickListener(this);

		// Spinner
		this.mSortSpinner = (Spinner) findViewById(R.id.sort_spinner);
		this.mSortSpinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.sort_spinner_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mSortSpinner.setAdapter(adapter);
	}

	// ________________ LISTERNER FOR THE MENU

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// menu.a
		return true;
	}

	// Checkboxes listerner
	@Override
	public void onCheckedChanged(CompoundButton cb, boolean checked) {
		String prefStr = "";
		switch (cb.getId()) {
		case R.id.defibrilator_cb:
			prefStr = DEFIBRILATOR_PREF;
			break;
		case R.id.handicaped_cb:
			prefStr = HANDICAPED_PREF;
			break;
		case R.id.fuel_cb:
			prefStr = FUEL_PREF;
			break;
		case R.id.diesel_cb:
			prefStr = DIESEL_PREF;
			break;
		case R.id.lpg_cb:
			prefStr = LPG_PREF;
			break;
		case R.id.ethanol_cb:
			prefStr = ETHANOL_PREF;
			break;
		case R.id.one_free_place_cb:
			prefStr = ONEFREESPOT_PREF;
			break;
		}

		this.mEditor.putBoolean(prefStr, checked);
		this.mEditor.commit();
	}

	// Seekbars listeners
	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		String prefStr = "";
		switch (seekBar.getId()) {
		case R.id.total_places_sb:
			prefStr = TOTALPLACES_PREF;
			this.mTotalPlacesTV.setText("" + seekBar.getProgress());
			break;
		case R.id.free_places_sb:
			prefStr = FREEPLACES_PREF;
			this.mFreePlacesTV.setText("" + seekBar.getProgress());
			break;
		case R.id.price_sb:
			prefStr = PRICE_PREF;
			this.mPriceTV.setText("" + seekBar.getProgress());
			break;
		}

		this.mEditor.putInt(prefStr, seekBar.getProgress());
		this.mEditor.commit();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// Nothing to do here !
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// Nothing to do here !
	}

	// Buttons listener
	@Override
	public void onClick(View v) {
		// Envoyer vers une liste avec les infos demandées ?

		switch (v.getId()) {
		case R.id.radius_minus:
			int radius_min = Integer.parseInt(this.mRadius.getText()
					.toString()) - 1;
			this.mRadius.setText("" + radius_min);
			this.mEditor.putInt(RADIUS_PREF, radius_min);
			break;
		case R.id.radius_plus:
			int radius_plus = Integer.parseInt(this.mRadius.getText()
					.toString()) + 1;
			this.mRadius.setText("" + radius_plus);
			this.mEditor.putInt(RADIUS_PREF, radius_plus);
			break;
		}
		this.mEditor.commit();
	}

	// Spinner listener
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		this.mEditor.putString(SORT_PREF, "" + parent.getItemAtPosition(pos));
		this.mEditor.commit();
		// TODO faire di-rek la requete
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Nothing to do here !

	}
}
