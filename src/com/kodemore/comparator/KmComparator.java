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

package com.kodemore.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * I provide an abstract superclass for comparators and implement
 * a few convenience methods.
 */
public abstract class KmComparator<T>
    implements Comparator<T>, Serializable
{
    //##################################################
    //# variables
    //##################################################

    private boolean _nullsOnTop;

    //##################################################
    //# constructor
    //##################################################

    public KmComparator()
    {
        init();
    }

    //##################################################
    //# init
    //##################################################

    protected void init()
    {
        // subclass
    }

    //##################################################
    //# nulls on top
    //##################################################

    public boolean getNullsOnTop()
    {
        return _nullsOnTop;
    }

    public void setNullsOnTop(boolean e)
    {
        _nullsOnTop = e;
    }

    public void setNullsOnTop()
    {
        setNullsOnTop(true);
    }

    public void setNullsOnBottom()
    {
        setNullsOnTop(false);
    }

    //##################################################
    //# abstract
    //##################################################

    /**
     * Each subclass must override this method to define the
     * appropriate comparison logic.
     */
    @Override
    public abstract int compare(T a, T b);

    /**
     * By default it is usually acceptable to use the default equals
     * method defined in Object.
     */
    @Override
    public boolean equals(Object e)
    {
        return super.equals(e);
    }

    /**
     * By default it is usually acceptable to use the default hashCode
     * method defined in Object.
     */
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Return a comparator that sorts elements in the reverse order.
     */
    public KmComparator<T> reverse()
    {
        return new KmReversingComparator<T>(this);
    }

    /**
     * Return a comparator that can handle nulls.
     */
    public KmComparator<T> toNullComparator()
    {
        return new KmNullComparator<T>(this);
    }

    //##################################################
    //# support
    //##################################################

    protected int c(Comparable<?> a, Comparable<?> b)
    {
        return KmCompareUtility.compare(a, b, getNullsOnTop());
    }

    protected int c(String a, String b)
    {
        return KmCompareUtility.compare(a, b, getNullsOnTop());
    }

    protected int c(Boolean a, Boolean b)
    {
        return KmCompareUtility.compare(a, b, getNullsOnTop());
    }

    protected int c(Integer a, Integer b)
    {
        return KmCompareUtility.compare(a, b, getNullsOnTop());
    }

    protected int c(Double a, Double b)
    {
        return KmCompareUtility.compare(a, b, getNullsOnTop());
    }
}
