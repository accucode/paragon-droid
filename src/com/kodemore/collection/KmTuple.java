/*
  Copyright (c) 2005-2012 Wyatt Love

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

package com.kodemore.collection;

import java.util.Map.Entry;

import com.kodemore.utility.Kmu;


public class KmTuple<K, V>
{
    //##################################################
    //# instance creation
    //##################################################

    public static <A, B> KmTuple<A,B> create(Entry<A,B> e)
    {
        return new KmTuple<A,B>(e.getKey(), e.getValue());
    }

    public static <A, B> KmTuple<A,B> create(A key, B value)
    {
        return new KmTuple<A,B>(key, value);
    }

    //##################################################
    //# variables
    //##################################################

    private K _key;
    private V _value;

    //##################################################
    //# constructor
    //##################################################

    public KmTuple()
    {
        // none
    }

    public KmTuple(K key, V value)
    {
        _key = key;
        _value = value;
    }

    //##################################################
    //# accessing
    //##################################################

    public K getKey()
    {
        return _key;
    }

    public void setKey(K e)
    {
        _key = e;
    }

    public V getValue()
    {
        return _value;
    }

    public void setValue(V e)
    {
        _value = e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return Kmu.toDisplay(getValue()) + " -> " + Kmu.toDisplay(getValue());
    }
}
