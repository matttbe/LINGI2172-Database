package com.charlyparkingapps.db.object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.charlyparkingapps.db.ParkingDB;
import com.charlyparkingapps.db.UserDB;

public class History implements Model {

	private int historyId;
	private int userId;
	private Date start;
	private Date end;
	private int parkingId;

	private User user;
	private Parking parking;

	@SuppressLint("SimpleDateFormat")
	DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public History(int userIdParam, Date startParam, Date endParam,
			int parkingIdParam) {
		this.setUserId(userIdParam);
		this.setStart(startParam);
		this.setEnd(endParam);
		this.setParkingId(parkingIdParam);
	}

	public History(Cursor cursor) {
		createFromCursor(cursor);
	}

	@Override
	public String getByInt(int i) {
		switch (i) {
		case 1:
			return String.valueOf(this.getHistoryId());
		case 2:
			return String.valueOf(this.getUserId());
		case 3:
			return this.getStart().toString();
		case 4:
			return this.getEnd().toString();
		case 5:
			return String.valueOf(this.getParkingId());
		default:
			return String.valueOf(this.getHistoryId());

		}
	}

	@Override
	public Model createFromCursor(Cursor c) {
		this.setHistoryId(c.getInt(0));
		this.setUserId(c.getInt(1));

		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = iso8601Format.parse(c.getString(2));
		} catch (ParseException e) {
			Log.e("History", "Error Parsing ISO8601", e);
		} finally {
			this.setStart(dateStart);
			this.setEnd(dateEnd);
		}
		this.setParkingId(c.getInt(4));
		return this;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
		this.setParkingId(parking.getParkingId());
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

}
