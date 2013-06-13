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
import java.util.TimeZone;

import com.kodemore.types.KmDayFrequency;
import com.kodemore.utility.Kmu;

/**
 * Support a simpler (and more reliable) date class than java.util.Date.
 * Instances are assumed to be immutable.
 * Valid years are in the range 1800..9999
 * Valid months are in the range 1..12
 * Valid days are in the range 1..31
 * Days are validated based on the year and month.
 */
public class KmDate
    implements KmTimeConstantsIF, Comparable<KmDate>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    /**
     * Create today, relative to the UTC clock.
     */
    public static KmDate createTodayUtc()
    {
        long msSince1970 = System.currentTimeMillis();
        return createFromSystemMillis(msSince1970);
    }

    /**
     * Create today, relative to whatever business rules define the local time.
     */
    public static KmDate createTodayLocal()
    {
        return KmTimestamp._createNowLocal().getDate();
    }

    /**
     * Create a date from System.currentTimeMillis().
     */
    public static KmDate createFromSystemMillis(long ms)
    {
        return createFromMsSince1970(ms);
    }

    /**
     * Create a date based on the number of milliseconds as returned
     * from System.currentTimeMillis (ms since midnight 1970 Utc).
     */
    public static KmDate createFromMsSince1970(long ms)
    {
        int daysSince1970 = (int)(ms / MS_PER_DAY);
        int x = DAYS_DIFF_1800_1970 + daysSince1970;
        KmDate e = new KmDate(x);
        int padMs = UTC_TIME_ZONE.getOffset(
            1,
            e.getYear(),
            e.getMonth() - 1,
            e.getDay(),
            e.getWeekDay().getJdkIndex(),
            0);
        int excessMillis = (int)(ms % MS_PER_DAY);
        if ( excessMillis + padMs < 0 )
            return new KmDate(x - 1);
        return e;
    }

    /**
     * Create a date that is offset from today.
     * E.g.: 0=today, 1=tomorrow, -1=yesterday...
     */
    public static KmDate createOffsetUtc(int days)
    {
        return createTodayUtc().addDays(days);
    }

    /**
     * Return today, relative to the current system clock.
     */
    public static KmDate createTodaySystemLocal()
    {
        long msSince1970 = System.currentTimeMillis();
        int daysSince1970 = (int)(msSince1970 / MS_PER_DAY);
        int x = DAYS_DIFF_1800_1970 + daysSince1970;
        KmDate e = new KmDate(x);
        int padMs = TimeZone.getDefault().getOffset(
            1,
            e.getYear(),
            e.getMonth() - 1,
            e.getDay(),
            e.getWeekDay().getJdkIndex(),
            0);
        int excessMillis = (int)(msSince1970 % MS_PER_DAY);
        if ( excessMillis + padMs < 0 )
            return new KmDate(x - 1);
        return e;
    }

    /**
     * Return a specific date.  Jan 31, 2000 = create(2000, 1, 31);
     */
    public static KmDate create(int yy, int mm, int dd)
    {
        return createOrdinal(computeOrdinal(yy, mm, dd));
    }

    public static KmDate createOrdinal(int i)
    {
        return new KmDate(i);
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * The ordinal is what fundamentally defines the date.
     * An ordinal value of zero means Jan 1, 1800, 
     * An ordinal value of one means Jan 2, 1800, etc...
     * 
     * Changing the meaning of ordinal will break the contract 
     * for weekDays.  However, if necessary, this is easy to
     * fix, see KmWeekDay.DATE_ORDINAL_ADJUSTMENT.
     */
    private int _ordinal;

    /**
     * The year, month, and day are calculated based on the ordinal
     * value.  They are cached to significantly improve performance.
     */
    private int _year;
    private int _month;
    private int _day;

    //##################################################
    //# constructor
    //##################################################

    /**
     * Create a date based on the number of days since 1800.
     */
    private KmDate(int ordinal)
    {
        _ordinal = ordinal;
        _year = UNDEFINED;
        _month = UNDEFINED;
        _day = UNDEFINED;
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Get the year.
     */
    public int getYear()
    {
        if ( _year == UNDEFINED )
            _computeYearMonthDay();
        return _year;
    }

    /**
     * Get the two digit year.  E.g.: 2013 returns 13
     */
    public int getShortYear()
    {
        return getYear() % 100;
    }

    /**
     * Get the month.
     */
    public int getMonth()
    {
        if ( _month == UNDEFINED )
            _computeYearMonthDay();
        return _month;
    }

    public String getMonthName()
    {
        return KmDateUtility.getMonthName(getMonth());
    }

    public String getMonthAbbreviation()
    {
        return KmDateUtility.getMonthAbbreviation(getMonth());
    }

    /**
     * Get the day [1..31].
     */
    public int getDay()
    {
        if ( _day == UNDEFINED )
            _computeYearMonthDay();
        return _day;
    }

    /**
     * Set the day, using the current year and month.
     */
    public KmDate toDay(int dd)
    {
        return create(getYear(), getMonth(), dd);
    }

    public KmDate toMonth(int mm)
    {
        return create(getYear(), mm, getDay());
    }

    public KmDate toYear(int yy)
    {
        return create(yy, getMonth(), getDay());
    }

    //##################################################
    //# month edge
    //##################################################

    /**
     * Return the first day of the current month.
     */
    public KmDate getStartOfMonth()
    {
        return toDay(1);
    }

    /**
     * Return the last day of the current month.
     */
    public KmDate getEndOfMonth()
    {
        return toDay(getDaysInMonth());
    }

    public boolean isStartOfMonth()
    {
        return getDay() == 1;
    }

    public boolean isEndOfMonth()
    {
        return getDay() == getDaysInMonth();
    }

    //##################################################
    //# year edge
    //##################################################

    /**
     * Return the first day of the current year.
     */
    public KmDate getStartOfYear()
    {
        return create(getYear(), 1, 1);
    }

    /**
     * Return the last day of the current month.
     */
    public KmDate getEndOfYear()
    {
        return create(getYear(), 12, 31);
    }

    public boolean isStartOfYear()
    {
        return getMonth() == 1 && getDay() == 1;
    }

    public boolean isEndOfYear()
    {
        return getMonth() == 12 && getDay() == 31;
    }

    //##################################################
    //# misc
    //##################################################

    /**
     * The number of days since Jan 1 of the current year, 1-based offset.
     * (Jan 1 is day 1).
     */
    public int getDayOfYear()
    {
        int yy = getYear();
        int start = KmDate.create(yy, 1, 1).getOrdinal();
        return getOrdinal() - start + 1;
    }

    /**
     * Return the number of days to the end of month, inclusive.
     * E.g.: On Jan 30, this returns 2.
     */
    public int getDaysToEndOfMonth()
    {
        return getDaysInMonth() - getDay() + 1;
    }

    //##################################################
    //# accessing (ordinal)
    //##################################################

    /**
     * Get the ordinal value; days since Jan 1, 1800.
     */
    public int getOrdinal()
    {
        return _ordinal;
    }

    //##################################################
    //# compare
    //##################################################

    /**
     * Determine if two dates are equal.
     */
    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmDate) )
            return false;

        return ((KmDate)e)._ordinal == _ordinal;
    }

    /**
     * Get the hashcode for the date.
     */
    @Override
    public int hashCode()
    {
        return _ordinal;
    }

    /**
     * Compare two dates.
     */
    @Override
    public int compareTo(KmDate o)
    {
        return _ordinal - o._ordinal;
    }

    //##################################################
    //# compare (convenience)
    //##################################################

    /**
     * Determine if I am before the parameter.
     */
    public boolean isBefore(KmDate d)
    {
        return compareTo(d) < 0;
    }

    /**
     * Determine if I equal to or before the parameter.
     */
    public boolean isOnOrBefore(KmDate d)
    {
        return compareTo(d) <= 0;
    }

    /**
     * Determine if I am after the parameter.
     */
    public boolean isAfter(KmDate d)
    {
        return compareTo(d) > 0;
    }

    /**
     * Determine if I am equal to or after the parameter.
     */
    public boolean isOnOrAfter(KmDate d)
    {
        return compareTo(d) >= 0;
    }

    /**
     * Determine if I am between the start and end date, also return true
     * if I am equal to either the start or end date.
     */
    public boolean isBetweenInclusive(KmDate start, KmDate end)
    {
        if ( start != null && isBefore(start) )
            return false;

        if ( end != null && isAfter(end) )
            return false;

        return true;
    }

    /**
     * Determine if I am between the start and end date; return false
     * if I am equal to either the start or end date.
     */
    public boolean isBetweenExclusive(KmDate start, KmDate end)
    {
        if ( start != null && isOnOrBefore(start) )
            return false;

        if ( end != null && isOnOrAfter(end) )
            return false;

        return true;
    }

    public boolean isBeforeMonthDay(int mm, int dd)
    {
        if ( getMonth() < mm )
            return true;

        if ( getMonth() > mm )
            return false;

        return getDay() < dd;
    }

    public boolean isAfterMonthDay(int mm, int dd)
    {
        if ( getMonth() > mm )
            return true;

        if ( getMonth() < mm )
            return false;

        return getDay() > dd;
    }

    public boolean isFutureUtc()
    {
        return isAfter(createTodayUtc());
    }

    public boolean isPastUtc()
    {
        return isBefore(createTodayUtc());
    }

    public boolean isTodayUtc()
    {
        return equals(createTodayUtc());
    }

    //##################################################
    //# week
    //##################################################

    public KmWeekDay getWeekDay()
    {
        return KmWeekDay.fromDate(this);
    }

    public KmDate getStartOfWeek()
    {
        KmDate d = this;

        while ( !d.isStartOfWeek() )
            d = d.subtractDay();

        return d;
    }

    public KmDate getEndOfWeek()
    {
        KmDate d = this;

        while ( !d.isEndOfWeek() )
            d = d.addDay();

        return d;
    }

    public boolean isStartOfWeek()
    {
        return getWeekDay().isFirstDay();
    }

    public boolean isEndOfWeek()
    {
        return getWeekDay().isLastDay();
    }

    /**
     * Return the most recent day, prior to myself, matching
     * the requested weekDay.
     **/
    public KmDate getPreviousDayOfWeek(KmWeekDay day)
    {
        return getPreviousDayOfWeek(day, false);
    }

    /**
     * Return the closest date matching the requested week day.
     * Optionally include myself in the search. 
     */
    public KmDate getPreviousDayOfWeek(KmWeekDay day, boolean includeToday)
    {
        if ( includeToday && isWeekDay(day) )
            return this;

        KmDate e = this;
        while ( true )
        {
            e = e.getPrevious();
            if ( e.isWeekDay(day) )
                return e;
        }
    }

    /**
     * Return the closest day, after myself, matching
     * the requested weekDay.
     **/
    public KmDate getNextDayOfWeek(KmWeekDay day)
    {
        return getNextDayOfWeek(day, false);
    }

    /**
     * Return the closest day matching the requested weekDay.
     * Optionally include myself in the search.
     **/
    public KmDate getNextDayOfWeek(KmWeekDay day, boolean includeToday)
    {
        if ( includeToday && isWeekDay(day) )
            return this;

        KmDate e = this;
        while ( true )
        {
            e = e.getNext();
            if ( e.isWeekDay(day) )
                return e;
        }
    }

    //##################################################
    //# week: days
    //##################################################

    public boolean isSunday()
    {
        return getWeekDay().isSunday();
    }

    public boolean isMonday()
    {
        return getWeekDay().isMonday();
    }

    public boolean isTuesday()
    {
        return getWeekDay().isTuesday();
    }

    public boolean isWednesday()
    {
        return getWeekDay().isWednesday();
    }

    public boolean isThursday()
    {
        return getWeekDay().isThursday();
    }

    public boolean isFriday()
    {
        return getWeekDay().isFriday();
    }

    public boolean isSaturday()
    {
        return getWeekDay().isSaturday();
    }

    public boolean isWeekDay(KmWeekDay e)
    {
        return getWeekDay() == e;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    /**
     * Determine the number of days in the current month (and year).
     */
    public int getDaysInMonth()
    {
        return KmDateUtility.getDaysInYearMonth(getYear(), getMonth());
    }

    //##################################################
    //# java date conversion
    //##################################################

    /**
     * Set the date from a java.util.Date.
     */
    public static KmDate createJavaDate(Date jd)
    {
        // kludge: fix for time zones
        Calendar c;
        c = new GregorianCalendar();
        c.setTime(jd);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        return create(yy, mm + 1, dd);
    }

    /**
     * Return a java.util.Caldenar corresponding to my date.
     */
    public Calendar getJavaCalendar()
    {
        Calendar c;
        c = new GregorianCalendar();
        c.clear();
        c.set(getYear(), getMonth() - 1, getDay());
        return c;
    }

    /**
     * Return a java.util.Caldenar corresponding to my date.
     */
    public Date getJavaDate()
    {
        return getJavaCalendar().getTime();
    }

    public long getMsSince1970()
    {
        return KmTimestamp.create(this).getMsSince1970();
    }

    //##################################################
    //# math
    //##################################################

    /**
     * Get the next day.
     */
    public KmDate addDay()
    {
        return addDays(1);
    }

    /**
     * Get a future date.  0=today, 1=tomorrow, etc...
     */
    public KmDate addDays(int i)
    {
        if ( i == 0 )
            return this;
        return new KmDate(_ordinal + i);
    }

    /**
     * Move the previous day.
     */
    public KmDate subtractDay()
    {
        return subtractDays(1);
    }

    /**
     * Move back by some number of days.
     */
    public KmDate subtractDays(int i)
    {
        return addDays(-i);
    }

    /**
     * Add seven days to my date.
     */
    public KmDate addWeek()
    {
        return addWeeks(1);
    }

    /**
     * Add some number of weeks to the current date.
     */
    public KmDate addWeeks(int i)
    {
        return addDays(7 * i);
    }

    /**
     * Subtract seven days from my date.
     */
    public KmDate subtractWeek()
    {
        return subtractWeeks(1);
    }

    /**
     * Subtract some number of weeks from my date.
     */
    public KmDate subtractWeeks(int i)
    {
        return addWeeks(-i);
    }

    public KmDate addDuration(KmDuration e)
    {
        return addDays(e.getTotalDays());
    }

    public KmDate substractDuration(KmDuration e)
    {
        return subtractDays(e.getTotalDays());
    }

    /**
     * Add one month to the current date.
     * Attempt to preserve the day, but ensure that the new day is valid for the new month.
     * E.g.: if the old date is 10/31, the new date would be 11/30
     */
    public KmDate addMonth()
    {
        int yy = getYear();
        int mm = getMonth() + 1;
        if ( mm > 12 )
        {
            mm = 1;
            yy++;
        }
        int ddMax = KmDateUtility.getDaysInYearMonth(yy, mm);
        int dd = Kmu.min(getDay(), ddMax);
        return create(yy, mm, dd);
    }

    public KmDate addMonths(int n)
    {
        KmDate e = this;
        for ( int i = 0; i < n; i++ )
            e = e.addMonth();
        return e;
    }

    /**
     * Subtract one month from the current date.
     * Attempt to preserve the day, but ensure that the new day is valid for the new month.
     * E.g.: if the old date is 12/31, the new date would be 11/30
     */
    public KmDate subtractMonth()
    {
        int yy = getYear();
        int mm = getMonth() - 1;
        if ( mm < 1 )
        {
            mm = 12;
            yy--;
        }
        int ddMax = KmDateUtility.getDaysInYearMonth(yy, mm);
        int dd = Kmu.min(getDay(), ddMax);
        return create(yy, mm, dd);
    }

    public KmDate subtractMonths(int n)
    {
        KmDate e = this;
        for ( int i = 0; i < n; i++ )
            e = e.subtractMonth();
        return e;
    }

    /**
     * Add n years to the current date.
     * Attempt to preserve the day, but ensure that the new day is valid for the new date.
     * E.g.: if the old date is 2/29/2004, the new date would be 2/28/2005
     */
    public KmDate addYears(int n)
    {
        int yy = getYear() + n;
        int mm = getMonth();

        int ddMax = KmDateUtility.getDaysInYearMonth(yy, mm);
        int dd = Kmu.min(getDay(), ddMax);
        return create(yy, mm, dd);
    }

    /**
     * Add one year to the current date.
     */
    public KmDate addYear()
    {
        return addYears(1);
    }

    /**
     * Subtract one year to the current date.
     */
    public KmDate subtractYear()
    {
        return addYears(-1);
    }

    //##################################################
    //# accessing
    //##################################################

    public KmDate getNext()
    {
        return addDay();
    }

    public KmDate getPrevious()
    {
        return subtractDay();
    }

    //##################################################
    //# difference
    //##################################################

    /**
     * Return the duration between myself and another date.
     * NOTE: Duractions have magnitude, but not direction;
     * they do not have a forward or back.  Therefore both
     * of the following return a duration of 1 day:
     * today.difference(tomorrow);
     * tomorrow.difference(today);
     * If you need the direction, consider using getDaysTo().
     */
    public KmDuration difference(KmDate d)
    {
        long a = _ordinal;
        long b = d.getOrdinal();
        int days = (int)Math.abs(a - b);
        KmDuration e = new KmDuration();
        e.addDays(days);
        return e;
    }

    /**
     * Count the number of days from my date until the date parameter.  Return
     * zero if the dates are equals.  Return a negative number if the parameter
     * is before my date.
     */
    public int getDaysTo(KmDate d)
    {
        return d._ordinal - _ordinal;
    }

    //##################################################
    //# conversion
    //##################################################

    public KmTimestamp toTimestamp()
    {
        return getStartOfDay();
    }

    public KmTimestamp getStartOfDay()
    {
        return KmTimestamp.create(this);
    }

    public KmTimestamp getEndOfDay()
    {
        return getNext().getStartOfDay().subtractSecond();
    }

    public KmDateInterval toInterval(KmDate end)
    {
        return KmDateInterval.create(this, end);
    }

    //##################################################
    //# format
    //##################################################

    /**
     * Format the date based using a common set of format codes.
     */
    public String format(String format)
    {
        return KmDateUtility.format(this, format);
    }

    /**
     * A convenience formatter.
     */
    public String format_mm_dd_yy()
    {
        return KmDateUtility.format_mm_dd_yy(this);
    }

    /**
     * A convenience formatter.
     */
    public String format_mm_dd_yyyy()
    {
        return KmDateUtility.format_mm_dd_yyyy(this);
    }

    /**
     * A convenience formatter.
     */
    public String format_m_d_yyyy()
    {
        return KmDateUtility.format_m_d_yyyy(this);
    }

    public String format_yyyymmdd()
    {
        return KmDateUtility.format_yyyymmdd(this);
    }

    public String format_yyyymm()
    {
        return KmDateUtility.format_yyyymm(this);
    }

    public String format_yymmdd()
    {
        return KmDateUtility.format_yymmdd(this);
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return format_mm_dd_yyyy();
    }

    //##################################################
    //# frequency
    //##################################################

    public boolean matchesFrequency(KmDayFrequency f)
    {
        if ( f == null )
            return false;

        if ( isMonday() && f.hasMonday() )
            return true;

        if ( isTuesday() && f.hasTuesday() )
            return true;

        if ( isWednesday() && f.hasWednesday() )
            return true;

        if ( isThursday() && f.hasThursday() )
            return true;

        if ( isFriday() && f.hasFriday() )
            return true;

        if ( isSaturday() && f.hasSaturday() )
            return true;

        if ( isSunday() && f.hasSunday() )
            return true;

        return false;
    }

    //##################################################
    //# support
    //##################################################

    private static int computeOrdinal(int yy, int mm, int dd)
    {
        yy += (mm - 1) / 12;
        mm = (mm - 1) % 12 + 1;

        int x = 0;
        for ( int i = MINIMUM_YEAR; i < yy; i++ )
        {
            x += DAYS_PER_NONLEAP_YEAR;
            if ( KmDateUtility.isLeapYear(i) )
                x++;
        }

        for ( int i = MINIMUM_MONTH; i < mm; i++ )
            x += KmDateUtility.getDaysInYearMonth(yy, i);

        x += dd - 1;
        return x;
    }

    private void _computeYearMonthDay()
    {
        int days = 0;
        for ( int i = MINIMUM_YEAR; i <= MAXIMUM_YEAR; i++ )
        {
            int inc = KmDateUtility.isLeapYear(i)
                ? DAYS_PER_LEAP_YEAR
                : DAYS_PER_NONLEAP_YEAR;
            days += inc;
            if ( days > _ordinal )
            {
                days -= inc;
                _year = i;
                break;
            }
        }
        for ( int i = MINIMUM_MONTH; i <= MAXIMUM_MONTH; i++ )
        {
            int inc = KmDateUtility.getDaysInYearMonth(_year, i);
            days += inc;
            if ( days > _ordinal )
            {
                days -= inc;
                _month = i;
                break;
            }
        }
        _day = _ordinal - days + 1;
    }
}
