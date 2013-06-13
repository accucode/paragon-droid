package com.kodemore.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;

import com.kodemore.collection.KmMap;
import com.kodemore.database.KmSqlCursor;
import com.kodemore.file.KmFilePath;
import com.kodemore.utility.Kmu;

/**
 * I act as a wrapper for an SQLiteDatabase, providing a simpler 
 * interface and various convenience methods.
 *
 * Notable differences:
 * 
 *      New instances of KmSqlDatabase are initially "closed."
 *      Call open() to open it.
 *      
 *      Closing a KmSqlDatabase does not invalidate the instance.
 *      Indeed some operations can ONLY be executed on a closed
 *      database.
 */
public class KmSqlDatabase
{
    //##################################################
    //# static
    //##################################################

    public static KmSqlDatabase open(KmFilePath path)
    {
        KmSqlDatabase e;
        e = new KmSqlDatabase(path);
        e.open();
        return e;
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * The path to the database.
     */
    private KmFilePath                 _path;

    /**
     * The delegate database.  This is initially null and is
     * only valid when the database is open.
     */
    private SQLiteDatabase             _inner;

    /**
     * A collection of insert helpers.  We lazy create these as 
     * needed, then close then automatically when the database 
     * is closed.  This provide a dramatic performance improvement
     * over the more direct SqliteDatabase.insert(...) approach.
     * 
     * tableName -> InsertHelper
     */
    private KmMap<String,InsertHelper> _insertHelpers;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlDatabase(KmFilePath e)
    {
        _path = e;
        _inner = null;
        _insertHelpers = new KmMap<String,InsertHelper>();
    }

    //##################################################
    //# path
    //##################################################

    public KmFilePath getPath()
    {
        return _path;
    }

    //##################################################
    //# open
    //##################################################

    /**
     * Open (or create) the underlying database, typically on the 
     * sd card. 
     */
    public void open()
    {
        String realPath = _path.getRealPath();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(realPath, null);
        _inner = db;
    }

    public boolean isOpen()
    {
        if ( _inner == null )
            return false;

        return _inner.isOpen();
    }

    public void closeSafely()
    {
        for ( InsertHelper e : _insertHelpers.getValues() )
            Kmu.closeSafely(e);

        Kmu.closeSafely(_inner);

        _insertHelpers.clear();
        _inner = null;
    }

    //##################################################
    //# version
    //##################################################

    /**
     * Get the version number stored in the sqlite pragma.
     * This value may be used by the application for schema
     * and migration management.  It is initially zero (0)
     * when the database is initially created.
     */
    public int getVersion()
    {
        return _inner.getVersion();
    }

    public int getNextVersion()
    {
        return getVersion() + 1;
    }

    public void setVersion(int i)
    {
        _inner.setVersion(i);
    }

    public boolean hasVersion(int i)
    {
        return getVersion() == i;
    }

    public boolean isNew()
    {
        return getVersion() == 0;
    }

    //##################################################
    //# transaction
    //##################################################

    public void begin()
    {
        _inner.beginTransaction();
    }

    public void commit()
    {
        _inner.setTransactionSuccessful();
        _inner.endTransaction();
    }

    //##################################################
    //# statements
    //##################################################

    public <T extends KmDaoDomainIF> KmSqlModelSelect<T> createSelect(Class<T> e)
    {
        return new KmSqlModelSelect<T>(this, e);
    }

    /**
     * Run sql that does NOT return a value.
     * For example, this can be used for ddl and pragmas.
     * Inserts and updates are usually better handled 
     * through other methods; and selects will likely
     * cause an exception.
     */
    public void run(String sql)
    {
        _inner.execSQL(sql);
    }

    /**
     * Run and ad hoc sql statement and return the 
     * resulting cursor.  
     */
    public KmSqlCursor query(String sql)
    {
        Cursor c = _inner.rawQuery(sql, null);
        return new KmSqlCursor(c);
    }

    /**
     * Insert the model as a new record.
     * This will fail if the record already exists.
     * Return the internal _id of the newly inserted record.
     * Return -1 if an error occurs.
     */
    public long insert(KmDaoDomainIF e)
    {
        InsertHelper h;
        h = getInsertHelperFor(e);
        return h.insert(e.getValues().getInner());
    }

    /**
     * Update the value, or insert it if it does not already exist.
     * Return the id of the updated (inserted) row.
     * Return -1 if an error occurs.
     */
    public long upsert(KmDaoDomainIF e)
    {
        KmSqlContentValues values = e.getValues();
        ContentValues inner = values.getInner();
        return getInsertHelperFor(e).replace(inner);
    }

    //##################################################
    //# support
    //##################################################

    /**
     * Most clients should NOT use this method.  This is public 
     * for specific integration with other framework tools.  
     */
    public SQLiteDatabase _getInner()
    {
        return _inner;
    }

    private InsertHelper getInsertHelperFor(KmDaoDomainIF e)
    {
        String table = e.getTableName();
        InsertHelper helper = _insertHelpers.get(table);

        if ( helper == null )
        {
            helper = new InsertHelper(_inner, table);
            _insertHelpers.put(table, helper);
        }

        return helper;
    }

}
