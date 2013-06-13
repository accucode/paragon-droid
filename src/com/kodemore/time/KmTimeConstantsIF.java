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
 * I define several constants that are useful when dealing with times.
 */
public interface KmTimeConstantsIF
{
    //##################################################
    //# date format
    //##################################################

    String   DATE_SEPARATOR         = "/";

    //##################################################
    //# months of the year
    //##################################################

    int      JANUARY                = 1;
    int      FEBRUARY               = 2;
    int      MARCH                  = 3;
    int      APRIL                  = 4;
    int      MAY                    = 5;
    int      JUNE                   = 6;
    int      JULY                   = 7;
    int      AUGUST                 = 8;
    int      SEPTEMBER              = 9;
    int      OCTOBER                = 10;
    int      NOVEMBER               = 11;
    int      DECEMBER               = 12;

    //##################################################
    //# internal
    //##################################################

    // these constants will rarely be used outside of the implementation.

    int      UNDEFINED              = -1;

    int      MINIMUM_MONTH          = 1;
    int      MAXIMUM_MONTH          = 12;
    int      MINIMUM_DAY            = 1;
    int      MINIMUM_YEAR           = 1800;
    int      MAXIMUM_YEAR           = 9999;

    int      MINUTES_PER_HOUR       = 60;

    int      SECONDS_PER_MINUTE     = 60;
    int      SECONDS_PER_DAY        = 60 * 60 * 24;

    int      MS_PER_SECOND          = 1000;
    int      MS_PER_MINUTE          = 1000 * 60;
    int      MS_PER_HOUR            = 1000 * 60 * 60;
    int      MS_PER_DAY             = 1000 * 60 * 60 * 24;

    int      DAYS_PER_NONLEAP_YEAR  = 365;
    int      DAYS_PER_LEAP_YEAR     = 366;

    int      DAYS_DIFF_1800_1970    = 62091;                           // why? see _getDiffDays1800And1970()
    int      DAYS_DIFF_1800_9999    = 2994623;                         // why? see _getDiffDays1800And9999()

    long     MS_DIFF_1800_1970      = DAYS_DIFF_1800_1970 * MS_PER_DAY;

    int[]    DAYS_PER_MONTH_NONLEAP =
                                    {
        31,
        28,
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31
                                    };
    int[]    DAYS_PER_MONTH_LEAP    =
                                    {
        31,
        29,
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31
                                    };

    int      MY_SQL_DATE_OFFSET     = 657437;

    TimeZone UTC_TIME_ZONE          = TimeZone.getTimeZone("UTC");

}
