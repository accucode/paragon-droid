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

package com.kodemore.stf;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

/**
 * I represent a single attribute.
 */
public class KmStfAttribute
{
    //##################################################
    //# variables
    //##################################################

    private String _key;
    private String _value;

    //##################################################
    //# key
    //##################################################

    public String getKey()
    {
        return _key;
    }

    public void setKey(String e)
    {
        _key = e;
    }

    public boolean hasKey()
    {
        return Kmu.hasValue(_key);
    }

    public boolean hasKey(String e)
    {
        return Kmu.isEqual(_key, e);
    }

    //##################################################
    //# value
    //##################################################

    public String getValue()
    {
        return _value;
    }

    public void setValue(String e)
    {
        _value = e;
    }

    public void appendValue(String e)
    {
        if ( e == null )
            return;

        if ( _value == null )
            _value = e;
        else
            _value += e;
    }

    public boolean hasValue()
    {
        return Kmu.hasValue(_value);
    }

    public boolean hasValue(String e)
    {
        return Kmu.isEqual(_value, e);
    }

    public KmList<String> getValueLines()
    {
        if ( !hasValue() )
            return new KmList<String>();

        return Kmu.getLines(getValue());
    }
}
