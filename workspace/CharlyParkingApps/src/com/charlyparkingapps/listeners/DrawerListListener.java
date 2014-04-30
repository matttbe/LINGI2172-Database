package com.charlyparkingapps.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.charlyparkingapps.activities.FiltersActivity;
import com.charlyparkingapps.activities.MainActivity;

public class DrawerListListener implements ListView.OnItemClickListener {
	
	private Context mContext;
	
	public DrawerListListener(Context context){
		this.mContext = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i;
		switch (position) {
		case 0:
			if(! (this.mContext instanceof MainActivity)) {
				i = new Intent(mContext, MainActivity.class);
				mContext.startActivity(i);
			}
			break;
		case 1:
			// TODO faire l'action corresondante
			break;
		case 2:
			//TODO
			break;
		case 3:
			if (!(this.mContext instanceof FiltersActivity)) {
				i = new Intent(mContext, FiltersActivity.class);
				mContext.startActivity(i);
			}
			break;
		default:
			// TODO gerer le reste
			break;
		}
	}

}
