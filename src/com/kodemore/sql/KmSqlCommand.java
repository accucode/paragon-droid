package com.kodemore.sql;

import com.kodemore.utility.Kmu;

/**
 * I am used to wrap database code in a transaction.
 * By default, the transaction is automatically committed.
 * The transaction is rolled back if an exception is thrown.
 */
public abstract class KmSqlCommand
{
    //##################################################
    //# run
    //##################################################

    public void run()
    {
        KmSqlDatabase db = null;
        try
        {
            db = getDatabase();
            db.begin();

            handle();

            db.commit();
        }
        catch ( KmSqlCommitException ex )
        {
            db.commit();
        }
        catch ( KmSqlRollbackException ex )
        {
            // none, rollback is implicit
        }
        finally
        {
            // rollback is implicit unless we called setTransactionSuccessful.
            closeSafely(db);
        }
    }

    protected abstract void handle();

    protected abstract KmSqlDatabase getDatabase();

    //##################################################
    //# support
    //##################################################

    private void closeSafely(KmSqlDatabase e)
    {
        try
        {
            if ( e != null )
                e.closeSafely();
        }
        catch ( RuntimeException ex )
        {
            Kmu.handleUnhandledException(ex);
        }
    }

}
