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

package com.kodemore.types;

import java.util.Iterator;

/**
 * An immutable iterable that can iterate either forward or backward.
 */
public class KmIntegerIterable
    implements Iterable<Integer>
{
    //##################################################
    //# variables
    //##################################################

    private int _start;
    private int _end;

    //##################################################
    //# constructor
    //##################################################

    public KmIntegerIterable(int start, int end)
    {
        _start = start;
        _end = end;
    }

    //##################################################
    //# accessing
    //##################################################

    public Integer getStart()
    {
        return _start;
    }

    public Integer getEnd()
    {
        return _end;
    }

    //##################################################
    //# iterable
    //##################################################

    @Override
    public Iterator<Integer> iterator()
    {
        if ( getStart() < getEnd() )
            return forwardIterator();

        return backwardIterator();
    }

    private Iterator<Integer> forwardIterator()
    {
        return new Iterator<Integer>()
        {
            private int _next = _start;

            @Override
            public boolean hasNext()
            {
                return _next <= _end;
            }

            @Override
            public Integer next()
            {
                return _next++;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Iterator<Integer> backwardIterator()
    {
        return new Iterator<Integer>()
        {
            private int _next = _start;

            @Override
            public boolean hasNext()
            {
                return _next >= _end;
            }

            @Override
            public Integer next()
            {
                return _next--;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

}
