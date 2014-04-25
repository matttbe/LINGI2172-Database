package com.charlyparkingapps.db;

import java.util.List;

public interface ObjectDB {

	public List<Object> GetAll();

	public Object GetById(int id);

	public void Save(Object entite);

	public void Update(Object entite);

	public void Delete(int id);

	String getTablename();

	String getRequete();

	boolean checkConstraint();

	Object getObject();

}
