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

public abstract class KmTimeZoneBridge
{
    //##################################################
    //# singleton
    //##################################################

    private static KmTimeZoneBridge _instance = new KmDefaultTimeZoneBridge();

    public static KmTimeZoneBridge getInstance()
    {
        return _instance;
    }

    public static void setInstance(KmTimeZoneBridge e)
    {
        _instance = e;
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Get the local time zone based on whatever preferences are
     * appropriate for your application.
     */
    public abstract KmTimeZoneIF getLocalTimeZone();

    //##################################################
    //# convenience
    //##################################################

    public KmTimestamp toLocal(KmTimestamp utc)
    {
        return toLocal(utc, getLocalTimeZone());
    }

    public KmTimestamp toLocal(KmTimestamp utc, KmTimeZoneIF localTz)
    {
        return localTz == null
            ? utc
            : localTz.toLocal(utc);
    }

    public KmTimestamp toUtc(KmTimestamp local)
    {
        return toUtc(local, getLocalTimeZone());
    }

    public KmTimestamp toUtc(KmTimestamp local, KmTimeZoneIF localTz)
    {
        return localTz == null
            ? local
            : localTz.toUtc(local);
    }

    //##################################################
    //# format
    //##################################################

    public String formatLocalMessage(KmTimestamp utc)
    {
        return formatLocalMessage(utc, getLocalTimeZone());
    }

    public String formatLocalMessage(KmTimestamp utc, KmTimeZoneIF localTz)
    {
        if ( utc == null )
            return null;

        KmTimestamp local = toLocal(utc, localTz);
        String s = KmTimestampUtility.format_m_dd_yy_h_mm_ss_am(local);

        if ( localTz != null )
            s += " (" + localTz.getCode() + ")";

        return s;
    }
}
