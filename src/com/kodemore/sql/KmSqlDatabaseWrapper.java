package com.kodemore.sql;

import android.database.sqlite.SQLiteDatabase;

// todo_wyatt: database wrapper?
public class KmSqlDatabaseWrapper
{
    //##################################################
    //# variables
    //##################################################//

    private SQLiteDatabase _inner;

    //##################################################
    //# constructor
    //##################################################//

    public KmSqlDatabaseWrapper(SQLiteDatabase e)
    {
        _inner = e;
    }

    //##################################################
    //# accessing
    //##################################################//

    public SQLiteDatabase getInner()
    {
        return _inner;
    }

}
