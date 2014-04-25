package com.charlyparkingapps;

import com.charlyparkingapps.db.UserDB;
import com.charlyparkingapps.db.object.User;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CharlyApplication extends Application{

    private User current_user;
    
    @Override
	public void onCreate() {
    	
    }

	public User getCurrent_user() {
		return current_user;
	}

	public void setCurrent_user(User current_user) {
		this.current_user = current_user;
	}
	
	public boolean testLogIn(){
		SharedPreferences sharedPref = 	this.getSharedPreferences("user", Context.MODE_PRIVATE);
		int id = sharedPref.getInt(getString(R.string.id_user_pref), 0);
		if(id!=0){
			UserDB user = new UserDB(getApplicationContext());
			user.Open();
			((CharlyApplication) getApplicationContext()).setCurrent_user(user.GetById(id));
			user.Close();
			return true;
		}
		return false;
	}
    

   
}