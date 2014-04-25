package com.charlyparkingapps;

import com.charlyparkingapps.db.object.User;

import android.app.Application;

public class CharlyApplication extends Application{

    private User current_user;

	public User getCurrent_user() {
		return current_user;
	}

	public void setCurrent_user(User current_user) {
		this.current_user = current_user;
	}
    

   
}