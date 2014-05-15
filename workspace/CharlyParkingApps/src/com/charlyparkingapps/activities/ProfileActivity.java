package com.charlyparkingapps.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.object.User;
import com.charlyparkingapps.db.object.User.UserType;

public class ProfileActivity extends Activity {

	public static final String KEY_USER = "userID";

	// Menu
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;

	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_profile);
		// Show the Up button in the action bar.
		setupActionBar();

		mUser = ((CharlyApplication) this.getApplication()).getCurrentUser();
		TextView tv = (TextView) findViewById (R.id.username);
		tv.setText(mUser.getUsername());

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
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.profile, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent i;
		switch (item.getItemId()) {
			case R.id.action_logout:
				((CharlyApplication) getApplication ()).logOut ();
				i = new Intent (this, LoginActivity.class);
				startActivity (i);
				return true;

			case R.id.edit_profile:
				i = new Intent (this, EditProfileActivity.class);
				startActivity (i);
				return true;
				
			case R.id.become_manager:
			Toast toast;
			if (this.mUser.getType() == UserType.USER) {
				this.mUser.setType(UserType.ADMIN);
				toast = Toast.makeText(this,
						getString(R.string.you_are_manager), Toast.LENGTH_LONG);
			} else {
				toast = Toast.makeText(this,
						getString(R.string.already_manager), Toast.LENGTH_LONG);
			}
			toast.show();
			return true;

			default:
				return super.onOptionsItemSelected (item);
		}
	}

}
