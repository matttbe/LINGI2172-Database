package com.charlyparkingapps.db;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlyparkingapps.db.object.Model;

public interface ObjectDB {

	public List<Model> getAll();

	public Object getById(int id);

	public void save(Model entite);

	public void update(Model entite);

	public void delete(int id);

	public String getTablename();

	public String getRequete();

	public void populate(SQLiteDatabase db);

	public boolean checkConstraint(Model entite);

	public List<Model> convertCursorToListObject(Cursor c);

	public String getUniqueColumn();

	public String[] getAllColumns();

	public Model createFromCursor(Cursor cursor, Context context);

}
