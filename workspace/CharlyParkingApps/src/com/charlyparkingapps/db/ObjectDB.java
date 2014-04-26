package com.charlyparkingapps.db;

import java.util.List;

import android.database.Cursor;

import com.charlyparkingapps.db.object.Model;

public interface ObjectDB {

	public List<Object> GetAll();

	public Object GetById(int id);

	public void Save(Object entite);

	public void Update(Object entite);

	public void Delete(int id);

	String getTablename();

	String getRequete();

	boolean checkConstraint();

	Model getObject();
	
	public List<Object> ConvertCursorToListObject(Cursor c);

}
