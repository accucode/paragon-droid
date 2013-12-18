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

package com.kodemore.callLog;

import android.content.Context;
import android.provider.CallLog.Calls;

import com.kodemore.collection.KmList;
import com.kodemore.database.KmQueryBuilder;
import com.kodemore.database.KmSqlCursor;

public class KmCallLogManager
{
    //##################################################
    //# variables
    //##################################################

    private Context _context;

    //##################################################
    //# constructor
    //##################################################

    public KmCallLogManager(Context e)
    {
        _context = e;
    }

    //##################################################
    //# accessing
    //##################################################

    private Context getContext()
    {
        return _context;
    }

    //##################################################
    //# calls
    //##################################################

    public KmList<KmCallLog> getCalls()
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(Calls.CONTENT_URI);

        KmSqlCursor c = q.toCursor();
        try
        {
            return readContacts(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<KmCallLog> readContacts(KmSqlCursor c)
    {
        KmList<KmCallLog> v = new KmList<KmCallLog>();

        while ( c.next() )
            v.add(readCall(c));

        return v;
    }

    private KmCallLog readCall(KmSqlCursor c)
    {
        KmCallLog e;
        e = new KmCallLog();
        e.setCachedName(c.getStringAt(Calls.CACHED_NAME));
        e.setCachedNumberLabel(c.getStringAt(Calls.CACHED_NUMBER_LABEL));
        e.setCachedNumberTypeFromInt(c.getIntegerAt(Calls.CACHED_NUMBER_TYPE));
        e.setCallTypeFromInt(c.getIntegerAt(Calls.TYPE));
        e.setDuration(c.getIntegerAt(Calls.DURATION));
        e.setNewFromInt(c.getIntegerAt(Calls.NEW));
        e.setNumber(c.getStringAt(Calls.NUMBER));
        e.setTimestampFromLong(c.getLongAt(Calls.DATE));
        return e;
    }
}
