package com.charlyparkingapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.charlyparkingapps.db.object.ObjectRepository;
import com.charlyparkingapps.db.object.User;

public class UserDB extends ObjectRepository {
	
	private User user;
	
	public UserDB(Context context) {
        sqLiteOpenHelper = new CharlyAppHelper(context, null);
    }
	
	public UserDB(){
		
	}
	
	@Override
	public String getTablename() {
		return "User";
	}

	@Override
	public String getRequete() {
		return "CREATE TABLE \"User\"(userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, type INTEGER NOT NULL "
				+ "CONSTRAINT chk_type CHECK (type IN (0, 1, 2)), "
				+ "username varchar(20) NOT NULL UNIQUE, password varchar(20) NOT NULL)";
	}

	@Override
	public boolean checkConstraint() {
		return user.getType()<3 && user.getType()>=0;
	}

	@Override
	public Object getObject() {
		return this.user;
	}
	
	
	public List GetAll(){
        Cursor cursor = maBDD.query(getTablename(),
                User.ALL_COLUMNS, null, null, null,
                null, null);
 
        return ConvertCursorToListObject(cursor);
	}
	
	
	public boolean login(String mEmail, String password) {
        Cursor cursor = maBDD.query(getTablename(),
                User.ALL_COLUMNS,
                "username =? AND "+"password =?",
                new String[] { String.valueOf(mEmail), password }, null, null, null);
 
        return cursor.moveToFirst();
    }
	
	
	public User GetById(int id) {
        Cursor cursor = maBDD.query(getTablename(),
                User.ALL_COLUMNS,
                User.ALL_COLUMNS[0] + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
 
        if(cursor.moveToFirst())
        	return new User(cursor);
        else
        	return null;
    }
	
	public User GetByUsername(String username) {
		Cursor cursor = maBDD.query(getTablename(),
                User.ALL_COLUMNS,
                "username=?",
                new String[] { username }, null, null, null);
 
        if(cursor.moveToFirst())
        	return new User(cursor);
        else
        	return null;
		
	}
 
    @Override
    public void Save(Object entite) {
    	User user=(User) entite;
        ContentValues contentValues = new ContentValues();
        for(int i = 1; i < User.ALL_COLUMNS.length; i++){
        	contentValues.put( User.ALL_COLUMNS[i], user.getByInt(i));
        }
        Log.i("charlyLog",contentValues.toString());
        maBDD.insert(getTablename(), null, contentValues);
    }
 
    @Override
    public void Update(Object entite) {
    	User user=(User) entite;
        ContentValues contentValues = new ContentValues();
        for(int i = 1; i< User.ALL_COLUMNS.length; i++){
        	contentValues.put( User.ALL_COLUMNS[1], user.getByInt(1));
        }
 
        maBDD.update(getTablename(), contentValues,
        		User.ALL_COLUMNS[0] + "=?",
                new String[] { String.valueOf(user.getId()) });
    }
 
    @Override
    public void Delete(int id) {
        maBDD.delete(getTablename(),
        		User.ALL_COLUMNS[0] + "=?",
                new String[] { String.valueOf(id) });
    }
    
    
    public List ConvertCursorToListObject(Cursor c) {
        List liste = new ArrayList();
        if (c.getCount() == 0)
            return liste;
        c.moveToFirst();

        do {
 
            User user = new User(c);
 
            liste.add(user);
        } while (c.moveToNext());
 
        c.close();
 
        return liste;
    }

}
