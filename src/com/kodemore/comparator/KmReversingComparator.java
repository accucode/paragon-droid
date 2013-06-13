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

import com.kodemore.utility.KmUnchecked;


/**
 * I am used to sort elements in the reverse order as the original
 * comparator.
 */
public class KmReversingComparator<T>
    extends KmComparator<T>
{
    //##################################################
    //# variables
    //##################################################

    private Comparator<T> _comparator;

    //##################################################
    //# constructor
    //##################################################

    public KmReversingComparator(Comparator<T> c)
    {
        _comparator = c;
    }

    //##################################################
    //# accessing
    //##################################################

    public Comparator<T> getComparator()
    {
        return _comparator;
    }

    public void setComparator(Comparator<T> e)
    {
        _comparator = e;
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public int compare(T a, T b)
    {
        return KmUnchecked.compare(_comparator, b, a);
    }

}
