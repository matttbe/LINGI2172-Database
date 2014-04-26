package com.charlyparkingapps.activities;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.charlyparkingapps.listeners.DrawerListListener;

public class MainActivity extends Activity {

	private ListView mMenuList;
	private DrawerLayout mDrawerLayout;
	private String[] mEntries;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEntries = getResources().getStringArray(R.array.drawer_array);
		mMenuList = (ListView) findViewById(R.id.drawer_menu_list);

		// Set the adapter for the list view
		mMenuList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mEntries));
		this.mMenuList.setOnItemClickListener(new DrawerListListener());

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

	}
	
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
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_logout:
			((CharlyApplication) getApplication()).logOut();
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
