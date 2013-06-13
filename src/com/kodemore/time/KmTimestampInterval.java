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
 * I implement an interval for timestamps.  I define a start and end
 * timestamp with various convenience methods.
 */
public class KmTimestampInterval
    implements Comparable<KmTimestampInterval>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmTimestampInterval create(KmTimestamp start, KmTimestamp end)
    {
        KmTimestampInterval ti;
        ti = new KmTimestampInterval();
        ti.setStart(start);
        ti.setEnd(end);
        return ti;
    }

    //##################################################
    //# variables
    //##################################################

    public KmTimestamp _start;
    public KmTimestamp _end;

    //##################################################
    //# contructor
    //##################################################

    public KmTimestampInterval()
    {
        _start = null;
        _end = null;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmTimestamp getStart()
    {
        return _start;
    }

    public void setStart(KmTimestamp ts)
    {
        _start = ts;
    }

    public boolean hasStart()
    {
        return _start != null;
    }

    public boolean hasStart(KmTimestamp ts)
    {
        return Kmu.isEqual(getStart(), ts);
    }

    public KmTimestamp getEnd()
    {
        return _end;
    }

    public void setEnd(KmTimestamp ts)
    {
        _end = ts;
    }

    public boolean hasEnd()
    {
        return _end != null;
    }

    public boolean hasEnd(KmTimestamp ts)
    {
        return Kmu.isEqual(getEnd(), ts);
    }

    public KmDuration getDuration()
    {
        if ( isClosed() )
            return getStart().difference(getEnd());

        return null;
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isOpen()
    {
        return !hasStart() && !hasEnd();
    }

    public boolean isClosed()
    {
        return hasStart() && hasEnd();
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmTimestampInterval) )
            return false;

        KmTimestampInterval ti = (KmTimestampInterval)e;
        return ti.hasStart(getStart()) && ti.hasEnd(getEnd());
    }

    /**
     * Return a hashcode that combines the start and end date.
     */
    @Override
    public int hashCode()
    {
        if ( isClosed() )
            return getStart().hashCode() ^ getEnd().hashCode();

        if ( hasStart() )
            return getStart().hashCode();

        if ( hasEnd() )
            return getEnd().hashCode();

        return 0;
    }

    @Override
    public int compareTo(KmTimestampInterval e)
    {
        int i = getStart().compareTo(e.getStart());
        if ( i != 0 )
            return i;

        return getEnd().compareTo(e.getEnd());
    }

    //##################################################
    //# intersection
    //##################################################

    /**
     * @see containsInclusive
     */
    public boolean contains(KmTimestamp t)
    {
        return containsInclusive(t);
    }

    /**
     * Determine if this interval contains the time.  The test includes
     * the start and end dates.
     */
    public boolean containsInclusive(KmTimestamp t)
    {
        if ( t == null )
            return false;

        if ( hasStart() && getStart().isAfter(t) )
            return false;

        if ( hasEnd() && getEnd().isBefore(t) )
            return false;

        return true;
    }

    /**
     * Determine if this interval contains the time.  The test excludes
     * the start and end dates.
     */
    public boolean containsExclusive(KmTimestamp t)
    {
        if ( t == null )
            return false;

        if ( hasStart() && getStart().isOnOrAfter(t) )
            return false;

        if ( hasEnd() && getEnd().isOnOrBefore(t) )
            return false;

        return true;
    }

    public boolean contains(KmTimestampInterval ti)
    {
        return containsInclusive(ti);
    }

    public boolean containsInclusive(KmTimestampInterval ti)
    {
        if ( ti == null )
            return false;

        return containsInclusive(ti.getStart())
            && containsInclusive(ti.getEnd());
    }

    public boolean containsExclusive(KmTimestampInterval ti)
    {
        if ( ti == null )
            return false;

        return containsExclusive(ti.getStart())
            && containsExclusive(ti.getEnd());
    }

    public boolean intersects(KmTimestampInterval ti)
    {
        if ( ti == null )
            return false;

        if ( contains(ti.getStart()) )
            return true;

        if ( contains(ti.getEnd()) )
            return true;

        if ( ti.contains(getStart()) )
            return true;

        return false;
    }

    public KmTimestampInterval getIntersection(KmTimestampInterval ti)
    {
        KmTimestampInterval x = ti.getCopy();

        if ( x.contains(getStart()) )
            x.setStart(getStart());

        if ( x.contains(getEnd()) )
            x.setEnd(getEnd());

        return x;
    }

    //##################################################
    //# copy
    //##################################################

    public KmTimestampInterval getCopy()
    {
        KmTimestampInterval e = new KmTimestampInterval();

        if ( hasStart() )
            e.setStart(getStart());

        if ( hasEnd() )
            e.setEnd(getEnd());

        return e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return Kmu.format(getToStringFormat(), getStart(), getEnd());
    }

    public static String getToStringFormat()
    {
        return "%s - %s";
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String... args)
    {
        test(0, 0, 0, 0);
        test(0, 0, 1, 0);
        test(0, 0, 0, 1);
        test(0, 0, 1, 1);

        test(0, 9, 0, 0);
        test(0, 9, 1, 0);
        test(0, 9, 0, 1);
        test(0, 9, 1, 1);

        test(2, 0, 0, 0);
        test(2, 0, 1, 0);
        test(2, 0, 0, 1);
        test(2, 0, 1, 1);

    }

    public static void test(int start1, int end1, int start2, int end2)
    {
        KmTimestamp startTimestamp1 = null;
        KmTimestamp startTimestamp2 = null;
        if ( start1 > 0 )
            startTimestamp1 = KmTimestamp.create(KmDate.create(2000, 1, start1));
        if ( start2 > 0 )
            startTimestamp2 = KmTimestamp.create(KmDate.create(2000, 2, start2));

        KmTimestamp endTimestamp1 = null;
        KmTimestamp endTimestamp2 = null;
        if ( end1 > 0 )
            endTimestamp1 = KmTimestamp.create(KmDate.create(2000, 1, end1));
        if ( end2 > 0 )
            endTimestamp2 = KmTimestamp.create(KmDate.create(2000, 2, end2));

        KmTimestampInterval ts1 = KmTimestampInterval.create(
            startTimestamp1,
            endTimestamp1);
        KmTimestampInterval ts2 = KmTimestampInterval.create(
            startTimestamp2,
            endTimestamp2);

        System.out.println("KmTimestampInterval.test() : 263");
        System.out.println("    ts1: " + ts1);
        System.out.println("    ts2: " + ts2);
        System.out.println("    ts1.intersects(ts2): " + ts1.intersects(ts2));
        System.out.println("    ts2.intersects(ts1): " + ts2.intersects(ts1));

    }

}
