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

import java.math.BigDecimal;

import com.kodemore.utility.KmString;

public class KmQuantity
    extends KmFixedDecimal<KmQuantity>
{
    //##################################################
    //# constants
    //##################################################

    public static final KmQuantity ZERO               = new KmQuantity(0);
    public static final KmQuantity ONE                = new KmQuantity(1);

    public static final int        SCALE              = 1;
    public static final int        DATABASE_PRECISION = 21;

    //##################################################
    //# static 
    //##################################################

    public static KmQuantity parse(String s)
    {
        try
        {
            KmString m = KmString.create(s.trim());
            if ( m.isEmpty() || m.isNull() )
                return null;

            return new KmQuantity(new BigDecimal(s));
        }
        catch ( RuntimeException ex )
        {
            return null;
        }
    }

    public static String getDisplayString(KmQuantity q)
    {
        if ( q == null )
            return "";

        return q.getDisplayString();
    }

    public static boolean validate(String s)
    {
        return parse(s) != null;
    }

    //##################################################
    //# constructor
    //##################################################

    public KmQuantity()
    {
        super(0.0);
    }

    public KmQuantity(double value)
    {
        super(value);
    }

    public KmQuantity(int value)
    {
        super(value);
    }

    protected KmQuantity(BigDecimal value)
    {
        super(value);
    }

    //##################################################
    //# multiply
    //##################################################

    public KmQuantity multiplySafe(KmFixedDecimal<?> e)
    {
        KmQuantity value;

        try
        {
            value = multiply(e);
        }
        catch ( RuntimeException ex )
        {
            value = null;
        }

        return value;
    }

    //##################################################
    //# policy
    //##################################################

    @Override
    public int getScale()
    {
        return SCALE;
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmQuantity newFixed(BigDecimal value)
    {
        return new KmQuantity(value);
    }

    @Override
    public KmQuantity getZero()
    {
        return ZERO;
    }

    //##################################################
    //# database
    //##################################################

    public static KmQuantity fromTenths(Long e)
    {
        if ( e == null )
            return null;

        return new KmQuantity(e).divide(10);
    }

    public static Long toTenths(KmQuantity e)
    {
        if ( e == null )
            return null;

        BigDecimal ten = new BigDecimal(10);
        return e.toBigDecimal().multiply(ten).longValue();
    }
}
