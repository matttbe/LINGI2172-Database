package com.charlyparkingapps.db.object;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.charlyparkingapps.db.ObjectDB;

public abstract class ObjectRepository implements ObjectDB {

	protected SQLiteDatabase maBDD;

	protected SQLiteOpenHelper sqLiteOpenHelper;

	public void Open() {
		maBDD = sqLiteOpenHelper.getWritableDatabase();
	}

	public void Close() {
		maBDD.close();
	}

}
