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

/**
 * I act as a value holder for the double primitive.  You might use this
 * class instead of Java.lang.Double when you want to be able to change
 * the value.
 */
public class KmDouble
{
    //##################################################
    //# variables
    //##################################################

    private double _value;

    //##################################################
    //# constructor
    //##################################################

    public KmDouble()
    {
        set(0);
    }

    public KmDouble(double e)
    {
        set(e);
    }

    //##################################################
    //# accessing
    //##################################################

    public double get()
    {
        return _value;
    }

    public void set(double e)
    {
        _value = e;
    }

    public boolean hasValue()
    {
        return Double.isNaN(_value);
    }

    public boolean hasValue(double e)
    {
        return Double.isNaN(e)
            ? Double.isNaN(_value)
            : _value == e;
    }

    public void clear()
    {
        _value = Double.NaN;
    }

    //##################################################
    //# math
    //##################################################

    public void add(double d)
    {
        _value += d;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return _value + "";
    }
}
