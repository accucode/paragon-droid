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

import java.util.List;

import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * I define a basic date parser.  Options may be set for various parsing
 * methods.
 */
public class KmDateParser
    implements KmDateParserIF, KmConstantsIF
{
    //##################################################
    //# static
    //##################################################

    public static KmDate parseDate(String s)
    {
        return new KmDateParser().parse(s);
    }

    public static KmDate parseXsd(String s)
    {
        KmDateParser e;
        e = new KmDateParser();
        e.setSeparator('-');
        e.setOneNumberMode(ONE_NUMBER_MODE_NONE);
        e.setTwoNumberMode(TWO_NUMBER_MODE_NONE);
        e.setThreeNumberMode(THREE_NUMBER_MODE_YEAR_MONTH_DAY);
        return e.parse(s);
    }

    //##################################################
    //# static
    //##################################################

    // kludge: change to enum
    public static final int ONE_NUMBER_MODE_NONE                   = 0;
    public static final int ONE_NUMBER_MODE_DAY_OFFSET             = 1;

    public static final int TWO_NUMBER_MODE_NONE                   = 0;
    public static final int TWO_NUMBER_MODE_MONTH_DAY_CLOSEST_YEAR = 1;
    public static final int TWO_NUMBER_MODE_MONTH_DAY_CURRENT_YEAR = 2;
    public static final int TWO_NUMBER_MODE_MONTH_YEAR_FIRST_DAY   = 3;
    public static final int TWO_NUMBER_MODE_MONTH_YEAR_LAST_DAY    = 4;

    public static final int THREE_NUMBER_MODE_NONE                 = 0;
    public static final int THREE_NUMBER_MODE_MONTH_DAY_YEAR       = 1;
    public static final int THREE_NUMBER_MODE_YEAR_MONTH_DAY       = 2;

    //##################################################
    //# variables
    //##################################################

    private char            _separator;
    private int             _centurySplitYear;
    private int             _minimumYear;
    private int             _maximumYear;

    private int             _oneNumberMode;
    private int             _twoNumberMode;
    private int             _threeNumberMode;

    //##################################################
    //# constructor
    //##################################################

    public KmDateParser()
    {
        _separator = '/';
        _centurySplitYear = 30;
        _minimumYear = 1800;
        _maximumYear = 9999;
        _oneNumberMode = ONE_NUMBER_MODE_DAY_OFFSET;
        _twoNumberMode = TWO_NUMBER_MODE_MONTH_DAY_CLOSEST_YEAR;
        _threeNumberMode = THREE_NUMBER_MODE_MONTH_DAY_YEAR;
    }

    //##################################################
    //# accessing
    //##################################################

    public char getSeparator()
    {
        return _separator;
    }

    public void setSeparator(char e)
    {
        _separator = e;
    }

    public int getCenturySplitYear()
    {
        return _centurySplitYear;
    }

    public void setCenturySplitYear(int e)
    {
        _centurySplitYear = e;
    }

    public int getMinimumYear()
    {
        return _minimumYear;
    }

    public void setMinimumYear(int e)
    {
        _minimumYear = e;
    }

    public int getMaximumYear()
    {
        return _maximumYear;
    }

    public void setMaximumYear(int e)
    {
        _maximumYear = e;
    }

    //##################################################
    //# parse modes
    //##################################################

    public int getOneNumberMode()
    {
        return _oneNumberMode;
    }

    public void setOneNumberMode(int e)
    {
        _oneNumberMode = e;
    }

    public int getTwoNumberMode()
    {
        return _twoNumberMode;
    }

    public void setTwoNumberMode(int e)
    {
        _twoNumberMode = e;
    }

    public int getThreeNumberMode()
    {
        return _threeNumberMode;
    }

    public void setThreeNumberMode(int e)
    {
        _threeNumberMode = e;
    }

    //##################################################
    //# parse
    //##################################################

    @Override
    public KmDate parse(String s)
    {
        try
        {
            if ( s == null )
                return null;
            s = s.trim();

            int[] arr = getFields(s);
            switch ( arr.length )
            {
                case 1:
                    return parse1(arr);

                case 2:
                    return parse2(arr);

                case 3:
                    return parse3(arr);
            }
            return null;
        }
        catch ( IllegalArgumentException ex )
        {
            return null;
        }
    }

    public int[] getFields(String s)
    {
        List<String> v = Kmu.tokenize(s, _separator);
        int n = v.size();
        int[] arr = new int[n];
        for ( int i = 0; i < n; i++ )
            arr[i] = Kmu.parse_int(v.get(i));
        return arr;
    }

    private KmDate parse1(int[] arr)
    {
        if ( _oneNumberMode == ONE_NUMBER_MODE_DAY_OFFSET )
        {
            int i = arr[0];
            if ( i == UNDEFINED_INT )
                return null;

            KmDate d = KmDate.createOffsetUtc(i);

            int yy = d.getYear();
            if ( yy < _minimumYear )
                return null;

            if ( yy > _maximumYear )
                return null;

            return d;
        }
        return null;
    }

    private KmDate parse2(int[] arr)
    {
        if ( _twoNumberMode == TWO_NUMBER_MODE_MONTH_DAY_CLOSEST_YEAR )
        {
            int mm = arr[0];
            int dd = arr[1];

            if ( mm == UNDEFINED_INT || dd == UNDEFINED_INT )
                return null;

            KmDate today = KmDate.createTodayUtc();
            int yy = today.getYear();

            KmDate d1 = KmDate.create(yy - 1, mm, dd);
            KmDate d2 = KmDate.create(yy, mm, dd);
            KmDate d3 = KmDate.create(yy + 1, mm, dd);

            int n1 = Math.abs(d1.getDaysTo(today));
            int n2 = Math.abs(d2.getDaysTo(today));
            int n3 = Math.abs(d3.getDaysTo(today));

            if ( n1 < n2 )
                return d1;

            if ( n3 < n2 )
                return d3;

            return d2;
        }
        if ( _twoNumberMode == TWO_NUMBER_MODE_MONTH_DAY_CURRENT_YEAR )
        {
            int mm = arr[0];
            int dd = arr[1];

            if ( mm == UNDEFINED_INT || dd == UNDEFINED_INT )
                return null;

            int yy = KmDate.createTodayUtc().getYear();

            return createDate(yy, mm, dd);
        }
        if ( _twoNumberMode == TWO_NUMBER_MODE_MONTH_YEAR_FIRST_DAY )
        {
            if ( arr[0] == UNDEFINED_INT || arr[1] == UNDEFINED_INT )
                return null;

            int mm = arr[0];
            int yy = coerceYear(arr[1]);

            int dd = 1;
            return createDate(yy, mm, dd);
        }
        if ( _twoNumberMode == TWO_NUMBER_MODE_MONTH_YEAR_LAST_DAY )
        {
            if ( arr[0] == UNDEFINED_INT || arr[1] == UNDEFINED_INT )
                return null;

            int mm = arr[0];
            int yy = coerceYear(arr[1]);
            int dd = KmDateUtility.getDaysInYearMonth(yy, mm);
            return createDate(yy, mm, dd);
        }
        return null;
    }

    private KmDate parse3(int[] arr)
    {
        if ( _threeNumberMode == THREE_NUMBER_MODE_MONTH_DAY_YEAR )
        {
            int mm = arr[0];
            int dd = arr[1];
            int yy = arr[2];
            return createDate(yy, mm, dd);
        }
        if ( _threeNumberMode == THREE_NUMBER_MODE_YEAR_MONTH_DAY )
        {
            int yy = arr[0];
            int mm = arr[1];
            int dd = arr[2];
            return createDate(yy, mm, dd);
        }
        return null;
    }

    //##################################################
    //# utility
    //##################################################

    public int coerceYear(int yy)
    {
        if ( yy == UNDEFINED_INT )
            return yy;
        if ( yy >= 100 )
            return yy;
        if ( yy >= _centurySplitYear )
            yy += 1900;
        else
            yy += 2000;
        return yy;
    }

    public KmDate createDate(int yy, int mm, int dd)
    {
        yy = coerceYear(yy);

        if ( mm < 1 )
            return null;
        if ( mm > 12 )
            return null;
        if ( yy < getMinimumYear() )
            return null;
        if ( yy > getMaximumYear() )
            return null;
        if ( dd < 1 )
            return null;
        if ( dd > KmDateUtility.getDaysInYearMonth(yy, mm) )
            return null;
        return KmDate.create(yy, mm, dd);
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String args[])
    {
        String[] arr =
        {
            "1/2/99",
            "1/2/00",
            "1/1",
            "12/31",
            "-1",
            "0",
            "1",
        };
        int n = arr.length;
        for ( int i = 0; i < n; i++ )
        {
            String s = arr[i];
            KmDate d = KmDateParser.parseDate(s);
            System.out.println(Kmu.padRight(s, 10) + d);
        }
    }
}
