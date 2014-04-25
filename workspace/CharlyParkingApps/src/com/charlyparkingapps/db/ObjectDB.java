package com.charlyparkingapps.db;

import java.util.List;

import android.database.Cursor;

public interface ObjectDB {
		 
	    public List GetAll();
	    public Object GetById(int id);
	 
	    public void Save(Object entite);
	    public void Update(Object entite);
	    public void Delete(int id);
	
	String getTablename();
	
	String getRequete();
	
	boolean checkConstraint();
	
	Object getObject();

}
