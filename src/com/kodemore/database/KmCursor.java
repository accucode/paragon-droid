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
import com.kodemore.utility.Kmu;


/**
 * Just a test.  Cursors are really not working yet.
 */
public class KmCursor
{
    //##################################################
    //# variables
    //##################################################//

    private Cursor _inner;

    //##################################################
    //# constructor
    //##################################################//

    public KmCursor(Cursor e)
    {
        _inner = e;
    }

    //##################################################
    //# navigation
    //##################################################//

    public boolean next()
    {
        return getInner().moveToNext();
    }

    //##################################################
    //# string columns
    //##################################################//

    public String getStringAt(String name)
    {
        return getStringAt(toIndex(name));
    }

    public String getStringAt(Integer index)
    {
        return index == null || index < 0
            ? null
            : getInner().getString(index);
    }

    public KmList<String> getAllStringsAt(String colName)
    {
        return getAllStringsAt(toIndex(colName));
    }

    public KmList<String> getAllStringsAt(int colIndex)
    {
        KmList<String> v = new KmList<String>();

        while ( next() )
            v.add(getStringAt(colIndex));

        return v;
    }

    //##################################################
    //# integer columns
    //##################################################//

    public Integer getIntegerAt(String name)
    {
        return getIntegerAt(toIndex(name));
    }

    public Integer getIntegerAt(Integer index)
    {
        return index == null || index < 0
            ? null
            : getInner().getInt(index);
    }

    //##################################################
    //# close
    //##################################################//

    public void close()
    {
        Kmu.closeSafely(getInner());
    }

    //##################################################
    //# support
    //##################################################//

    private Cursor getInner()
    {
        return _inner;
    }

    private Integer toIndex(String colName)
    {
        int i = getInner().getColumnIndex(colName);
        return i < 0
            ? null
            : i;
    }

}
