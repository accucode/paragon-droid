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

import com.kodemore.collection.KmList;

/**
 * 
 */
public class KmIntegerRange
    implements Iterable<Integer>
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmIntegerRange create(Integer start, Integer end)
    {
        KmIntegerRange e;
        e = new KmIntegerRange();
        e.setStart(start);
        e.setEnd(end);
        return e;
    }

    //##################################################
    //# variables
    //##################################################

    private Integer _start;
    private Integer _end;

    //##################################################
    //# accessing
    //##################################################

    public Integer getStart()
    {
        return _start;
    }

    public void setStart(Integer e)
    {
        _start = e;
    }

    public boolean hasStart()
    {
        return _start != null;
    }

    public Integer getEnd()
    {
        return _end;
    }

    public void setEnd(Integer e)
    {
        _end = e;
    }

    public boolean hasEnd()
    {
        return _end != null;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public boolean isBounded()
    {
        return hasStart() && hasEnd();
    }

    public KmList<Integer> toList()
    {
        KmList<Integer> v;
        v = new KmList<Integer>();
        v.addAll(iterator());
        return v;
    }

    @Override
    public Iterator<Integer> iterator()
    {
        return new KmIntegerIterable(getStart(), getEnd()).iterator();
    }
}
