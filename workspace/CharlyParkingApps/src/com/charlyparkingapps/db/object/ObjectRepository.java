package com.charlyparkingapps.db.object;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.charlyparkingapps.db.ObjectDB;

public abstract class ObjectRepository implements ObjectDB {

	protected SQLiteDatabase maBDD;

	protected SQLiteOpenHelper sqLiteOpenHelper;
	
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void Open() {
		maBDD = sqLiteOpenHelper.getWritableDatabase();
	}

	public void Close() {
		maBDD.close();
	}
	
	public List<Object> GetAll() {
		Cursor cursor = maBDD.query(getTablename(), this.getObject().getAll_Columns(), null,
				null, null, null, null);

		return ConvertCursorToListObject(cursor);
	}
	
	public Model GetById(int id) {
		Cursor cursor = maBDD.query(getTablename(), this.getObject().getAll_Columns(),
				this.getObject().getUniqueColumn() + "=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor.moveToFirst())
			return this.getObject().createFromCursor(cursor, context);
		else
			return null;
	}	
	
	@Override
	public void Save(Model entite) {
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < entite.getAll_Columns().length; i++) {
			contentValues.put(entite.getAll_Columns()[i], entite.getByInt(i));
		}
		maBDD.insert(getTablename(), null, contentValues);
	}
	
	@Override
	public void Update(Model entite) {
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < entite.getAll_Columns().length; i++) {
			contentValues.put(entite.getAll_Columns()[i], entite.getByInt(i));
		}

		maBDD.update(getTablename(), contentValues, entite.getUniqueColumn() + "=?",
				new String[] { String.valueOf(entite.getByInt(0)) });

	}

	@Override
	public void Delete(int id) {
		maBDD.delete(getTablename(), this.getObject().getAll_Columns()[0] + "=?",
				new String[] { String.valueOf(id) });
	}

}
