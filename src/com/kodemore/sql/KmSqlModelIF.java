package com.kodemore.sql;

import android.content.ContentValues;
import android.database.Cursor;

public interface KmSqlModelIF
{
    void applyColumn(Cursor c, String columnName);
    ContentValues getContentValues();
}
