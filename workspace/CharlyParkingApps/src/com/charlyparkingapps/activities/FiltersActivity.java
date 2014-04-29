package com.charlyparkingapps.activities;

import com.charlyparkingapps.R;
import com.charlyparkingapps.listeners.DrawerListListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FiltersActivity extends Activity 
implements 	CheckBox.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, 
						View.OnClickListener, OnItemSelectedListener {
	
	// Menu
	private ListView mMenuList;
	private DrawerLayout mDrawerLayout;
	private String[] mEntries;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	
	//Preferences
	private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
	
	//Interface objects
	//checkboxes
	private CheckBox mDefibrilatorCB;
	private CheckBox mHandicapedCB;
	private CheckBox mFuelCB;
	private CheckBox mDieselCB;
	private CheckBox mLPGCB;
	private CheckBox mEthanolCB;
	private CheckBox mOneFreeCB;
	
	//Seekbars
	private SeekBar mTotalPlacesSB;
	private SeekBar mFreePlacesSB;
	
	//Buttons
	private Button mFavoriteButton;
	private Button mUsingButton;
	private Button mMineButton;
	
	//Spinner
	private Spinner mSortSpinner;
	
	//TextViews
	private TextView mTotalPlacesTV;
	private TextView mFreePlacesTV;
	
	//Strings for preferences
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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_search);

		// For the navigation drawer
		
		mEntries = getResources().getStringArray(R.array.drawer_array);
		mMenuList = (ListView) findViewById(R.id.drawer_menu_list);

		// Set the adapter for the list view
		mMenuList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mEntries));
		this.mMenuList.setOnItemClickListener(new DrawerListListener(this));

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer_dark, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(R.string.app_name);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.menu);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		
		// End for the navigation drawer
		
		//Preferences
		this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mEditor = mPreferences.edit();
		
		// Interfaces Objects
		//Checkboxes
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
		
		//SeekBars
		this.mTotalPlacesSB = (SeekBar) findViewById(R.id.total_places_sb);
		this.mTotalPlacesSB.setOnSeekBarChangeListener(this);
		this.mFreePlacesSB = (SeekBar) findViewById(R.id.free_places_sb);
		this.mFreePlacesSB.setOnSeekBarChangeListener(this);
		
		//Buttons
		this.mFavoriteButton = (Button) findViewById(R.id.favorites_button);
		this.mFavoriteButton.setOnClickListener(this);
		this.mUsingButton = (Button) findViewById(R.id.using_button);
		this.mUsingButton.setOnClickListener(this);
		this.mMineButton = (Button) findViewById(R.id.mine_button);
		this.mMineButton.setOnClickListener(this);
		
		//Spinner 
		this.mSortSpinner = (Spinner) findViewById(R.id.sort_spinner);
		this.mSortSpinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_spinner_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mSortSpinner.setAdapter(adapter);
		
		//TextViews
		this.mTotalPlacesTV = (TextView) findViewById(R.id.total_places_tv);
		this.mTotalPlacesTV.setText(""+this.mTotalPlacesSB.getProgress());
		this.mFreePlacesTV = (TextView) findViewById(R.id.free_places_tv);
		this.mFreePlacesTV.setText(""+this.mFreePlacesSB.getProgress());
	}
	
	// ________________ LISTERNER FOR THE MENU

		@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			super.onPostCreate(savedInstanceState);
			// Sync the toggle state after onRestoreInstanceState has occurred.
			mDrawerToggle.syncState();
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			mDrawerToggle.onConfigurationChanged(newConfig);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			// menu.a
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}
			return super.onOptionsItemSelected(item);
			}

		
		//Checkboxes listerner
		@Override
		public void onCheckedChanged(CompoundButton cb, boolean checked) {
			String prefStr = "";
			switch(cb.getId()) {
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
			//TODO faire di-rek la requete
		}
		
		//Seekbars listeners
		 @Override
		    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
			 String prefStr = "";
		       switch(seekBar.getId()) {
		       		case R.id.total_places_sb:
		       			prefStr = TOTALPLACES_PREF;
		       			this.mTotalPlacesTV.setText(""+seekBar.getProgress());
		       			break;
		       		case R.id.free_places_sb:
		       			prefStr = FREEPLACES_PREF;
		       			this.mFreePlacesTV.setText(""+seekBar.getProgress());
		       			break;
		       }

		       this.mEditor.putInt(prefStr, seekBar.getProgress());
	           this.mEditor.commit();
	         //TODO faire di-rek la requete
		    }

		    @Override
		    public void onStartTrackingTouch(SeekBar seekBar) {
		        //Nothing to do here !
		    }

		    @Override
		    public void onStopTrackingTouch(SeekBar seekBar) {
		        //Nothing to do here !
		    }

		    //Buttons listener
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Envoyer vers une liste avec les infos demandées ?
				
			}

			//Spinner listener
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				this.mEditor.putString(SORT_PREF, ""+parent.getItemAtPosition(pos)); //TODO, faire un enum ou pas ?
		         this.mEditor.commit();
				//TODO faire di-rek la requete
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//Nothing to do here !
				
			}
}
