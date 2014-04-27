package com.charlyparkingapps.db;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CharlyAppHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 314;

	private static final String BASE_NAME = "charly.db";

	private static List<ObjectDB> allObjects = new LinkedList<ObjectDB>();

	public CharlyAppHelper(Context context, CursorFactory factory) {
		super(context, BASE_NAME, factory, DATABASE_VERSION);
		allObjects.add(new UserDB());
		allObjects.add(new ParkingDB());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (ObjectDB object : allObjects) {
			db.execSQL(object.getRequete());
			object.populate(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > DATABASE_VERSION) {
			for (ObjectDB object : allObjects) {
				db.execSQL("DROP TABLE " + object.getTablename() + ";");
			}
			onCreate(db);
		}
	}
}
