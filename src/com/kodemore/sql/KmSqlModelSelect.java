package com.kodemore.sql;

import com.kodemore.collection.KmList;
import com.kodemore.database.KmSqlCursor;
import com.kodemore.utility.Kmu;

/**
 * I am used to run simple select statements that retrieve
 * lists of models.  Although multi-table joins are allowed,
 * the columns should be restricted to the single model
 * being fetched.
 */
public class KmSqlModelSelect<T extends KmDaoDomainIF>
    extends KmSqlSelect
{
    //##################################################
    //# variables
    //##################################################

    private Class<T> _clazz;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlModelSelect(KmSqlDatabase db, Class<T> clazz)
    {
        super(db);

        _clazz = clazz;
    }

    //##################################################
    //# run
    //##################################################

    /**
     * Return the full list of all results.
     */
    public KmList<T> findAll()
    {
        return _composeResults();
    }

    /**
     * Find the first result, if any.  
     * Returns null if no matches are found.
     * 
     * Note: this overrides the row limit.
     */
    public T findFirst()
    {
        limit(1);
        return findAll().getFirstSafe();
    }

    /**
     * Find up to the first N results, if any.  
     * May return less than the n if fewer results are found.
     * Returns an empty list if no matches are found.
     * 
     * Note: this overrides the row limit.
     */
    public KmList<T> findFirst(Integer n)
    {
        limit(n);
        return findAll();
    }

    /**
     * Attempt to find exactly one result.  Throw a runtime
     * exception if the query does not return exactly one row.
     * 
     * Note: this overrides the row limit.
     */
    public T findExactlyOne()
    {
        limit(2);
        KmList<T> v = findAll();

        if ( v.isEmpty() )
            Kmu.fatal("Expected exactly one result (found none).");

        if ( v.hasMultiple() )
            Kmu.fatal("Expected exactly one result (found multiple).");

        return v.getFirst();
    }

    /**
     * Determine if there are any results for this select.
     * This overrides the limit.
     */
    public boolean exists()
    {
        KmSqlCursor c = null;
        try
        {
            limit(1);
            c = query();
            return c.isNotEmpty();
        }
        catch ( IllegalStateException ex )
        {
            KmSqlConnectionClosedException.check(ex);
            throw ex;
        }
        finally
        {
            closeSafely(c);
        }
    }

    //##################################################
    //# support
    //##################################################

    private KmList<T> _composeResults()
    {
        KmSqlCursor c = null;
        try
        {
            c = query();
            return getResults(c, _clazz);
        }
        catch ( IllegalStateException ex )
        {
            KmSqlConnectionClosedException.check(ex);
            throw ex;
        }
        finally
        {
            closeSafely(c);
        }
    }

    private KmList<T> getResults(KmSqlCursor cursor, Class<T> clazz)
    {
        KmList<T> v = new KmList<T>();

        while ( cursor.next() )
        {
            T e = fillElement(cursor, clazz);
            v.add(e);
        }

        return v;
    }

    private T fillElement(KmSqlCursor cursor, Class<T> clazz)
    {
        try
        {
            T e = clazz.newInstance();

            KmList<String> cols = cursor.getColumnNames();
            for ( String col : cols )
                e.applyFromValues(cursor, col);

            return e;
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

}
