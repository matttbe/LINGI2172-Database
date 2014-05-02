package com.charlyparkingapps.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CharlyAppHelper extends SQLiteOpenHelper {

	// Singleton stuff
	private static CharlyAppHelper sInstance;
	public static CharlyAppHelper getInstance() { return sInstance; }
	public static void init(Context context, List<ObjectDB> allObjects) {
		sInstance = new CharlyAppHelper(context, allObjects);
	}


	private static final int DATABASE_VERSION = 316;

	private static final String BASE_NAME = "charly.db";

	private List<ObjectDB> allObjects;

	public CharlyAppHelper(Context context, List<ObjectDB> allObjects) {
		super(context, BASE_NAME, null, DATABASE_VERSION);
		this.allObjects = allObjects;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (ObjectDB object : allObjects) {
			db.execSQL(object.getCreateRequest());
			object.populate();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (ObjectDB object : allObjects) {
			object.upgrade(db, oldVersion, newVersion);
		}
	}
}
