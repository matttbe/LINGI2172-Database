package com.charlyparkingapps.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.object.User;


public class EditProfileActivity extends Activity
{

	// Menu
	private ListView mMenuList;
	private DrawerLayout mDrawerLayout;
	private String[] mEntries;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_edit_profile);
		// Show the Up button in the action bar.
		setupActionBar ();
		User u = ((CharlyApplication) this.getApplication ()).getCurrentUser ();

		EditText et = (EditText) findViewById (R.id.edit_username);
		et.setText (u.getUsername ());


		initMenu ();
	}

	private void initMenu ()
	{

		mDrawerLayout = (DrawerLayout) findViewById (R.id.drawer_menu_layout);
		mDrawerToggle = new ActionBarDrawerToggle (this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer_dark, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		)
		{

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed (View view)
			{
				super.onDrawerClosed (view);
				getActionBar ().setTitle (R.string.app_name);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened (View drawerView)
			{
				super.onDrawerOpened (drawerView);
				getActionBar ().setTitle (R.string.menu);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener (mDrawerToggle);
		mActionBar = getActionBar ();
		mActionBar.setDisplayHomeAsUpEnabled (true);
		mActionBar.setHomeButtonEnabled (true);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar ()
	{

		getActionBar ().setDisplayHomeAsUpEnabled (true);

	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.edit_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId ())
		{
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask (this);
				return true;
		}
		return super.onOptionsItemSelected (item);
	}

}
