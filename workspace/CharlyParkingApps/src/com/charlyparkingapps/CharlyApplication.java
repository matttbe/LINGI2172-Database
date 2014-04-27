package com.charlyparkingapps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.User;

public class CharlyApplication extends Application {

	private User currentUser;

	@Override
	public void onCreate() {

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void logOut() {
		SharedPreferences sharedPref = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.id_user_pref), 0);
		editor.apply();
		setCurrentUser(null);

	}

	public boolean testLogIn() {
		SharedPreferences sharedPref = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		int id = sharedPref.getInt(getString(R.string.id_user_pref), 0);
		if (id != 0) {
			UserDB user = new UserDB(getApplicationContext());
			user.open();
			((CharlyApplication) getApplicationContext())
					.setCurrentUser((User) user.getById(id));
			user.close();
			return true;
		}
		return false;
	}

}
