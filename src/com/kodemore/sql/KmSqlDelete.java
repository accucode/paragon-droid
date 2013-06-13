package com.kodemore.sql;

/**
 * I provide a simple wrapper for ad hoc updates.  This is 
 * primarily intended for use when updating specific columns
 * for multiple records.
 * 
 * If you are simply updating an entire row (model), it is 
 * usually much simpler to use KmSqlDatabase.upsert(myModel).
 */
public class KmSqlDelete
{
    //##################################################
    //# variables
    //##################################################

    private KmSqlDatabase     _database;
    private String            _table;
    private KmSqlAndCondition _where;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlDelete(KmSqlDatabase db)
    {
        _database = db;
        _where = new KmSqlAndCondition();
    }

    //##################################################
    //# accessing
    //##################################################

    public void setTable(String e)
    {
        _table = e;
    }

    public String getTable()
    {
        return _table;
    }

    public KmSqlAndCondition where()
    {
        return _where;
    }

    /**
     * Execute the delete, returning the number of modified rows. 
     */
    public int execute()
    {
        String[] whereArgs = null;

        return _database._getInner().delete(getTable(), _where.toString(), whereArgs);
    }
}
