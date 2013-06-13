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

import com.kodemore.utility.KmNamedEnumIF;

/**
 * The days of a week: monday, tuesday, ...
 */
public enum KmWeekDay
    implements KmNamedEnumIF
{
    //##################################################
    //# enum
    //##################################################

    /**
     * The enumerated values.
     *   
     * The values are assumed to be in the correct sequence, but
     * the first enum is NOT assumed to be the first day of the week.
     * E.g.: The enumeration may list the days as Mon..Sun, but the
     * first day of the week may be Saturday.
     * 
     * If you change the sequence of these values, the conversion
     * between dates and weekDays may break.  See the constant
     * KmWeekDay.DATE_ORDINAL_ADJUSTMENT.
     */

    MONDAY("M", "Monday", "Mon"),
    TUESDAY("T", "Tuesday", "Tue"),
    WEDNESDAY("W", "Wednesday", "Wed"),
    THURSDAY("H", "Thursday", "Thu"),
    FRIDAY("F", "Friday", "Fri"),
    SATURDAY("S", "Saturday", "Sat"),
    SUNDAY("U", "Sunday", "Sun");

    //##################################################
    //# constants
    //##################################################

    /**
     * These are intentionally implemented in such a manner that
     * the developer can easily change the system wide default
     * for the first day of the week, without requiring any additional
     * rewiring.  In particular, the ordinal values of the enum values
     * are not required to directly match the first_day.
     */
    public static final KmWeekDay FIRST_DAY               = SUNDAY;
    public static final KmWeekDay LAST_DAY                = FIRST_DAY.getPreviousDay();

    /**
     * This is used to convert a date's ordinal value into a week day.
     * If you change the sequence of the enum, or change the date's 
     * ordinal implementation, then this value may need to change.
     * Current assumes that KmDate.createOrdinal(0) = Jan 1, 1800
     * 
     */
    public static final int       DATE_ORDINAL_ADJUSTMENT = 2;

    //##################################################
    //# variables
    //##################################################

    private String                _code;
    private String                _name;
    private String                _abbreviation;

    //##################################################
    //# constructor
    //##################################################

    private KmWeekDay(String code, String name, String abbr)
    {
        _code = code;
        _name = name;
        _abbreviation = abbr;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getCode()
    {
        return _code;
    }

    @Override
    public String getName()
    {
        return _name;
    }

    public String getAbbreviation()
    {
        return _abbreviation;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    /**
     * Get my 0-based index relative to the first day of the week.
     * For example if Wednesday is the first day of the week, then
     * Wed=0, Thu=1, Fri=2, etc...
     * 
     * See also, getJdkIndex()
     */
    public int getIndex()
    {
        return (ordinal() - FIRST_DAY.ordinal() + 7) % 7;
    }

    /**
     * Returns the index compatible with Calendar.
     * Values returned are 1..7, corresponding to Sunday..Saturday.
     * 
     * This implementation does NOT take multiple calendars 
     * or countries into account, it simply assumes that Sunday 
     * is the first day of the week as defined in Calendar for 
     * the United States.
     */
    public int getJdkIndex()
    {
        return (ordinal() - SUNDAY.ordinal() + 7) % 7 + 1;
    }

    //##################################################
    //# testing: days
    //##################################################

    public boolean isMonday()
    {
        return this == MONDAY;
    }

    public boolean isTuesday()
    {
        return this == TUESDAY;
    }

    public boolean isWednesday()
    {
        return this == WEDNESDAY;
    }

    public boolean isThursday()
    {
        return this == THURSDAY;
    }

    public boolean isFriday()
    {
        return this == FRIDAY;
    }

    public boolean isSaturday()
    {
        return this == SATURDAY;
    }

    public boolean isSunday()
    {
        return this == SUNDAY;
    }

    //##################################################
    //# testing: other
    //##################################################

    public boolean isFirstDay()
    {
        return this == FIRST_DAY;
    }

    public boolean isLastDay()
    {
        return this == LAST_DAY;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    /**
     * Get the next week day.  
     * This wraps around so that LAST_DAY.getNextDay == FIRST_DAY.
     */
    public KmWeekDay getNextDay()
    {
        int i = ordinal();
        KmWeekDay[] arr = values();

        return i == arr.length - 1
            ? arr[0]
            : arr[i + 1];
    }

    /**
     * Get the previous week day.  
     * This wraps around so that FIRST_DAY.getPreviousDay == LAST_DAY.
     */
    public KmWeekDay getPreviousDay()
    {
        int i = ordinal();
        KmWeekDay[] arr = values();

        return i == 0
            ? arr[arr.length - 1]
            : arr[i - 1];
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return getName();
    }

    public static KmWeekDay fromDate(KmDate date)
    {
        int i = (date.getOrdinal() + DATE_ORDINAL_ADJUSTMENT) % 7;
        return values()[i];
    }
}
