package com.kodemore.sql;

import com.kodemore.utility.Kmu;

/**
 * I am used to help clients understand a specific error condition.
 */
public class KmSqlConnectionClosedException
    extends RuntimeException
{
    //##################################################
    //# static
    //##################################################

    /**
     * I provide a standard way to check other exceptions raise
     * an connectionClosedException if appropriate.
     */
    public static void check(IllegalStateException ex)
    {
        if ( ex == null )
            return;

        String b = ex.getMessage();
        String a = "Cannot perform this operation because the connection pool has been closed.";
        if ( Kmu.isEqual(a, b) )
            throw new KmSqlConnectionClosedException();
    }

    //##################################################
    //# constructor
    //##################################################

    public KmSqlConnectionClosedException()
    {
        // none
    }
}
