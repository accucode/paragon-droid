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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;


/**
 * Just a test.  Cursors are really not working yet.
 */
public class KmQueryBuilder
{
    //##################################################
    //# variables
    //##################################################

    private Context        _context;

    private Uri            _uri;
    private KmList<String> _columns;

    private String         _where;
    private KmList<String> _whereArgs;

    private KmList<String> _sorts;

    //##################################################
    //# constructor
    //##################################################

    public KmQueryBuilder(Context context)
    {
        _context = context;
        _columns = new KmList<String>();
        _where = null;
        _whereArgs = new KmList<String>();
        _sorts = new KmList<String>();
    }

    //##################################################
    //# uri
    //##################################################

    public Uri getUri()
    {
        return _uri;
    }

    public void setUri(Uri e)
    {
        _uri = e;
    }

    //##################################################
    //# columns
    //##################################################

    public KmList<String> getColumns()
    {
        return _columns;
    }

    public void setColumns(KmList<String> v)
    {
        _columns = v;
    }

    public void addColumn(String e)
    {
        _columns.add(e);
    }

    //##################################################
    //# where
    //##################################################

    public void whereEqualString(String col, String value)
    {
        String s = Kmu.format("%s = ?", col);
        where(s);
        whereArg(value);
    }

    //##################################################
    //# where (private)
    //##################################################

    private void where(String s)
    {
        if ( _where == null )
        {
            _where = s;
            return;
        }

        _where += " and " + s;
    }

    private void whereArg(String e)
    {
        _whereArgs.add(e);
    }

    //##################################################
    //# sort
    //##################################################

    public void sortOn(String col)
    {
        _sorts.add(col);
    }

    public void sortOnDescending(String col)
    {
        sortOn(col + " desc");
    }

    //##################################################
    //# query
    //##################################################

    public KmSqlCursor toCursor()
    {
        Uri uri = _uri;
        String[] cols = toArray(_columns);
        String where = _where;
        String[] whereArgs = toArray(_whereArgs);

        String sorts = _sorts.isEmpty()
            ? null
            : _sorts.format(",");

        Cursor c = getContext().getContentResolver().query(uri, cols, where, whereArgs, sorts);
        return new KmSqlCursor(c);
    }

    //##################################################
    //# convenience
    //##################################################

    public KmList<String> getAllStringsAt(String colName)
    {
        KmSqlCursor c = toCursor();
        try
        {
            return c.getAllStringsAt(colName);
        }
        finally
        {
            c.closeSafely();
        }
    }

    //##################################################
    //# support
    //##################################################

    private Context getContext()
    {
        return _context;
    }

    private String[] toArray(KmList<String> v)
    {
        if ( v == null || v.isEmpty() )
            return null;

        int n = v.size();
        String[] arr = new String[n];

        for ( int i = 0; i < n; i++ )
            arr[i] = v.get(i);

        return arr;
    }
}
