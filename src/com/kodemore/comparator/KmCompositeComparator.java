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

import java.util.Comparator;

import com.kodemore.collection.KmList;


/**
 * I provide a convenient way to combine existing comparator into
 * a composite.  For example: there may already be two comparators,
 * one for first name, and another for last name.  Using this composite
 * comparator, the logic from the existing comparators can be combined
 * to easily create a firstLast, or lastFirst comparator without having
 * to resort to the copy/paste approach.
 */
public class KmCompositeComparator<T>
    extends KmComparator<T>
{
    //##################################################
    //# instance creation
    //##################################################

    public static <T> KmCompositeComparator<T> createWith(Comparator<T> a)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(Comparator<T> a, Comparator<T> b)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c,
        Comparator<T> d)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        x.add(d);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c,
        Comparator<T> d,
        Comparator<T> e)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        x.add(d);
        x.add(e);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c,
        Comparator<T> d,
        Comparator<T> e,
        Comparator<T> f)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        x.add(d);
        x.add(e);
        x.add(f);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c,
        Comparator<T> d,
        Comparator<T> e,
        Comparator<T> f,
        Comparator<T> g)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        x.add(d);
        x.add(e);
        x.add(f);
        x.add(g);
        return x;
    }

    public static <T> KmCompositeComparator<T> createWith(
        Comparator<T> a,
        Comparator<T> b,
        Comparator<T> c,
        Comparator<T> d,
        Comparator<T> e,
        Comparator<T> f,
        Comparator<T> g,
        Comparator<T> h)
    {
        KmCompositeComparator<T> x;
        x = new KmCompositeComparator<T>();
        x.add(a);
        x.add(b);
        x.add(c);
        x.add(d);
        x.add(e);
        x.add(f);
        x.add(g);
        x.add(h);
        return x;
    }

    //##################################################
    //# variables
    //##################################################

    private KmList<Comparator<T>> _comparators;

    //##################################################
    //# constructor
    //##################################################

    public KmCompositeComparator()
    {
        _comparators = new KmList<Comparator<T>>();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<Comparator<T>> getComparators()
    {
        return _comparators;
    }

    public void setComparators(KmList<Comparator<T>> e)
    {
        _comparators = e;
    }

    public void add(Comparator<T> c)
    {
        _comparators.add(c);
    }

    public void addAll(Comparator<T>... arr)
    {
        int n = arr.length;
        for ( int i = 0; i < n; i++ )
            add(arr[i]);
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public int compare(T a, T b)
    {
        int i;
        for ( Comparator<T> e : _comparators )
        {
            i = e.compare(a, b);
            if ( i != 0 )
                return i;
        }
        return 0;
    }

}
