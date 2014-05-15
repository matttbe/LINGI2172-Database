package com.charlyparkingapps.db.object;

import java.util.Date;

import android.database.Cursor;

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

	public History(int userIdParam, Date startParam, Date endParam,
			int parkingIdParam) {
		this.setUserId(userIdParam);
		this.setStart(startParam);
		this.setEnd(endParam);
		this.setParking_id(parkingIdParam);
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
		this.setStart(new Date(c.getString(2)));
		this.setEnd(new Date(c.getString(3)));
		this.setParking_id(c.getInt(4));
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

	public void setParking_id(int parking_id) {
		this.parkingId = parking_id;
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
		this.setParking_id(parking.getParkingId());
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
		this.setParking_id(parking.getParkingId());
	}

}
