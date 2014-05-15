package com.charlyparkingapps.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.charlyparkingapps.activities.CarsActivity;
import com.charlyparkingapps.activities.FiltersActivity;
import com.charlyparkingapps.activities.HistoryActivity;
import com.charlyparkingapps.activities.ProfileActivity;

public class DrawerListListener implements ListView.OnItemClickListener {

	private Context mContext;

	public DrawerListListener(Context context) {
		this.mContext = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i;
		switch (position) {
		case 0:
			i = new Intent(mContext, FiltersActivity.class);
			mContext.startActivity(i);
			break;
		case 1:
			i = new Intent(mContext, ProfileActivity.class);
			mContext.startActivity(i);
			break;
		case 2:
			i = new Intent(mContext, CarsActivity.class);
			mContext.startActivity(i);
			break;
			case 3:
				i = new Intent (mContext, HistoryActivity.class);
				mContext.startActivity (i);
			break;
		default:
			break;
		}
	}

}
