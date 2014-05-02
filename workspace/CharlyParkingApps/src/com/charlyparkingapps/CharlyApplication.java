package com.charlyparkingapps;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.charlyparkingapps.db.AddressDB;
import com.charlyparkingapps.db.CharlyAppHelper;
import com.charlyparkingapps.db.EntriesDB;
import com.charlyparkingapps.db.ObjectDB;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.User;

public class CharlyApplication extends Application {

	private User currentUser;

	@Override
	public void onCreate() {
		Context context = getApplicationContext();
		List<ObjectDB> allObjects = new LinkedList<ObjectDB>();
		CharlyAppHelper.init(context, allObjects); // at the beginning!

		allObjects.add(UserDB.init(context));
		allObjects.add(AddressDB.init (context));
		allObjects.add(ParkingDB.init(context));
		allObjects.add(EntriesDB.init(context));
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void logIn(User currentUser) {
		this.currentUser = currentUser;
	}

	public void logOut() {
		SharedPreferences sharedPref = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.id_user_pref), 0);
		editor.apply();
		logIn(null);
	}

	/**
	 * Test if we already save the user/pass. If yes, login.
	 *
	 * @return true if we are no logged in
	 */
	public boolean testLogIn() {
		SharedPreferences sharedPref = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		int id = sharedPref.getInt(getString(R.string.id_user_pref), 0);
		if (id != 0) {
			UserDB user = UserDB.getInstance();
			user.open(false);
			User u = (User) user.getById(id);
			user.close();
			logIn(u);
			return true;
		}
		return false;
	}
}
