package com.charlyparkingapps.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;
import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.User;


public class EditProfileActivity extends Activity
{

	// Menu
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;

	// Variables
	User u;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_edit_profile);
		// Show the Up button in the action bar.
		setupActionBar ();
		u = ((CharlyApplication) this.getApplication ()).getCurrentUser ();

		EditText et = (EditText) findViewById (R.id.edit_username);
		et.setText (u.getUsername ());
		findViewById (R.id.confirm_profile_edit_button).setOnClickListener (
				new View.OnClickListener ()
				{
					@Override
					public void onClick (View view)
					{
						editAction ();
					}
				});

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

	private boolean checkFields ()
	{
		EditText username = (EditText) findViewById (R.id.edit_username);
		EditText oldpass = (EditText) findViewById (R.id.old_password);
		EditText newpass = (EditText) findViewById (R.id.new_password);
		EditText confpass = (EditText) findViewById (R.id.new_password_confirm);
		if (!username.getText ().toString ().equals (u.getUsername ()))
		{
			u.setUsername (username.getText ().toString ());
		}

		if (!(oldpass.getText ().toString ().equals ("") && newpass.getText ()
				.toString ().equals ("")))
		{
			if (!(oldpass.getText ().toString ().equals (u.getPassword ())))
			{
				Toast.makeText (this, R.string.error_incorrect_password,
						Toast.LENGTH_LONG).show ();
				return false;
			}
			else
			{
				if (newpass.getText ().toString ()
						.equals (confpass.getText ().toString ()))
				{
					if (newpass.getText ().toString ().length () < 4)
					{
						Toast.makeText (this, R.string.error_invalid_password,
								Toast.LENGTH_LONG).show ();
						return false;
					}
					else
					{
						u.setPassword (newpass.getText ().toString ());
					}

				}
				else
				{
					Toast.makeText (this, R.string.error_matching,
							Toast.LENGTH_LONG).show ();
					return false;
				}
			}
		}

		return true;
	}

	private void editAction ()
	{
		if (checkFields ())
		{
			UserDB udb = UserDB.getInstance ();
			udb.open (true);
			udb.update (u);
			udb.close ();
			Intent i = new Intent(this,ProfileActivity.class);
			startActivity (i);
			return;
		}
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
