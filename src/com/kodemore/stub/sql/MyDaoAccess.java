package com.kodemore.stub.sql;

import com.kodemore.sql.KmSqlDatabase;

/**
 * I act as the bridge between the user interface (e.g.: activities)
 * and the back end persistence layer. 
 */
public class MyDaoAccess
{
    //##################################################
    //# instance
    //##################################################

    private static MyDaoAccess _instance = new MyDaoAccess();

    public static MyDaoAccess getInstance()
    {
        return _instance;
    }

    //##################################################
    //# variables
    //##################################################

    //##################################################
    //# constructor
    //##################################################

    private MyDaoAccess()
    {

    }

    //##################################################
    //# installed
    //##################################################

    /**
     * Determines if the database is ready for use.  In order to be ready,
     * the database must already be both installed and updated with the 
     * lastest migration patches.
     */
    public boolean isReady()
    {
        return isInstalled() && isCurrent();
    }

    public boolean isInstalled()
    {
        return getDatabaseManager().exists();
    }

    public boolean isCurrent()
    {
        return getDatabaseManager().isCurrent();
    }

    public void reset()
    {
        getDatabaseManager().closeSafely();
        getDatabaseManager().drop();
    }

    //##################################################
    //# dao's
    //##################################################

    //##################################################
    //# accessing
    //##################################################

    //##################################################
    //# database
    //##################################################

    public MyDatabaseManager getDatabaseManager()
    {
        return MyDatabaseManager.getInstance();
    }

    public KmSqlDatabase getDatabase()
    {
        return getDatabaseManager().getDatabase();
    }
}
