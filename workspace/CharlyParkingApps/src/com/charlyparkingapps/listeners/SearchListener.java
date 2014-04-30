package com.charlyparkingapps.listeners;

import java.util.Locale;

import com.charlyparkingapps.activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.widget.SearchView;


public class SearchListener implements SearchView.OnQueryTextListener {
	
	private Context mContext;
	
	public SearchListener(Context context) {
		this.mContext = context;
	}

    @Override
    public boolean onQueryTextSubmit(String query) {
    	try {

            Geocoder geocoder = new Geocoder(this.mContext);
            Address address = geocoder.getFromLocationName(query, 1).get(0);
            LatLng loc = new LatLng(address.getLatitude(), address.getLongitude());
            ((MainActivity) this.mContext).centerMap(loc);
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    	
    	return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

