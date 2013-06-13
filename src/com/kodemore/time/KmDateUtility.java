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

import com.kodemore.utility.Kmu;

/**
 * I define various static methods that are useful when dealing with dates.
 */
public class KmDateUtility
    implements KmTimeConstantsIF
{
    //##################################################
    //# testing
    //##################################################

    /**
     * Determine if the combination of year, month, and day
     * defines a valid date.
     */
    public static boolean isValid(int year, int month, int day)
    {
        if ( !isYearValid(year) )
            return false;

        if ( !isMonthValid(month) )
            return false;

        if ( !isDayValid(year, month, day) )
            return false;

        return true;
    }

    /**
     * Determine if the month is valid.
     */
    public static boolean isMonthValid(int month)
    {
        if ( month < MINIMUM_MONTH )
            return false;

        if ( month > MAXIMUM_MONTH )
            return false;

        return true;
    }

    /**
     * Determine if the day is valid for the year and month.
     */
    public static boolean isDayValid(int year, int month, int day)
    {
        if ( day < MINIMUM_DAY )
            return false;

        if ( day > KmDateUtility.getDaysInYearMonth(year, month) )
            return false;

        return true;
    }

    /**
     * Determine if the year is valid.
     */
    public static boolean isYearValid(int year)
    {
        if ( year < MINIMUM_YEAR )
            return false;

        if ( year > MAXIMUM_YEAR )
            return false;

        return true;
    }

    /**
     * Determine if the orginal value is valid.
     */
    public static boolean isOrdinalValid(int ordinal)
    {
        if ( ordinal < 0 )
            return false;

        if ( ordinal > DAYS_DIFF_1800_9999 )
            return false;

        return true;
    }

    /**
     * Determine the number of days in a given month.
     */
    public static int getDaysInYearMonth(int year, int month)
    {
        return isLeapYear(year)
            ? DAYS_PER_MONTH_LEAP[month - 1]
            : DAYS_PER_MONTH_NONLEAP[month - 1];
    }

    /**
     * Determine if a year is a leap year.
     */
    public static boolean isLeapYear(int year)
    {
        if ( !isYearValid(year) )
            throw new IllegalArgumentException("Invalid year: " + year);

        if ( year % 400 == 0 )
            return true;

        if ( year % 100 == 0 )
            return false;

        if ( year % 4 == 0 )
            return true;

        return false;
    }

    //##################################################
    //# misc
    //##################################################

    /**
     * Determine the difference between two dates.  The resultant
     * array contains three elements { years, months, days }.
     */
    public static final int[] difference(KmDate fromDate, KmDate toDate)
    {
        if ( fromDate.getOrdinal() >= toDate.getOrdinal() )
            return new int[]
            {
                0,
                0,
                0
            };

        int fromYear = fromDate.getYear();
        int fromMonth = fromDate.getMonth();
        int fromDay = fromDate.getDay();
        int toYear = toDate.getYear();
        int toMonth = toDate.getMonth();
        int toDay = toDate.getDay();
        int diffYears = toYear - fromYear;
        int diffMonths = toMonth - fromMonth;
        int diffDays = toDay - fromDay;

        if ( diffDays < 0 )
        {
            diffDays += getDaysInYearMonth(fromYear, fromMonth);
            diffMonths--;
        }
        if ( diffMonths < 0 )
        {
            diffMonths += 12;
            diffYears--;
        }
        if ( fromYear < 0 && toYear > 0 )
            diffYears--;

        if ( diffYears < 0 )
        {
            diffYears = 0;
            diffMonths = 0;
            diffDays = 0;
        }
        return new int[]
        {
            diffYears,
            diffMonths,
            diffDays
        };
    }

    //##################################################
    //# debug
    //##################################################

    /**
     * Determine the value for the constant DAYS_DIFF_1800_1970.
     */
    public int _getDiffDays1800And1970()
    {
        int days = 0;
        for ( int i = MINIMUM_YEAR; i < 1970; i++ )
            if ( isLeapYear(i) )
                days += DAYS_PER_LEAP_YEAR;
            else
                days += DAYS_PER_NONLEAP_YEAR;
        return days;
    }

    /**
     * Determine the value for the constant DAYS_DIFF_1800_9999
     */
    public int _getDiffDays1800And9999()
    {
        int days = 0;
        for ( int i = MINIMUM_YEAR; i < MAXIMUM_YEAR; i++ )
            if ( isLeapYear(i) )
                days += DAYS_PER_LEAP_YEAR;
            else
                days += DAYS_PER_NONLEAP_YEAR;
        return days;
    }

    //##################################################
    //# display
    //##################################################

    public static String getMonthName(int i)
    {
        switch ( i )
        {
            case JANUARY:
                return "January";

            case FEBRUARY:
                return "February";

            case MARCH:
                return "March";

            case APRIL:
                return "April";

            case MAY:
                return "May";

            case JUNE:
                return "June";

            case JULY:
                return "July";

            case AUGUST:
                return "August";

            case SEPTEMBER:
                return "September";

            case OCTOBER:
                return "October";

            case NOVEMBER:
                return "November";

            case DECEMBER:
                return "December";
        }
        return "Unknown";
    }

    public static String getMonthAbbreviation(int i)
    {
        switch ( i )
        {
            case JANUARY:
                return "Jan";

            case FEBRUARY:
                return "Feb";

            case MARCH:
                return "Mar";

            case APRIL:
                return "Apr";

            case MAY:
                return "May";

            case JUNE:
                return "Jun";

            case JULY:
                return "Jul";

            case AUGUST:
                return "Aug";

            case SEPTEMBER:
                return "Sep";

            case OCTOBER:
                return "Oct";

            case NOVEMBER:
                return "Nov";

            case DECEMBER:
                return "Dec";
        }
        return "Unknown";
    }

    //##################################################
    //# format
    //##################################################

    /**
     * I provide a very simple, and non-optimized, facility for formatting
     * dates in a variety of formats.  Valid format codes are:
     * {d}    : day of month (1-31)
     * {dd}   : day of month (01-31)
     * {ddd}  : weekday abbreviation (Sun-Sat)
     * {dddd} : weekday name (Sunday-Saturday)
     * {m}    : month (1-12)
     * {mm}   : month (01-12)
     * {mmm}  : month abbreviation (Jan-Dec)
     * {mmmm} : month name (January-December)
     * {yy}   : year (00-99)
     * {yyyy} : year (1800-9999)
     */
    public static String format(KmDate d, String format)
    {
        if ( d == null )
            return "";

        String s = format;
        s = Kmu.replaceAll(s, "{mmmm}", KmDateUtility.getMonthName(d.getMonth()));
        s = Kmu.replaceAll(s, "{mmm}", KmDateUtility.getMonthAbbreviation(d.getMonth()));
        s = Kmu.replaceAll(s, "{mm}", pad2(d.getMonth()));
        s = Kmu.replaceAll(s, "{m}", d.getMonth() + "");

        s = Kmu.replaceAll(s, "{dddd}", d.getWeekDay().getName());
        s = Kmu.replaceAll(s, "{ddd}", d.getWeekDay().getAbbreviation());
        s = Kmu.replaceAll(s, "{dd}", pad2(d.getDay()));
        s = Kmu.replaceAll(s, "{d}", d.getDay() + "");

        s = Kmu.replaceAll(s, "{yyyy}", d.getYear() + "");
        s = Kmu.replaceAll(s, "{yy}", pad2(getShortYear(d)));
        return s;
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 1/31/1985
     */
    public static String format_m_dd_yy(KmDate d)
    {
        if ( d == null )
            return "";
        return ""
            + d.getMonth()
            + DATE_SEPARATOR
            + pad2(d.getDay())
            + DATE_SEPARATOR
            + pad2(getShortYear(d));
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 01/31/85
     */
    public static String format_mm_dd_yy(KmDate d)
    {
        if ( d == null )
            return "";
        return ""
            + pad2(d.getMonth())
            + DATE_SEPARATOR
            + pad2(d.getDay())
            + DATE_SEPARATOR
            + pad2(getShortYear(d));
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 01/31/1985
     */
    public static String format_mm_dd_yyyy(KmDate d)
    {
        if ( d == null )
            return "";

        return ""
            + pad2(d.getMonth())
            + DATE_SEPARATOR
            + pad2(d.getDay())
            + DATE_SEPARATOR
            + pad4(d.getYear());
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 1/31/1985
     */
    public static String format_m_d_yyyy(KmDate d)
    {
        if ( d == null )
            return "";

        return "" + d.getMonth() + DATE_SEPARATOR + d.getDay() + DATE_SEPARATOR + pad4(d.getYear());
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 19850131.
     */
    public static String format_yyyymmdd(KmDate d)
    {
        if ( d == null )
            return "";
        return "" + pad4(d.getYear()) + pad2(d.getMonth()) + pad2(d.getDay());
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 198501.
     */
    public static String format_yyyymm(KmDate d)
    {
        if ( d == null )
            return "";
        return "" + pad4(d.getYear()) + pad2(d.getMonth());
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 850131.
     */
    public static String format_yymmdd(KmDate d)
    {
        if ( d == null )
            return "";
        return "" + pad2(d.getShortYear()) + pad2(d.getMonth()) + pad2(d.getDay());
    }

    /**
     * Format a date in XSD format.
     */
    public static String formatXsd(KmDate d)
    {
        return KmDateFormatter.formatDate(d, "{yyyy}-{mm}-{dd}");
    }

    /**
     * Format a date, e.g.: Jan 31, 1985 -> 01/85
     */
    public static String format_mm_yy(KmDate d)
    {
        if ( d == null )
            return "";
        return "" + pad2(d.getMonth()) + DATE_SEPARATOR + pad4(d.getYear());
    }

    //##################################################
    //# private
    //##################################################

    private static String pad2(int i)
    {
        return Kmu.padLeft(i, 2, '0');
    }

    private static String pad4(int i)
    {
        return Kmu.padLeft(i, 4, '0');
    }

    private static int getShortYear(KmDate d)
    {
        return d.getYear() % 100;
    }

}
