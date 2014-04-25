package com.charlyparkingapps.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CharlyAppHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 314;
 
    private static final String BASE_NAME = "charly.db";
    
    private static final ObjectDB[] ALL_OBJECTS= new ObjectDB[]{new UserDB()};
 
    public CharlyAppHelper(Context context, CursorFactory factory) {
        super(context, BASE_NAME, factory, DATABASE_VERSION);
    }
 
    /**
     * Création de la base
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    	for(ObjectDB object : ALL_OBJECTS){
    		db.execSQL(object.getRequete());
    	}
    }
 
    /**
     * Mise à jour de la base
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > DATABASE_VERSION) {
        	for(ObjectDB object : ALL_OBJECTS){
        		db.execSQL("DROP TABLE " + object.getTablename() + ";");
        	}
            onCreate(db);            
        }
    }
}
