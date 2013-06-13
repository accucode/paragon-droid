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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.kodemore.utility.KmClock;

/**
 * I combine a date and time of day.
 * Instances are assumed to be immutable.
 */
public class KmTimestamp
    implements KmTimeConstantsIF, Comparable<KmTimestamp>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    /**
     * Most clients should use KmClock.getNowUtc() instead.
     */
    public static KmTimestamp _createNowUtc()
    {
        return new KmTimestamp(KmDate.createTodayUtc(), KmTime.createNowUtc());
    }

    /**
     * Most clients should use KmClock.getNowLocal() instead.
     */
    public static KmTimestamp _createNowLocal()
    {
        return KmTimestampUtility.toLocal(_createNowUtc());
    }

    public static KmTimestamp _createNowSystemLocal()
    {
        return new KmTimestamp(KmDate.createTodaySystemLocal(), KmTime.createNowLocal());
    }

    public static KmTimestamp create(KmDate d, KmTime t)
    {
        return new KmTimestamp(d, t);
    }

    public static KmTimestamp create(int year, int month, int day)
    {
        KmDate d = KmDate.create(year, month, day);
        return create(d);
    }

    public static KmTimestamp create(KmDate d)
    {
        return create(d, KmTime.createMidnight());
    }

    public static KmTimestamp create(Date d)
    {
        return createJavaDate(d);
    }

    /**
     * Create a timestamp based on the number of milliseconds as returned
     * from System.currentTimeMillis.
     */
    public static KmTimestamp createFromSystemMillis(long ms)
    {
        return createFromMsSince1970(ms);
    }

    /**
     * Create a timestamp based on the ms since midnight 1970 Utc.
     */
    public static KmTimestamp createFromMsSince1970(long ms)
    {
        KmDate date = KmDate.createFromMsSince1970(ms);
        KmTime time = KmTime.createFromMsSince1970(ms);
        return new KmTimestamp(date, time);
    }

    //##################################################
    //# variables
    //##################################################

    private KmDate _date;
    private KmTime _time;

    //##################################################
    //# constructor
    //##################################################

    private KmTimestamp(KmDate date, KmTime time)
    {
        _date = date;
        _time = time;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmDate getDate()
    {
        return _date;
    }

    public boolean hasDate(KmDate e)
    {
        return getDate().equals(e);
    }

    public boolean hasDate(KmTimestamp e)
    {
        return hasDate(e.getDate());
    }

    public KmTime getTime()
    {
        return _time;
    }

    public boolean hasTime(KmTime e)
    {
        return getTime().equals(e);
    }

    //##################################################
    //# convenience
    //##################################################

    public int getYear()
    {
        return getDate().getYear();
    }

    public int getMonth()
    {
        return getDate().getMonth();
    }

    public int getDay()
    {
        return getDate().getDay();
    }

    public int getHour()
    {
        return getTime().getHour();
    }

    public int getHour12()
    {
        return getTime().getHour12();
    }

    public int getMinute()
    {
        return getTime().getMinute();
    }

    public int getSecond()
    {
        return getTime().getSecond();
    }

    public int getMillisecond()
    {
        return getTime().getMillisecond();
    }

    public KmTimestamp toDay(Integer dd)
    {
        return create(getDate().toDay(dd), getTime());
    }

    public KmTimestamp toHour(Integer hh)
    {
        return create(getDate(), getTime().toHour(hh));
    }

    public KmTimestamp toHourExact(Integer hh)
    {
        return create(getDate(), KmTime.create(hh));
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public static KmTimestamp createJavaDate(Date jd)
    {
        Calendar c = new GregorianCalendar();
        c.setTime(jd);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);

        KmDate date = KmDate.create(y, m + 1, d);
        KmTime time = KmTime.create(hh, mm, ss, ms);
        return create(date, time);
    }

    public static KmTimestamp createJavaTimestamp(Timestamp ts)
    {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(ts.getTime());
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);

        KmDate date = KmDate.create(y, m + 1, d);
        KmTime time = KmTime.create(hh, mm, ss, ms);
        return create(date, time);
    }

    public Timestamp getJavaTimestamp()
    {
        return new Timestamp(getMsSince1970());
    }

    public long getMsSince1970()
    {
        Calendar c;
        c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.YEAR, getYear());
        c.set(Calendar.MONTH, getMonth() - 1);
        c.set(Calendar.DAY_OF_MONTH, getDay());
        c.set(Calendar.HOUR_OF_DAY, getHour());
        c.set(Calendar.MINUTE, getMinute());
        c.set(Calendar.SECOND, getSecond());
        c.set(Calendar.MILLISECOND, getMillisecond());

        return c.getTimeInMillis();
    }

    public KmTimestamp getStartOfDay()
    {
        return getDate().getStartOfDay();
    }

    public KmTimestamp getEndOfDay()
    {
        return getDate().getEndOfDay();
    }

    public KmTimestamp getStartOfMonth()
    {
        return getDate().getStartOfMonth().getStartOfDay();
    }

    public KmTimestamp getEndOfMonth()
    {
        return getDate().getEndOfMonth().getEndOfDay();
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmTimestamp) )
            return false;

        KmTimestamp ts = (KmTimestamp)e;
        return getDate().equals(ts.getDate()) && getTime().equals(ts.getTime());
    }

    @Override
    public int hashCode()
    {
        return getDate().hashCode() ^ getTime().hashCode();
    }

    @Override
    public int compareTo(KmTimestamp e)
    {
        int i = getDate().compareTo(e.getDate());
        if ( i != 0 )
            return i;
        return getTime().compareTo(e.getTime());
    }

    //##################################################
    //# compare (convenience)
    //##################################################

    public boolean isBefore(KmTimestamp ts)
    {
        return compareTo(ts) < 0;
    }

    public boolean isBefore(KmTimestampInterval ti)
    {
        if ( ti.hasStart() )
            return isBefore(ti.getStart());
        return false;
    }

    public boolean isNotBefore(KmTimestamp ts)
    {
        return !isBefore(ts);
    }

    public boolean isOnOrBefore(KmTimestamp ts)
    {
        return compareTo(ts) <= 0;
    }

    public boolean isAfter(KmTimestamp ts)
    {
        return compareTo(ts) > 0;
    }

    public boolean isOnOrAfter(KmTimestamp ts)
    {
        return compareTo(ts) >= 0;
    }

    public boolean isNotAfter(KmTimestamp ts)
    {
        return !isAfter(ts);
    }

    public boolean isBetweenInclusive(KmTimestampInterval ti)
    {
        return isBetweenInclusive(ti.getStart(), ti.getEnd());
    }

    /**
     * Determine if I am between the start and end timestamp, also return true
     * if I am equal to either the start or end timestamp.
     */
    public boolean isBetweenInclusive(KmTimestamp start, KmTimestamp end)
    {
        if ( start != null && isBefore(start) )
            return false;

        if ( end != null && isAfter(end) )
            return false;

        return true;
    }

    /**
     * Determine if I am between the start and end timestamp; return false
     * if I am equal to either the start or end timestamp.
     */
    public boolean isBetweenExclusive(KmTimestamp start, KmTimestamp end)
    {
        if ( start != null && isOnOrBefore(start) )
            return false;

        if ( end != null && isOnOrAfter(end) )
            return false;

        return true;
    }

    public boolean isBeforeMonthDay(int mm, int dd)
    {
        return getDate().isBeforeMonthDay(mm, dd);
    }

    public boolean isAfterMonthDay(int mm, int dd)
    {
        return getDate().isAfterMonthDay(mm, dd);
    }

    public boolean isAfterNowUtc()
    {
        return isAfter(KmClock.getNowUtc());
    }

    public boolean isBeforeNowUtc()
    {
        return isBefore(KmClock.getNowUtc());
    }

    public boolean isNowUtc()
    {
        return equals(KmClock.getNowUtc());
    }

    //##################################################
    //# add
    //##################################################

    public KmTimestamp addYear()
    {
        return create(getDate().addYear(), getTime());
    }

    public KmTimestamp addMonth()
    {
        return create(getDate().addMonth(), getTime());
    }

    public KmTimestamp addDay()
    {
        return addDays(1);
    }

    public KmTimestamp addDays(long i)
    {
        return create(getDate().addDay(), getTime());
    }

    public KmTimestamp addHour()
    {
        return addHours(1);
    }

    public KmTimestamp addHours(long i)
    {
        return adjust(getTime().addHours(i));
    }

    public KmTimestamp addMinutes()
    {
        return addMinutes(1);
    }

    public KmTimestamp addMinutes(long i)
    {
        return adjust(getTime().addMinutes(i));
    }

    public KmTimestamp addSecond()
    {
        return addSeconds(1);
    }

    public KmTimestamp addSeconds(long i)
    {
        return adjust(getTime().addSeconds(i));
    }

    public KmTimestamp addMillisecond()
    {
        return addMilliseconds(1);
    }

    public KmTimestamp addMilliseconds(long i)
    {
        return adjust(getTime().addMilliseconds(i));
    }

    public KmTimestamp addDuration(KmDuration d)
    {
        return adjust(getTime().addDuration(d));
    }

    //##################################################
    //# subtract
    //##################################################

    public KmTimestamp subtractDay()
    {
        return subtractDays(1);
    }

    public KmTimestamp subtractDays(int i)
    {
        return create(getDate().subtractDays(i), getTime());
    }

    public KmTimestamp subtractWeek()
    {
        return subtractWeeks(1);
    }

    public KmTimestamp subtractWeeks()
    {
        return subtractDays(7);
    }

    public KmTimestamp subtractWeeks(int i)
    {
        return create(getDate().subtractWeeks(i), getTime());
    }

    public KmTimestamp subtractHour()
    {
        return subtractHours(1);
    }

    public KmTimestamp subtractHours(int i)
    {
        return addHours(-i);
    }

    public KmTimestamp subtractMinutes()
    {
        return subtractMinutes(1);
    }

    public KmTimestamp subtractMinutes(int i)
    {
        return addMinutes(-i);
    }

    public KmTimestamp subtractSecond()
    {
        return subtractSeconds(1);
    }

    public KmTimestamp subtractSeconds(int i)
    {
        return addSeconds(-i);
    }

    public KmTimestamp subtractMillisecond()
    {
        return subtractMilliseconds(1);
    }

    public KmTimestamp subtractMilliseconds(int i)
    {
        return addMilliseconds(-i);
    }

    //##################################################
    //# database (my sql)
    //##################################################

    /**
     * kludge
     * Fast conversions for use with MY SQL
     * MySql must be in UTC timezone.
     */
    public int getMySqlOrdinal()
    {
        return (getDate().getOrdinal() - DAYS_DIFF_1800_1970)
            * SECONDS_PER_DAY
            + getTime().getTotalSeconds();
    }

    public static KmTimestamp createMySqlOrdinal(int i)
    {
        KmDate date = KmDate.createOrdinal(i / SECONDS_PER_DAY + DAYS_DIFF_1800_1970);
        KmTime time = KmTime.createAdjustment(i % SECONDS_PER_DAY * MS_PER_SECOND).getTime();
        return create(date, time);
    }

    //##################################################
    //# utility
    //##################################################

    /**
     * Ordinal is a unique value that is only intended for use when comparing
     * two timestamp.  Comparing two ordinals yields the number of milliseconds
     * between the timestamps.
     */
    public long getOrdinal()
    {
        return (long)getDate().getOrdinal() * MS_PER_DAY + getTime().getOrdinal();
    }

    //##################################################
    //# difference
    //##################################################

    public KmDuration difference(KmTimestamp ts)
    {
        long ms = getMillisecondsDifference(ts);

        KmDuration e;
        e = new KmDuration();
        e.setOrdinal(ms);
        return e;
    }

    public int getDaysDifference(KmTimestamp ts)
    {
        return (int)(getMillisecondsDifference(ts) / MS_PER_DAY);
    }

    public double getDaysDoubleDifference(KmTimestamp ts)
    {
        return (int)(getMillisecondsDifference(ts) / (double)MS_PER_DAY);
    }

    public int getMinutesDifference(KmTimestamp ts)
    {
        return (int)(getMillisecondsDifference(ts) / MS_PER_MINUTE);
    }

    public double getMinutesDoubleDifference(KmTimestamp ts)
    {
        return getMillisecondsDifference(ts) / (double)MS_PER_MINUTE;
    }

    public int getSecondsDifference(KmTimestamp ts)
    {
        return (int)(getMillisecondsDifference(ts) / MS_PER_SECOND);
    }

    public double getSecondsDoubleDifference(KmTimestamp ts)
    {
        return (int)(getMillisecondsDifference(ts) / (double)MS_PER_SECOND);
    }

    public long getMillisecondsDifference(KmTimestamp ts)
    {
        return Math.abs(getOrdinal() - ts.getOrdinal());
    }

    //##################################################
    //# until
    //##################################################

    public double getDaysUntil(KmTimestamp ts)
    {
        return getMillisecondsUntil(ts) / MS_PER_DAY;
    }

    public double getHoursUntil(KmTimestamp ts)
    {
        return getMillisecondsUntil(ts) / MS_PER_HOUR;
    }

    public double getMinutesUntil(KmTimestamp ts)
    {
        return getMillisecondsUntil(ts) / MS_PER_MINUTE;
    }

    public double getSecondsUntil(KmTimestamp ts)
    {
        return getMillisecondsUntil(ts) / MS_PER_SECOND;
    }

    public double getMillisecondsUntil(KmTimestamp ts)
    {
        return ts.getOrdinal() - getOrdinal();
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return KmTimestampUtility.formatFull(this);
    }

    public String format(String s)
    {
        return KmTimestampUtility.format(this, s);
    }

    public String formatXsdUtc()
    {
        return KmTimestampUtility.formatXsdUtc(this);
    }

    public String format_yyyymmdd_hhmmss()
    {
        return getDate().format_yyyymmdd() + "_" + getTime().format_hh24mmss();
    }

    public String format_m_d_yyyy_hh_mm_ss()
    {
        return getDate().format_m_d_yyyy() + " " + getTime().format_hh24_mm_ss();
    }

    public String format_m_d_yyyy_h_mm()
    {
        return getDate().format_m_d_yyyy() + " " + getTime().format_h_mm_am();
    }

    //##################################################
    //# time zone
    //##################################################

    public KmTimestamp toUtc()
    {
        return KmTimestampUtility.toUtc(this);
    }

    public KmTimestamp toUtc(KmTimeZoneIF localTz)
    {
        return KmTimestampUtility.toUtc(this, localTz);
    }

    public KmTimestamp toLocal()
    {
        return KmTimestampUtility.toLocal(this);
    }

    public KmTimestamp toLocal(KmTimeZoneIF localTz)
    {
        return KmTimestampUtility.toLocal(this, localTz);
    }

    public KmDate toLocalDate(KmTimeZoneIF tz)
    {
        return toLocal().getDate();
    }

    public KmTime toLocalTime(KmTimeZoneIF tz)
    {
        return toLocal().getTime();
    }

    public String formatLocal()
    {
        return KmTimestampUtility.formatLocalMessage(this);
    }

    public String formatLocalMessage()
    {
        return KmTimestampUtility.formatLocalMessage(this);
    }

    public String formatLocalMessage(KmTimeZoneIF localTz)
    {
        return KmTimestampUtility.formatLocalMessage(this, localTz);
    }

    //##################################################
    //# support
    //##################################################

    private KmTimestamp adjust(KmTimeAdjustment a)
    {
        int days = a.getDays();
        KmDate date = getDate().addDays(days);
        KmTime time = a.getTime();

        return create(date, time);
    }
}
