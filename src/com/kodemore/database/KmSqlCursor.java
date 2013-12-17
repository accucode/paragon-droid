/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.database;

import android.database.Cursor;

import com.kodemore.collection.KmList;
import com.kodemore.sql.KmSqlColumnIF;
import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.types.KmMoney;
import com.kodemore.utility.Kmu;

/**
 * I wrap the standard cursor class.
 * 
 * This allows us to dramatically simplify client code
 * By providing various convenience methods. For example, 
 * this wrapper provides simplified handling column name, 
 * common data types, and null values.
 * 
 * To illustrate, suppose you have a cursor and want to 
 * fetch the boolean value of a column named "paid".
 *  
 * WITHOUT the KmSqlCursor, you have code that looks like:
 * 
 *      Cursor c = ...;
 *      Boolean paid = null;
 *      int paidColumn = c.getColumnIndex("paid");
 *      if ( paidColumn < 0 )
 *          throw new RuntimeException("Unknown column (paid)");
 *      if ( !c.isNull(paidColumn) )
 *          paid = c.getInt(paidColumn) == 1;
 *      System.out.println(paid);
 *      
 * WITH the KmSqlCursor, this is simplified to:
 * 
 *      KmSqlCursor = ...;
 *      Boolean paid = c.getBooleanAt("paid");
 *      System.out.println(paid);
 *      
 * Effectively, we have simplified six lines of redundant, error
 * prone code into one simple robust line.
 */
public class KmSqlCursor
    implements KmSqlValuesIF
{
    //##################################################
    //# variables
    //##################################################

    private Cursor _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlCursor(Cursor e)
    {
        _inner = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getRowCount()
    {
        return getInner().getCount();
    }

    public boolean isEmpty()
    {
        return getRowCount() == 0;
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    public int getColumnCount()
    {
        return getInner().getColumnCount();
    }

    @Override
    public KmList<String> getKeys()
    {
        return getColumnNames();
    }

    public KmList<String> getColumnNames()
    {
        KmList<String> v;
        v = new KmList<String>();
        v.addAll(getInner().getColumnNames());
        return v;
    }

    //##################################################
    //# navigation
    //##################################################

    public boolean moveToFirst()
    {
        return getInner().moveToFirst();
    }

    public boolean isLast()
    {
        return getInner().isLast();
    }

    public boolean isAfterLast()
    {
        return getInner().isAfterLast();
    }

    public boolean isDone()
    {
        return !hasNext();
    }

    public boolean hasNext()
    {
        return !isEmpty() && !isLast() && !isAfterLast();
    }

    public boolean next()
    {
        return getInner().moveToNext();
    }

    //##################################################
    //# utility
    //##################################################

    /**
     * Confirm that the cursor contains a simple (1x1)
     * result set; that is, a single row containing a single 
     * column.  Anything else will throw a runtime exception.
     * 
     * This does not check for a null value.
     * A single null value is considered a valid response.
     */
    public void checkSimpleResultSet()
    {
        int rows = getRowCount();
        int cols = getColumnCount();

        if ( rows == 0 )
            fatal("No rows");

        if ( rows >= 2 )
            fatal("Multiple rows");

        if ( cols == 0 )
            fatal("No columns");

        if ( cols >= 2 )
            fatal("Multiple columns");
    }

    //##################################################
    //# null columns
    //##################################################

    public boolean isNullAt(KmSqlColumnIF col)
    {
        return isNullAt(col.getName());
    }

    public boolean isNullAt(String col)
    {
        return isNullAt(toColumnIndex(col));
    }

    public boolean isNullAt(int col)
    {
        return getInner().isNull(col);
    }

    //##################################################
    //# blob 
    //##################################################

    public byte[] getBlobAt(KmSqlColumnIF col)
    {
        return getBlobAt(col.getName());
    }

    public byte[] getBlobAt(String col)
    {
        return getBlobAt(toColumnIndex(col));
    }

    private byte[] getBlobAt(int col)
    {
        return isNullAt(col)
            ? null
            : getInner().getBlob(col);
    }

    //##################################################
    //# string columns
    //##################################################

    @Override
    public String getStringAt(KmSqlColumnIF col)
    {
        return getStringAt(col.getName());
    }

    @Override
    public String getStringAt(String col)
    {
        return getStringAt(toColumnIndex(col));
    }

    public String getStringAt(int col)
    {
        return isNullAt(col)
            ? null
            : getInner().getString(col);
    }

    public KmList<String> getAllStringsAt(String col)
    {
        return getAllStringsAt(toColumnIndex(col));
    }

    public KmList<String> getAllStringsAt(int index)
    {
        KmList<String> v = new KmList<String>();

        while ( next() )
            v.add(getStringAt(index));

        return v;
    }

    //##################################################
    //# integer columns
    //##################################################

    @Override
    public Integer getIntegerAt(KmSqlColumnIF col)
    {
        return getIntegerAt(col.getName());
    }

    @Override
    public Integer getIntegerAt(String col)
    {
        return getIntegerAt(toColumnIndex(col));
    }

    public Integer getIntegerAt(int col)
    {
        return isNullAt(col)
            ? null
            : getInner().getInt(col);
    }

    //##################################################
    //# long columns
    //##################################################

    @Override
    public Long getLongAt(KmSqlColumnIF col)
    {
        return getLongAt(col.getName());
    }

    @Override
    public Long getLongAt(String col)
    {
        return getLongAt(toColumnIndex(col));
    }

    public Long getLongAt(int col)
    {
        return isNullAt(col)
            ? null
            : getInner().getLong(col);
    }

    //##################################################
    //# double columns
    //##################################################

    @Override
    public Double getDoubleAt(KmSqlColumnIF col)
    {
        return getDoubleAt(col.getName());
    }

    @Override
    public Double getDoubleAt(String col)
    {
        return getDoubleAt(toColumnIndex(col));
    }

    public Double getDoubleAt(int col)
    {
        return isNullAt(col)
            ? null
            : getInner().getDouble(col);
    }

    //##################################################
    //# boolean columns
    //##################################################

    @Override
    public Boolean getBooleanAt(KmSqlColumnIF col)
    {
        return getBooleanAt(col.getName());
    }

    @Override
    public Boolean getBooleanAt(String col)
    {
        return getBooleanAt(toColumnIndex(col));
    }

    public Boolean getBooleanAt(int col)
    {
        Integer i = getIntegerAt(col);

        if ( i == null )
            return null;

        return i.equals(1);
    }

    //##################################################
    //# date
    //##################################################

    @Override
    public KmDate getDateAt(String key)
    {
        Long ms = getLongAt(key);
        return ms == null
            ? null
            : KmDate.createFromMsSince1970(ms);
    }

    @Override
    public KmDate getDateAt(KmSqlColumnIF key)
    {
        return getDateAt(key.getName());
    }

    //##################################################
    //# timestamp
    //##################################################

    @Override
    public KmTimestamp getTimestampAt(String key)
    {
        Long ms = getLongAt(key);
        return ms == null
            ? null
            : KmTimestamp.createFromMsSince1970(ms);
    }

    @Override
    public KmTimestamp getTimestampAt(KmSqlColumnIF key)
    {
        return getTimestampAt(key.getName());
    }

    //##################################################
    //# money
    //##################################################

    @Override
    public KmMoney getMoneyAt(String key)
    {
        Long cents = getLongAt(key);
        return KmMoney.fromCents(cents);
    }

    @Override
    public KmMoney getMoneyAt(KmSqlColumnIF key)
    {
        return getMoneyAt(key.getName());
    }

    //##################################################
    //# close
    //##################################################

    public void closeSafely()
    {
        Kmu.closeSafely(getInner());
    }

    //##################################################
    //# column index
    //##################################################

    /**
     * Get the 0-offset index of a column name.
     * Default behavior, simply delgates to the default cursor.
     */
    public int getColumnIndex(KmSqlColumnIF col)
    {
        return getColumnIndex(col.getName());
    }

    public int getColumnIndex(String col)
    {
        return getInner().getColumnIndex(col);
    }

    /**
     * Determine if the given column index is valid.
     */
    public boolean isColumnIndexValid(Integer col)
    {
        if ( col == null )
            return false;

        if ( col < 0 )
            return false;

        if ( col >= getColumnCount() )
            return false;

        return true;
    }

    /**
     * Used internally; throws a runtime exception if the 
     * column name is invalid.
     */
    private int toColumnIndex(String colName)
    {
        int i = getInner().getColumnIndex(colName);

        if ( i < 0 )
            fatal("Unknown column name (%s).", colName);

        return i;
    }

    //##################################################
    //# support
    //##################################################

    private Cursor getInner()
    {
        return _inner;
    }

    private void fatal(String msg, Object... args)
    {
        Kmu.fatal(msg, args);
    }

}
