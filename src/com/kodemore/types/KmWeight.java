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

public class KmWeight
    implements KmReadOnlyIF, KmCopyIF, Comparable<KmWeight>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmWeight createKilograms(double d)
    {
        return new KmWeight(d, KmWeightUnit.KILOGRAM);
    }

    //##################################################
    //# variables
    //##################################################

    private double       _value;
    private KmWeightUnit _unit;
    private boolean      _readOnly;

    //##################################################
    //# constructor
    //##################################################

    public KmWeight()
    {
        this(0, KmWeightUnit.KILOGRAM);
    }

    public KmWeight(double d, KmWeightUnit u)
    {
        _value = d;
        _unit = u;
    }

    //##################################################
    //# accessing
    //##################################################

    public double getValue()
    {
        return _value;
    }

    public double getValue(KmWeightUnit u)
    {
        return u.toLocal(this);
    }

    public void setValue(double d)
    {
        checkReadOnly();
        _value = d;
    }

    public void setValue(double d, KmWeightUnit u)
    {
        checkReadOnly();
        _value = d;
        _unit = u;
    }

    public KmWeightUnit getUnit()
    {
        return _unit;
    }

    public void setUnit(KmWeightUnit e)
    {
        checkReadOnly();
        _unit = e;
    }

    //##################################################
    //# convenience
    //##################################################

    public double getKilogramValue()
    {
        return getValue(KmWeightUnit.KILOGRAM);
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

    private void checkReadOnly()
    {
        if ( _readOnly )
            throw new KmReadOnlyException(this);
    }

    //##################################################
    //# copy
    //##################################################

    @Override
    public KmCopyIF getCopy()
    {
        return new KmWeight(_value, _unit);
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return e instanceof KmWeight && compareTo((KmWeight)e) == 0;
    }

    @Override
    public int hashCode()
    {
        return (int)_value;
    }

    @Override
    public int compareTo(KmWeight e)
    {
        double d1 = _value;
        double d2 = _unit.toLocal(e);

        if ( d1 < d2 )
            return -1;

        if ( d1 > d2 )
            return 1;

        return 0;
    }

    //##################################################
    //# format
    //##################################################

    public String formatKilograms()
    {
        return format(KmWeightUnit.KILOGRAM);
    }

    public String format(KmWeightUnit u)
    {
        double d = getValue(u);
        return Kmu.formatDouble(d, 4);
    }

    //##################################################
    //# conversion
    //##################################################

    public KmWeight toUnit(KmWeightUnit u)
    {
        return new KmWeight(getValue(u), u);
    }
}
