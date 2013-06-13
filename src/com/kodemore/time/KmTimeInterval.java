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
 * I implement an interval for times.  I define a start and end
 * time with various convenience methods.
 */
public class KmTimeInterval
    implements Comparable<KmTimeInterval>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmTimeInterval createTimeInterval(KmTime start, KmTime end)
    {
        KmTimeInterval ti;
        ti = new KmTimeInterval();
        ti.setStart(start);
        ti.setEnd(end);
        return ti;
    }

    //##################################################
    //# variables
    //##################################################

    private KmTime _start;
    private KmTime _end;

    //##################################################
    //# contructor
    //##################################################

    public KmTimeInterval()
    {
        _start = null;
        _end = null;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmTime getStart()
    {
        return _start;
    }

    public void setStart(KmTime t)
    {
        _start = t;
    }

    public boolean hasStart()
    {
        return _start != null;
    }

    public boolean hasStart(KmTime t)
    {
        return Kmu.isEqual(getStart(), t);
    }

    public KmTime getEnd()
    {
        return _end;
    }

    public void setEnd(KmTime t)
    {
        _end = t;
    }

    public boolean hasEnd()
    {
        return _end != null;
    }

    public boolean hasEnd(KmTime t)
    {
        return Kmu.isEqual(getEnd(), t);
    }

    //##################################################
    //# testing
    //##################################################

    /**
     * This is true if both the start and end dates are null.
     */
    public boolean isOpen()
    {
        return !hasStart() && !hasEnd();
    }

    /**
     * This is true if both the start and end dates are NOT null.
     */
    public boolean isClosed()
    {
        return hasStart() && hasEnd();
    }

    //##################################################
    //# compare
    //##################################################

    /**
     * Two time intervals are considered equal if both their
     * start and end dates match.
     */
    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmTimeInterval) )
            return false;

        KmTimeInterval ti = (KmTimeInterval)e;
        return Kmu.isEqual(getStart(), ti.getStart())
            && Kmu.isEqual(getEnd(), ti.getEnd());
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
    public int compareTo(KmTimeInterval e)
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
    public boolean contains(KmTime t)
    {
        return containsInclusive(t);
    }

    /**
     * Determine if this interval contains the time.  The test includes
     * the start and end dates.
     */
    public boolean containsInclusive(KmTime t)
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
    public boolean containsExclusive(KmTime t)
    {
        if ( t == null )
            return false;
        if ( hasStart() && getStart().isOnOrAfter(t) )
            return false;
        if ( hasEnd() && getEnd().isOnOrBefore(t) )
            return false;
        return true;
    }

    public boolean contains(KmTimeInterval ti)
    {
        return containsInclusive(ti);
    }

    public boolean containsInclusive(KmTimeInterval ti)
    {
        if ( ti == null )
            return false;
        return containsInclusive(ti.getStart())
            && containsInclusive(ti.getEnd());
    }

    public boolean containsExclusive(KmTimeInterval ti)
    {
        if ( ti == null )
            return false;
        return containsExclusive(ti.getStart())
            && containsExclusive(ti.getEnd());
    }

    public boolean intersects(KmTimeInterval ti)
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

    public KmTimeInterval getIntersection(KmTimeInterval ti)
    {
        KmTimeInterval x = ti.getCopy();

        if ( x.contains(getStart()) )
            x.setStart(getStart());

        if ( x.contains(getEnd()) )
            x.setEnd(getEnd());

        return x;
    }

    //##################################################
    //# copy
    //##################################################

    public KmTimeInterval getCopy()
    {
        KmTimeInterval e;
        e = new KmTimeInterval();

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
        return "%s..%s";
    }
}
