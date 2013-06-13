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
import java.util.Iterator;

import com.kodemore.utility.KmReadOnlyException;
import com.kodemore.utility.KmReadOnlyIF;
import com.kodemore.utility.Kmu;

/**
 * I implement an interval for dates.  I define a start and end
 * date with various convenience methods.
 */
public class KmDateInterval
    implements KmReadOnlyIF, Comparable<KmDateInterval>, Serializable, Iterable<KmDate>
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmDateInterval create(KmDate start, KmDate end)
    {
        KmDateInterval di;
        di = new KmDateInterval();
        di.setStart(start);
        di.setEnd(end);
        return di;
    }

    //##################################################
    //# variables
    //##################################################

    private KmDate  _start;
    private KmDate  _end;
    private boolean _readOnly;

    //##################################################
    //# contructor
    //##################################################

    public KmDateInterval()
    {
        _start = null;
        _end = null;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmDate getStart()
    {
        return _start;
    }

    public void setStart(KmDate d)
    {
        _start = d;
    }

    public boolean hasStart()
    {
        return _start != null;
    }

    public boolean hasStart(KmDate d)
    {
        return Kmu.isEqual(getStart(), d);
    }

    public KmDate getEnd()
    {
        return _end;
    }

    public void setEnd(KmDate d)
    {
        _end = d;
    }

    public boolean hasEnd()
    {
        return _end != null;
    }

    public boolean hasEnd(KmDate d)
    {
        return Kmu.isEqual(getEnd(), d);
    }

    @Override
    public Iterator<KmDate> iterator()
    {
        if ( !isClosed() )
            throw new RuntimeException("Cannot create an iterator on an open interval");

        return new Iterator<KmDate>()
        {
            private KmDate _next = getStart();

            @Override
            public boolean hasNext()
            {
                return _next.isOnOrBefore(getEnd());
            }

            @Override
            public KmDate next()
            {
                KmDate d = _next;
                _next = _next.addDay();
                return d;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
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

    public boolean isNotClosed()
    {
        return !isClosed();
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmDateInterval) )
            return false;

        KmDateInterval di = (KmDateInterval)e;
        return hasStart(di.getStart()) && hasEnd(di.getEnd());
    }

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
    public int compareTo(KmDateInterval e)
    {
        int i = getStart().compareTo(e.getStart());
        if ( i != 0 )
            return i;
        return getEnd().compareTo(e.getEnd());
    }

    //##################################################
    //# testing
    //##################################################

    public boolean contains(KmDate d)
    {
        return containsInclusive(d);
    }

    public boolean containsInclusive(KmDate d)
    {
        if ( d == null )
            return false;
        if ( hasStart() && getStart().isAfter(d) )
            return false;
        if ( hasEnd() && getEnd().isBefore(d) )
            return false;
        return true;
    }

    public boolean containsExclusive(KmDate d)
    {
        if ( d == null )
            return false;
        if ( hasStart() && getStart().isOnOrAfter(d) )
            return false;
        if ( hasEnd() && getEnd().isOnOrBefore(d) )
            return false;
        return true;
    }

    public boolean contains(KmDateInterval di)
    {
        return containsInclusive(di);
    }

    public boolean containsInclusive(KmDateInterval di)
    {
        if ( di == null )
            return false;
        return containsInclusive(di.getStart()) && containsInclusive(di.getEnd());
    }

    public boolean containsExclusive(KmDateInterval di)
    {
        if ( di == null )
            return false;
        return containsExclusive(di.getStart()) && containsExclusive(di.getEnd());
    }

    public boolean intersects(KmDateInterval di)
    {
        if ( di == null )
            return false;

        if ( contains(di.getStart()) )
            return true;

        if ( contains(di.getEnd()) )
            return true;

        if ( di.contains(getStart()) )
            return true;

        return false;
    }

    public KmDateInterval getIntersection(KmDateInterval di)
    {
        if ( !intersects(di) )
            return null;

        KmDateInterval x = di.getCopy();

        if ( x.contains(getStart()) )
            x.setStart(getStart());

        if ( x.contains(getEnd()) )
            x.setEnd(getEnd());

        return x;
    }

    //##################################################
    //# utility
    //##################################################

    public int getDays()
    {
        return _end.getOrdinal() - _start.getOrdinal() + 1;
    }

    public int getWeeks()
    {
        return getWholeWeeks();
    }

    public int getWholeWeeks()
    {
        return getDays() / 7;
    }

    public int getPartialWeeks()
    {
        int n = getWholeWeeks();
        if ( getDays() % 7 != 0 )
            n++;
        return n;
    }

    //##################################################
    //# copy
    //##################################################

    public KmDateInterval getCopy()
    {
        KmDateInterval e = new KmDateInterval();

        if ( hasStart() )
            e.setStart(getStart());

        if ( hasEnd() )
            e.setEnd(getEnd());

        return e;
    }

    //##################################################
    //# read only
    //##################################################

    @Override
    public boolean isReadOnly()
    {
        return _readOnly;
    }

    @Override
    public void setReadOnly(boolean b)
    {
        _readOnly = b;
    }

    public void checkReadOnly()
    {
        if ( _readOnly )
            throw new KmReadOnlyException(this);
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
