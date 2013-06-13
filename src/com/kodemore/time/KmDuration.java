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

import com.kodemore.utility.Kmu;

/**
 * I encapsulate the idea of a length of time, rather than a specific
 * time.  I am some number of hours, minutes, seconds, etc....
 */
public class KmDuration
    implements KmTimeConstantsIF, Comparable<KmDuration>, Serializable
{
    //##################################################
    //# variables
    //##################################################

    private long _ordinal;

    //##################################################
    //# constructor
    //##################################################

    public KmDuration()
    {
        _ordinal = 0;
    }

    //##################################################
    //# instance creation
    //##################################################

    public static KmDuration createDuration(int hh, int mm, int ss, int ms)
    {
        KmDuration d = new KmDuration();
        d.set(hh, mm, ss, ms);
        return d;
    }

    public static KmDuration createDuration(int hh, int mm, int ss)
    {
        return createDuration(hh, mm, ss, 0);
    }

    public static KmDuration createDuration(int hh, int mm)
    {
        return createDuration(hh, mm, 0, 0);
    }

    public static KmDuration createDuration(int hh)
    {
        return createDuration(hh, 0, 0, 0);
    }

    //##################################################
    //# accessing
    //##################################################

    public void setOrdinal(long ordinal)
    {
        _ordinal = ordinal;
    }

    public long getOrdinal()
    {
        return _ordinal;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public int getDay()
    {
        return (int)(_ordinal / MS_PER_DAY);
    }

    public int getTotalDays()
    {
        return (int)(_ordinal / MS_PER_DAY);
    }

    public int getHour()
    {
        return (int)(_ordinal % MS_PER_DAY / MS_PER_HOUR);
    }

    public int getTotalHours()
    {
        return (int)(_ordinal / MS_PER_HOUR);
    }

    public int getMinute()
    {
        return (int)(_ordinal % MS_PER_HOUR / MS_PER_MINUTE);
    }

    public int getTotalMinutes()
    {
        return (int)(_ordinal / MS_PER_MINUTE);
    }

    public int getSecond()
    {
        return (int)(_ordinal % MS_PER_MINUTE / MS_PER_SECOND);
    }

    public int getTotalSeconds()
    {
        return (int)(_ordinal / MS_PER_SECOND);
    }

    public int getMillisecond()
    {
        return (int)(_ordinal % MS_PER_SECOND);
    }

    public int getTotalMilliseconds()
    {
        return (int)_ordinal;
    }

    //##################################################
    //# set
    //##################################################

    public void set(int hh, int mm)
    {
        set(hh, mm, 0, 0);
    }

    public void set(int hh, int mm, int ss)
    {
        set(hh, mm, ss, 0);
    }

    public void set(int hh, int mm, int ss, int ms)
    {
        _ordinal = (long)hh
            * MS_PER_HOUR
            + (long)mm
            * MS_PER_MINUTE
            + (long)ss
            * MS_PER_SECOND
            + ms;
    }

    //##################################################
    //# adding
    //##################################################

    public void add(KmDuration d)
    {
        _ordinal += d._ordinal;
    }

    public void addDays(int dd)
    {
        _ordinal += (long)dd * MS_PER_DAY;
    }

    public void addHours(int hh)
    {
        _ordinal += (long)hh * MS_PER_HOUR;
    }

    public void addMinutes(int mm)
    {
        _ordinal += (long)mm * MS_PER_MINUTE;
    }

    public void addSeconds(int ss)
    {
        _ordinal += (long)ss * MS_PER_SECOND;
    }

    public void addMilliseconds(int ms)
    {
        _ordinal += ms;
    }

    //##################################################
    //# subtracting
    //##################################################

    public void subtract(KmDuration d)
    {
        _ordinal -= d._ordinal;
    }

    public void subtractDays(int dd)
    {
        _ordinal -= (long)dd * MS_PER_DAY;
    }

    public void subtractHours(int hh)
    {
        _ordinal -= (long)hh * MS_PER_HOUR;
    }

    public void subtractMinutes(int mm)
    {
        _ordinal -= (long)mm * MS_PER_MINUTE;
    }

    public void subtractSeconds(int ss)
    {
        _ordinal -= (long)ss * MS_PER_SECOND;
    }

    public void subtractMilliseconds(int ms)
    {
        _ordinal -= ms;
    }

    public void divideBy(int i)
    {
        _ordinal /= i;
    }

    //##################################################
    //# testing
    //##################################################

    public boolean hasDay()
    {
        return getDay() != 0;
    }

    public boolean hasHour()
    {
        return getHour() != 0;
    }

    public boolean hasMinute()
    {
        return getMinute() != 0;
    }

    public boolean hasSecond()
    {
        return getSecond() != 0;
    }

    public boolean hasMillisecond()
    {
        return getMillisecond() != 0;
    }

    //##################################################
    //# comparing
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmDuration) )
            return false;

        return ((KmDuration)e)._ordinal == _ordinal;
    }

    @Override
    public int hashCode()
    {
        return (int)_ordinal;
    }

    @Override
    public int compareTo(KmDuration e)
    {
        KmDuration t = e;
        if ( _ordinal < t._ordinal )
            return -1;
        if ( _ordinal > t._ordinal )
            return 1;
        return 0;
    }

    public boolean isLessThan(KmDuration d)
    {
        return compareTo(d) < 0;
    }

    public boolean isLessThanOrEqualTo(KmDuration d)
    {
        return compareTo(d) <= 0;
    }

    public boolean isGreaterThan(KmDuration d)
    {
        return compareTo(d) > 0;
    }

    public boolean isGreaterThanOrEqualTo(KmDuration d)
    {
        return compareTo(d) >= 0;
    }

    //##################################################
    //# copy
    //##################################################

    public KmDuration getCopy()
    {
        KmDuration e = new KmDuration();
        e.setOrdinal(_ordinal);
        return e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if ( hasDay() )
        {
            sb.append(getDay());
            sb.append(" ");
        }
        sb.append(getHour());
        sb.append(":");
        sb.append(_pad2(getMinute()));
        sb.append(":");
        sb.append(_pad2(getSecond()));
        if ( hasMillisecond() )
        {
            sb.append(".");
            sb.append(_pad3(getMillisecond()));
        }
        return sb.toString();
    }

    //##################################################
    //# format
    //##################################################

    /**
     * I provide a very simple, and non-optimized, facility for formatting
     * duractions in a variety of formats.  Valid format codes are:
     * {nnn}        : thousandths of a second with leading zeros
     * {nnnTotal}   : thousandths of a second (total)
     * {s}          : seconds
     * {ss}         : seconds with leading zero
     * {sTotal}     : seconds (total)
     * {m}          : minutes
     * {mm}         : minutes with leading zero
     * {mTotal}     : minutes (total)
     * {h}          : hours
     * {hh}         : hours with leading zero
     * {hTotal}     : hours (total)
     * {dTotal}     : days
     */
    public String format(String format)
    {
        String s = format;
        s = Kmu.replaceAll(s, "{nnn}", _pad3(getMillisecond()));
        s = Kmu.replaceAll(s, "{nnnTotal}", getTotalMilliseconds() + "");
        s = Kmu.replaceAll(s, "{s}", getSecond() + "");
        s = Kmu.replaceAll(s, "{ss}", _pad2(getSecond()));
        s = Kmu.replaceAll(s, "{sTotal}", getTotalSeconds() + "");
        s = Kmu.replaceAll(s, "{m}", getMinute() + "");
        s = Kmu.replaceAll(s, "{mm}", _pad2(getMinute()));
        s = Kmu.replaceAll(s, "{mTotal}", getTotalMinutes() + "");
        s = Kmu.replaceAll(s, "{h}", getHour() + "");
        s = Kmu.replaceAll(s, "{hh}", _pad2(getHour()));
        s = Kmu.replaceAll(s, "{hTotal}", getTotalHours() + "");
        s = Kmu.replaceAll(s, "{dTotal}", getTotalDays() + "");
        return s;
    }

    public String _pad2(long i)
    {
        return Kmu.padLeft(i, 2, '0');
    }

    public String _pad3(long i)
    {
        return Kmu.padLeft(i, 3, '0');
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String[] args)
    {
        KmDuration d = createDuration(1, 2, 3, 4);
        format(d, "({s} seconds)");
        format(d, "({ss} seconds)");
        format(d, "({sTotal} seconds)");
        format(d, "({mTotal}:{ss} minutes)");
    }

    public static void format(KmDuration d, String f)
    {
        System.out.println(Kmu.padRight(f + ":", 25) + d.format(f));
    }
}
