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

import java.io.Serializable;

import com.kodemore.utility.KmCopyIF;
import com.kodemore.utility.KmReadOnlyException;
import com.kodemore.utility.KmReadOnlyIF;
import com.kodemore.utility.Kmu;

/**
 * I represent weight in kilograms. I provide utility methods for
 * displaying weights at the proper scale and formatting weight to
 * a database scale (independent of the display scale).
 *
 * IMPORTANT: This class should NOT be used when running calculations
 * against large sets of data, as I wrap the KmDecimal. See KmDecimal
 * for more details.
 *
 */
public abstract class KmAbstractDouble
    implements KmReadOnlyIF, KmCopyIF, Comparable<KmAbstractDouble>, Cloneable,
    Serializable
{
    //##################################################
    //# constants (template keys)
    //##################################################

    public static final String VALUE_KEY          = "value";
    public static final String DATABASE_SCALE_KEY = "databaseScale";
    public static final String DISPLAY_SCALE_KEY  = "displayScale";

    //##################################################
    //# variables
    //##################################################

    private double             _value;
    private boolean            _readOnly;

    //##################################################
    //# constructor
    //##################################################

    public KmAbstractDouble()
    {
        this(0);
    }

    public KmAbstractDouble(double e)
    {
        _value = e;
    }

    public KmAbstractDouble(int e)
    {
        _value = e;
    }

    //##################################################
    //# abstract
    //##################################################

    public abstract int getDatabaseScale();

    public int getDisplayScale()
    {
        return getDatabaseScale();
    }

    //##################################################
    //# accessing
    //##################################################

    public double getValue()
    {
        return _value;
    }

    public void setValue(KmAbstractDouble e)
    {
        setValue(e.getValue());
    }

    public void setValue(int e)
    {
        setValue((double)e);
    }

    public void setValue(double e)
    {
        checkReadOnly();
        _value = e;
    }

    public void beZero()
    {
        setValue(0);
    }

    //##################################################
    //# math
    //##################################################

    public void add(double e)
    {
        checkReadOnly();
        _value += e;
    }

    public void add(KmAbstractDouble e)
    {
        checkReadOnly();
        if ( e == null )
            return;
        _value += e.getValue();
    }

    public void subtract(double e)
    {
        checkReadOnly();
        _value -= e;
    }

    public void subtract(KmAbstractDouble e)
    {
        checkReadOnly();
        if ( e == null )
            return;
        _value -= e.getValue();
    }

    public void multiply(double d)
    {
        checkReadOnly();
        _value *= d;
    }

    public void divide(double d)
    {
        checkReadOnly();
        _value /= d;
    }

    public void negate()
    {
        _value = -_value;
    }

    public boolean isPositive()
    {
        return _value > 0;
    }

    public boolean isNegative()
    {
        return _value < 0;
    }

    public boolean isZero()
    {
        return _value == 0;
    }

    public boolean isPositiveOrZero()
    {
        return _value >= 0;
    }

    public boolean isNegativeOrZero()
    {
        return _value <= 0;
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object o)
    {
        if ( !(o instanceof KmAbstractDouble) )
            return false;

        return Kmu.isNearEqual(((KmAbstractDouble)o)._value, _value);
    }

    @Override
    public int hashCode()
    {
        return (int)_value;
    }

    @Override
    public int compareTo(KmAbstractDouble e)
    {
        return Kmu.compare(_value, e.getValue());
    }

    //##################################################
    //# testing
    //##################################################

    /**
     * Determine if my value matches the argument within some tolerance.
     */
    public boolean isClose(KmAbstractDouble e, double tolerance)
    {
        if ( e == null )
            return false;
        return isClose(e.getValue(), tolerance);
    }

    /**
     * Determine if my value matches the argument within some tolerance.
     */
    public boolean isClose(double d, double tolerance)
    {
        return Kmu.isEqual(_value, d, tolerance);
    }

    //##################################################
    //# copy
    //##################################################

    @Override
    public KmAbstractDouble getCopy()
    {
        KmAbstractDouble e = Kmu.getSerializedCopy(this);
        e.setReadOnly(false);
        return e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return getDisplayString(getDatabaseScale());
    }

    public String getDatabaseString()
    {
        return getDisplayString(getDatabaseScale(), false);
    }

    public String getDisplayString()
    {
        return getDisplayString(getDisplayScale());
    }

    public String getDisplayString(int displayScale)
    {
        return getDisplayString(displayScale, true);
    }

    public String getDisplayString(int displayScale, boolean commas)
    {
        return Kmu.formatDouble(_value, displayScale, commas);
    }

    //##################################################
    //# read only
    //##################################################

    @Override
    public boolean isReadOnly()
    {
        return _readOnly;
    }

    @Override
    public void setReadOnly(boolean b)
    {
        _readOnly = b;
    }

    public void checkReadOnly()
    {
        if ( _readOnly )
            throw new KmReadOnlyException(this);
    }

}
