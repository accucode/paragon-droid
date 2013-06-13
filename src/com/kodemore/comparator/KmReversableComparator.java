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
 * I am used to switch the sort order back and forth on a comparator.
 */
public class KmReversableComparator<T>
    implements Comparator<T>, Serializable
{
    //##################################################
    //# variables
    //##################################################

    private Comparator<T> _comparator;
    private boolean       _reverse;

    //##################################################
    //# constructor
    //##################################################

    public KmReversableComparator()
    {
        _comparator = null;
        _reverse = false;
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

    public boolean getReverse()
    {
        return _reverse;
    }

    public void setReverse(boolean e)
    {
        _reverse = e;
    }

    public void reverse()
    {
        _reverse = !_reverse;
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public int compare(T a, T b)
    {
        return _reverse
            ? _comparator.compare(b, a)
            : _comparator.compare(a, b);
    }

}
