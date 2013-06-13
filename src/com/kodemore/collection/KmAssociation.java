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

public class KmAssociation<K, V>
{
    //##################################################
    //# variables
    //##################################################

    private K _key;
    private V _value;

    //##################################################
    //# constructor
    //##################################################

    public KmAssociation()
    {
        // none
    }

    public KmAssociation(K key, V value)
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

    public boolean hasKey(K e)
    {
        if ( _key == null )
            return e == null;

        return _key.equals(e);
    }

    public V getValue()
    {
        return _value;
    }

    public void setValue(V e)
    {
        _value = e;
    }

    public boolean hasValue(V e)
    {
        if ( _value == null )
            return e == null;

        return _value.equals(e);
    }

}
