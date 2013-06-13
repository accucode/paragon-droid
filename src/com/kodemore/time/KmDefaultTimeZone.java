/*
  Copyright (c) 2005-2011 www.kodemore.com

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

package com.kodemore.time;

import java.util.TimeZone;

/**
 * I provide a time zone based on the default locale
 * provided by the o/s.
 */
public class KmDefaultTimeZone
    implements KmTimeZoneIF
{
    //##################################################
    //# override
    //##################################################

    @Override
    public String getCode()
    {
        return getZone().getID();
    }

    @Override
    public String getName()
    {
        return getZone().getDisplayName();
    }

    @Override
    public KmTimestamp toUtc(KmTimestamp local)
    {
        long ms = local.getMsSince1970();
        int offset = getZone().getOffset(ms);
        return local.subtractMilliseconds(offset);
    }

    @Override
    public KmTimestamp toLocal(KmTimestamp utc)
    {
        long ms = utc.getMsSince1970();
        int offset = getZone().getOffset(ms);
        return utc.addMilliseconds(offset);
    }

    //##################################################
    //# support
    //##################################################

    private TimeZone getZone()
    {
        return TimeZone.getDefault();
    }
}
