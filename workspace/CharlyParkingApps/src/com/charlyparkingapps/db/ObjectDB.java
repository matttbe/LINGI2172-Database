package com.charlyparkingapps.db;

import java.util.List;

import android.database.Cursor;

import com.charlyparkingapps.db.object.Model;

public interface ObjectDB {

	public List<Object> getAll();

	public Object getById(int id);

	public void save(Model entite);

	public void update(Model entite);

	public void delete(int id);

	String getTablename();

	String getRequete();

	boolean checkConstraint(Model entite);

	Model getObject();

	public List<Object> convertCursorToListObject(Cursor c);

}
