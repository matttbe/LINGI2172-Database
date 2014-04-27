package com.charlyparkingapps.db.object;

import android.content.Context;
import android.database.Cursor;

public interface Model {

	public String[] getAllColumns();

	public String getByInt(int i);

	public String getUniqueColumn();

	Model createFromCursor(Cursor c, Context context);

}
