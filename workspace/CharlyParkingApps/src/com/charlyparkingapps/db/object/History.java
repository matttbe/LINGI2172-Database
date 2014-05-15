package com.charlyparkingapps.db.object;

import java.util.Date;

import android.database.Cursor;

public class History implements Model {
	// CREATE TABLE "History" ("user" INTEGER PRIMARY KEY NOT NULL ,"start"
	// DATETIME NOT NULL DEFAULT (null) ,"end" DATETIME NOT NULL DEFAULT (null)
	// ,"parking" INTEGER NOT NULL );

	private int historyId;
	private int userId;
	private Date start;
	private Date end;
	private int parkingId;

	public History(int userIdParam, Date startParam, Date endParam,
			int parkingIdParam) {
		this.setUserId(userIdParam);
		this.setStart(startParam);
		this.setEnd(endParam);
		this.setParking_id(parkingIdParam);
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

}
