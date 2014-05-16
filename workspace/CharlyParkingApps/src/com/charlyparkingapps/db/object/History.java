package com.charlyparkingapps.db.object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.charlyparkingapps.db.CarDB;
import com.charlyparkingapps.db.HourlyRateDB;
import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.UserDB;

public class History implements Model {

	private int historyId;
	private int carId;
	private Date start;
	private Date end;
	private int parkingId;
	private int userId;
	private int feedback;

	private Car car;
	private Parking parking;
	private User user;

	@SuppressLint("SimpleDateFormat")
	DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public History(int carIdParam, Date startParam, Date endParam,
			int parkingIdParam, int UserIdParam, int feedback)
	{
		this.setCarId(carIdParam);
		this.setStart(startParam);
		this.setEnd(endParam);
		this.setParkingId(parkingIdParam);
		this.setUserId(UserIdParam);
		this.setFeedback (feedback);
	}

	public int getFeedback ()
	{
		return feedback;
	}

	public void setFeedback (int feedback)
	{
		this.feedback = feedback;
	}

	public History(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
			case 0:
				return String.valueOf (this.getHistoryId ());
			case 1:
				return String.valueOf (this.getCarId ());
			case 2:
				return iso8601Format.format (this.getStart ())
						.toString ();
			case 3:
				if (this.getEnd () == null)
				{
					return null;
				}
				return iso8601Format.format (this.getEnd ())
						.toString ();
			case 4:
				return String.valueOf (this.getParkingId ());
			case 5:
				return String.valueOf (this.getUserId ());
			case 6:
				return String.valueOf (this.getFeedback ());
			default:
				return String.valueOf (this.getHistoryId ());

		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setHistoryId(c.getInt(0));
		this.setCarId(c.getInt(1));

		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = iso8601Format.parse(c.getString(2));
			if (!c.isNull(3)) {
				dateEnd = iso8601Format.parse(c.getString(3));
			}
		} catch (ParseException e) {
			Log.e("History", "Error Parsing ISO8601", e);
		} finally {
			this.setStart(dateStart);
			this.setEnd(dateEnd);
		}
		this.setParkingId(c.getInt(4));
		this.setUserId(c.getInt(5));
		this.setFeedback (c.getInt (6));
		return this;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getParkingId() {
		return parkingId;
	}

	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}

	public void loadCar() {
		CarDB u = CarDB.getInstance();
		u.open(false);
		this.setCar((Car) u.getById(this.getCarId()));
		u.close();
	}

	public Car getCar() {
		if (car == null) {
			loadCar();
		}
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
		this.setCarId(car.getCarId());
	}

	public void loadParking() {
		ParkingDB p = ParkingDB.getInstance();
		p.open(false);
		this.setParking((Parking) p.getById(this.getParkingId()));
		p.close();
	}

	public Parking getParking() {
		if (parking == null) {
			loadParking();
		}
		return this.parking;

	}

	public void setParking(Parking parking) {
		this.parking = parking;
		this.setParkingId(parking.getParkingId());
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void loadUser() {
		UserDB u = UserDB.getInstance();
		u.open(false);
		this.setUser((User) u.getById(this.getUserId()));
		u.close();
	}

	public User getUser() {
		if (user == null) {
			loadUser();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.setUserId(user.getId());
	}

	/*
	 * c'est un peu bourrin
	 */
	public float getPrice(History history) {
		float price = 0;
		Parking p = history.getParking();
		long time = history.getEnd().getTime() - history.getStart().getTime();
		HourlyRateDB u = HourlyRateDB.getInstance();
		u.open(false);
		List<Model> allH = u.getAllHourlyRateForParking(p);
		int index = 0;

		while (index < allH.size() && time > 0) {
			HourlyRate h = (HourlyRate) allH.get(index);
			price = price + h.getCost();
			time = time - (h.getEnd().getTime() - h.getStart().getTime());
			index = index + 1;
		}

		// u.get
		u.close();

		return price;
	}

}
