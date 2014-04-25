package com.charlyparkingapps.activities;
 
 import com.charlyparkingapps.CharlyApplication;
import com.charlyparkingapps.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
 
 public class MainActivity extends Activity {
 
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
     }
 
 
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.main, menu);
         //menu.a
         return true;
     }
     
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle item selection
         switch (item.getItemId()) {
             case R.id.action_logout:
            	 ((CharlyApplication) getApplication()).logOut();
            	 Intent i = new Intent(this, LoginActivity.class);
         		 startActivity(i);
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
     }
     
 }
