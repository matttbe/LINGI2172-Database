package com.charlyparkingapps.db.object;

import java.util.List;

import android.database.Cursor;

import com.charlyparkingapps.db.CarDB;

public class User implements Model {

	private int id;
	private String username;
	private String password;
	private UserType type;
	private int my_favorite_carID;

	private Car my_favorite_car = null;

	private List<Model> allCars;

	public User(String usernameParam, UserType typeParam, String passwordParam,
			int myfavoritecarParam) {
		this.setUsername(usernameParam);
		this.setType(typeParam);
		this.setPassword(passwordParam);
		this.setMy_favorite_carID(myfavoritecarParam);
	}

	public User(Cursor c) {
		createFromCursor(c);
	}

	public Model createFromCursor(Cursor c) {
		this.setId(c.getInt(0));
		this.setUsername (c.getString (1));
		this.setType (UserType.values ()[c.getInt (2)]);
		this.setPassword (c.getString (3));
		this.setMy_favorite_carID(c.getInt(4));

		return this;
	}

	public String getByInt(int i) {
		switch (i) {
		case 0:
			return String.valueOf(getId());
		case 1:
			return getUsername();
		case 2:
			return String.valueOf(getType().ordinal());
		case 3:
			return String.valueOf(getPassword());
		case 4:
			return String.valueOf(getMy_favorite_carID());

		}
		return String.valueOf(getId());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public enum UserType {
		USER, OWNER, ADMIN;
	}

	public void loadCars() {
		CarDB p = CarDB.getInstance();
		p.open(false);
		this.allCars = p.getAllCars(this.id);
		p.close();
	}

	public List<Model> getCars() {
		if (this.allCars == null) {
			loadCars();
		}
		return this.allCars;
	}

	public void loadFavoriteCar() {
		CarDB c = CarDB.getInstance();
		c.open(false);
		Car car = (Car) c.getById(this.getMy_favorite_carID());
		this.setMy_favorite_car(car);
		c.close();
	}

	public Car getMy_favorite_car() {
		if (my_favorite_carID == 0) {
			return null;
		} else {
			if (this.my_favorite_car == null) {
				loadFavoriteCar();
			}
			return this.my_favorite_car;
		}
	}

	public void setMy_favorite_car(Car my_favorite_car) {
		this.my_favorite_car = my_favorite_car;
		this.my_favorite_carID = this.my_favorite_car.getCarId();
	}

	public int getMy_favorite_carID() {
		return my_favorite_carID;
	}

	public void setMy_favorite_carID(int my_favorite_car) {
		this.my_favorite_carID = my_favorite_car;
	}
}
