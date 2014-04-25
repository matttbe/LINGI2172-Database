package com.charlyparkingapps.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerListListener implements ListView.OnItemClickListener {
	
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
    	
    	switch(position) {
    		case 0:
    			//TODO retourner sur la vue de la map
    			break;
    		case 1:
    			//TODO faire l'action corresondante
    			break;
    		default:
    			//TODO gerer le reste
    			break;
    	}
    }
    
}

