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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kodemore.utility.Kmu;

/**
 * I represent a time of day; e.g.: 2:12 pm.
 * Instances are assumed to be immutable.
 */
public class KmTime
    implements KmTimeConstantsIF, Comparable<KmTime>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmTime createNowUtc()
    {
        long ms = System.currentTimeMillis();
        return createFromSystemMillis(ms);
    }

    /**
     * Create a time based on the number of milliseconds as returned
     * from System.currentTimeMillis.
     */
    public static KmTime createFromSystemMillis(long ms)
    {
        return createFromMsSince1970(ms);
    }

    /**
     * Create a time based on the number of milliseconds since 1970 Utc.
     */
    public static KmTime createFromMsSince1970(long ms)
    {
        return createOrdinal((int)(ms % MS_PER_DAY));
    }

    public static KmTime createNowLocal()
    {
        Calendar c = new GregorianCalendar();
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        return create(hh, mm, ss, ms);
    }

    public static KmTime create(int hh, int mm, int ss, int ms)
    {
        return new KmTime(hh * MS_PER_HOUR + mm * MS_PER_MINUTE + ss * MS_PER_SECOND + ms);
    }

    public static KmTime create(int hh, int mm, int ss)
    {
        return create(hh, mm, ss, 0);
    }

    public static KmTime create(int hh, int mm)
    {
        return create(hh, mm, 0, 0);
    }

    public static KmTime create(int hh)
    {
        return create(hh, 0, 0, 0);
    }

    public static KmTime create(Date d)
    {
        if ( d == null )
            return null;

        // does not account for time zones
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        return create(hh, mm, ss);
    }

    public static KmTime createMidnight()
    {
        return new KmTime(0);
    }

    public static KmTime createOrdinal(int i)
    {
        return createAdjustment(i).getTime();
    }

    /**
     * Set the orginal value and return the number of days offset.
     * For example if the value set is MS_PER_DAY + 3, then ordinal
     * is set to 3 and the number of days returned is 1.  If the value
     * set is -3 then the milliseconds should be set to MS_PER_DAY - 3,
     * and the number of days returned should be -1.
     */
    public static KmTimeAdjustment createAdjustment(long i)
    {
        int days = (int)(i / MS_PER_DAY);
        int ms = (int)(i % MS_PER_DAY);

        if ( ms >= 0 )
            return new KmTimeAdjustment(new KmTime(ms), days);

        return new KmTimeAdjustment(new KmTime(MS_PER_DAY + ms), -days - 1);
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * Ordinal is the number of milliseconds since midnight.  The
     * ordinal is always normalized so that it is in the range of
     * 0..ms_per_day.
     */
    private int _ordinal;

    //##################################################
    //# constructor
    //##################################################

    private KmTime(int ordinal)
    {
        _ordinal = ordinal % MS_PER_DAY;
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Get the ordinal value.  This is the number of milliseconds
     * since midnight.
     */
    public int getOrdinal()
    {
        return _ordinal;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public int getHour()
    {
        return _ordinal / MS_PER_HOUR;
    }

    public int getHour12()
    {
        int i = getHour() % 12;
        return i == 0
            ? 12
            : i;
    }

    public int getMinute()
    {
        return _ordinal / MS_PER_MINUTE % MINUTES_PER_HOUR;
    }

    public int getTotalMinutes()
    {
        return _ordinal / MS_PER_MINUTE;
    }

    public int getSecond()
    {
        return _ordinal / MS_PER_SECOND % SECONDS_PER_MINUTE;
    }

    public int getTotalSeconds()
    {
        return _ordinal / MS_PER_SECOND;
    }

    public int getMillisecond()
    {
        return _ordinal % MS_PER_SECOND;
    }

    public int getTotalMilliseconds()
    {
        return _ordinal;
    }

    public KmTime toHour(int hh)
    {
        return create(hh, getMinute(), getSecond(), getMillisecond());
    }

    //##################################################
    //# database (my sql)
    //##################################################

    /**
     * Used to fast time conversion with my sql.  Returns
     * the number of seconds since midnight.
     */
    public int getMySqlOrdinal()
    {
        return getTotalSeconds();
    }

    public static KmTime createMySqlOrdinal(int secs)
    {
        return create(0, 0, secs);
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isAm()
    {
        return getHour() < 12;
    }

    public boolean isPm()
    {
        return !isAm();
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmTime) )
            return false;

        return ((KmTime)e)._ordinal == _ordinal;
    }

    @Override
    public int hashCode()
    {
        return _ordinal;
    }

    @Override
    public int compareTo(KmTime e)
    {
        return Kmu.compare(_ordinal, e.getOrdinal());
    }

    //##################################################
    //# compare (convenience)
    //##################################################

    public boolean isBefore(KmTime d)
    {
        return compareTo(d) < 0;
    }

    public boolean isOnOrBefore(KmTime d)
    {
        return compareTo(d) <= 0;
    }

    public boolean isAfter(KmTime d)
    {
        return compareTo(d) > 0;
    }

    public boolean isOnOrAfter(KmTime d)
    {
        return compareTo(d) >= 0;
    }

    /**
     * Determine if I am between the start and end time, also return true
     * if I am equal to either the start or end time.
     */
    public boolean isBetweenInclusive(KmTime start, KmTime end)
    {
        if ( start != null && isBefore(start) )
            return false;

        if ( end != null && isAfter(end) )
            return false;

        return true;
    }

    /**
     * Determine if I am between the start and end time; return false
     * if I am equal to either the start or end time.
     */
    public boolean isBetweenExclusive(KmTime start, KmTime end)
    {
        if ( start != null && isOnOrBefore(start) )
            return false;

        if ( end != null && isOnOrAfter(end) )
            return false;

        return true;
    }

    public KmDuration difference(KmTime t)
    {
        long a = getOrdinal();
        long b = t.getOrdinal();
        long c = Math.abs(a - b);

        KmDuration e;
        e = new KmDuration();
        e.setOrdinal(c);
        return e;
    }

    //##################################################
    //# math
    //##################################################

    public KmTimeAdjustment addHour()
    {
        return addHours(1);
    }

    public KmTimeAdjustment addHours(long i)
    {
        return createAdjustment(_ordinal + i * MS_PER_HOUR);
    }

    public KmTimeAdjustment addMinute()
    {
        return addMinutes(1);
    }

    public KmTimeAdjustment addMinutes(long i)
    {
        return createAdjustment(_ordinal + i * MS_PER_MINUTE);
    }

    public KmTimeAdjustment addSecond()
    {
        return addSeconds(1);
    }

    public KmTimeAdjustment addSeconds(long i)
    {
        return createAdjustment(_ordinal + i * MS_PER_SECOND);
    }

    public KmTimeAdjustment addMillisecond()
    {
        return addMilliseconds(1);
    }

    public KmTimeAdjustment addMilliseconds(long i)
    {
        return createAdjustment(_ordinal + i);
    }

    public KmTimeAdjustment addDuration(KmDuration e)
    {
        return createAdjustment(_ordinal + e.getTotalMilliseconds());
    }

    //##################################################
    //# subtract
    //##################################################

    public KmTimeAdjustment subtractHour()
    {
        return addHours(-1);
    }

    public KmTimeAdjustment subtractHours(int i)
    {
        return addHours(-i);
    }

    public KmTimeAdjustment subtractMinute()
    {
        return addMinutes(-1);
    }

    public KmTimeAdjustment subtractMinutes(int i)
    {
        return addMinutes(-i);
    }

    public KmTimeAdjustment subtractSecond()
    {
        return addSeconds(-1);
    }

    public KmTimeAdjustment subtractSeconds(int i)
    {
        return addSeconds(-i);
    }

    public KmTimeAdjustment subtractMillisecond()
    {
        return addMilliseconds(-1);
    }

    public KmTimeAdjustment subtractMilliseconds(int i)
    {
        return addMilliseconds(-i);
    }

    public KmTimeAdjustment subtractDuration(KmDuration e)
    {
        return createAdjustment(_ordinal - e.getTotalMilliseconds());
    }

    //##################################################
    //# format
    //##################################################

    public String format(String s)
    {
        return KmTimeUtility.format(this, s);
    }

    public String format_h_mm_am()
    {
        return KmTimeUtility.format_h_mm_am(this);
    }

    public String format_h_mm_ss_am()
    {
        return KmTimeUtility.format_h_mm_ss_am(this);
    }

    public String format_hh24_mm_ss()
    {
        return KmTimeUtility.format_hh24_mm_ss(this);
    }

    public String format_hh24mmss()
    {
        return KmTimeUtility.format_hh24mmss(this);
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return KmTimeUtility.format_h_mm_ss_am(this);
    }

}
