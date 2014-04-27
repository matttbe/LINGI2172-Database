package com.charlyparkingapps.db.object;

import android.content.Context;
import android.database.Cursor;

public interface Model {

	public String getByInt(int i);

	Model createFromCursor(Cursor c, Context context);

}
