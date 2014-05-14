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

	// Spinner
	private Spinner mSortSpinner;

	// TextViews
	private TextView mTotalPlacesTV;
	private TextView mFreePlacesTV;
	private TextView mPriceTV;

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
		this.mHandicapedCB = (CheckBox) findViewById(R.id.handicaped_cb);
		this.mHandicapedCB.setOnCheckedChangeListener(this);
		this.mFuelCB = (CheckBox) findViewById(R.id.fuel_cb);
		this.mFuelCB.setOnCheckedChangeListener(this);
		this.mDieselCB = (CheckBox) findViewById(R.id.diesel_cb);
		this.mDieselCB.setOnCheckedChangeListener(this);
		this.mLPGCB = (CheckBox) findViewById(R.id.lpg_cb);
		this.mLPGCB.setOnCheckedChangeListener(this);
		this.mEthanolCB = (CheckBox) findViewById(R.id.ethanol_cb);
		this.mEthanolCB.setOnCheckedChangeListener(this);
		this.mOneFreeCB = (CheckBox) findViewById(R.id.one_free_place_cb);
		this.mOneFreeCB.setOnCheckedChangeListener(this);

		// SeekBars
		this.mTotalPlacesSB = (SeekBar) findViewById(R.id.total_places_sb);
		this.mTotalPlacesSB.setOnSeekBarChangeListener(this);
		this.mFreePlacesSB = (SeekBar) findViewById(R.id.free_places_sb);
		this.mFreePlacesSB.setOnSeekBarChangeListener(this);
		this.mPriceSB = (SeekBar) findViewById(R.id.price_sb);
		this.mPriceSB.setOnSeekBarChangeListener(this);

		// Buttons
		this.mFavoriteButton = (Button) findViewById(R.id.favorites_button);
		this.mFavoriteButton.setOnClickListener(this);
		this.mUsingButton = (Button) findViewById(R.id.using_button);
		this.mUsingButton.setOnClickListener(this);
		this.mMineButton = (Button) findViewById(R.id.mine_button);
		this.mMineButton.setOnClickListener(this);

		// Spinner
		this.mSortSpinner = (Spinner) findViewById(R.id.sort_spinner);
		this.mSortSpinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.sort_spinner_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mSortSpinner.setAdapter(adapter);

		// TextViews
		this.mTotalPlacesTV = (TextView) findViewById(R.id.total_places_tv);
		this.mTotalPlacesTV.setText("" + this.mTotalPlacesSB.getProgress());
		this.mFreePlacesTV = (TextView) findViewById(R.id.free_places_tv);
		this.mFreePlacesTV.setText("" + this.mFreePlacesSB.getProgress());
		this.mPriceTV = (TextView) findViewById(R.id.price_tv);
		this.mPriceTV.setText("" + this.mPriceSB.getProgress());
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
		// TODO faire di-rek la requete
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
		// TODO faire di-rek la requete
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
		// TODO Auto-generated method stub
		// Envoyer vers une liste avec les infos demandées ?

	}

	// Spinner listener
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		this.mEditor.putString(SORT_PREF, "" + parent.getItemAtPosition(pos));
		// TODO, faire un enum ou pas ?
		this.mEditor.commit();
		// TODO faire di-rek la requete
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Nothing to do here !

	}
}
