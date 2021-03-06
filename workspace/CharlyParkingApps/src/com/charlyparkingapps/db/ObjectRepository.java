package com.charlyparkingapps.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.charlyparkingapps.db.object.Model;

public abstract class ObjectRepository implements ObjectDB {

	protected SQLiteDatabase myBDD;

	protected SQLiteOpenHelper sqLiteOpenHelper;

	private Context context;

	public ObjectRepository(Context context) {
		this.context = context;
		sqLiteOpenHelper = CharlyAppHelper.getInstance();
	}

	public Context getContext() {
		return context;
	}
	
	public void open(boolean write) {
		if(write)
			myBDD = sqLiteOpenHelper.getWritableDatabase();
		else
			myBDD = sqLiteOpenHelper.getReadableDatabase();
	}

	public void close() {
		myBDD.close();
	}

	public List<Model> getAll() {
		Cursor cursor = myBDD.query(getTablename(), getAllColumns(), null,
				null, null, null, null);

		return convertCursorToListObject(cursor);
	}

	public Model getById(int id) {
		Cursor cursor = myBDD.query(getTablename(), getAllColumns(),
				getUniqueColumn() + "=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor.moveToFirst())
			return createFromCursor(cursor);
		else
			return null;
	}

	@Override
	public long save(Model entite) {
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < getAllColumns().length; i++) {
			contentValues.put(getAllColumns()[i], entite.getByInt(i));
		}
		return myBDD.insert(getTablename(), null, contentValues);
	}

	@Override
	public void update(Model entite) {
		ContentValues contentValues = new ContentValues();
		for (int i = 1; i < getAllColumns().length; i++) {
			contentValues.put(getAllColumns()[i], entite.getByInt(i));
		}

		myBDD.update(getTablename(), contentValues, getUniqueColumn()
				+ "=?", new String[] { String.valueOf(entite.getByInt(0)) });

	}

	@Override
	public void delete(int id) {
		myBDD.delete(getTablename(), getAllColumns()[0]
				+ "=?", new String[] { String.valueOf(id) });
	}
	
	

}
