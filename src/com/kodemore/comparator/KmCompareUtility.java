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

import com.kodemore.utility.KmUnchecked;

/**
 * I provide static utility methods for comparing objects.
 */
public abstract class KmCompareUtility
{
    public static int compare(Comparable<?> a, Comparable<?> b, boolean nullsOnTop)
    {
        if ( a == null || b == null )
            return compareNulls(a, b, nullsOnTop);

        return KmUnchecked.compare(a, b);
    }

    public static int compare(String a, String b, boolean nullsOnTop)
    {
        if ( a == null || b == null )
            return compareNulls(a, b, nullsOnTop);

        return a.compareToIgnoreCase(b);
    }

    public static int compare(Boolean a, Boolean b, boolean nullsOnTop)
    {
        if ( a == null || b == null )
            return compareNulls(a, b, nullsOnTop);

        return b.compareTo(a);
    }

    public static int compare(Integer a, Integer b, boolean nullsOnTop)
    {
        if ( a == null || b == null )
            return compareNulls(a, b, nullsOnTop);

        return a.compareTo(b);
    }

    public static int compare(Double a, Double b, boolean nullsOnTop)
    {
        if ( a == null || b == null )
            return compareNulls(a, b, nullsOnTop);

        return a.compareTo(b);
    }

    public static int compareNulls(Object a, Object b, boolean nullsOnTop)
    {
        if ( a == null && b == null )
            return 0;

        int i = a == null
            ? -1
            : 1;

        return nullsOnTop
            ? i
            : -i;
    }
}
