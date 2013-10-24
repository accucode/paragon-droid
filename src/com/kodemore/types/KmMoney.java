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
import com.kodemore.view.KmMoneyField;

/**
 * I represent money.  Values are stored as fixed decimal, rounding
 * occurs to 2 decimals after each operation.
 */
public class KmMoney
    extends KmFixedDecimal<KmMoney>
{
    //##################################################
    //# constants
    //##################################################

    public static final KmMoney ZERO  = new KmMoney(0);

    private static final int    SCALE = KmMoneyField.SCALE;

    //##################################################
    //# static 
    //##################################################

    public static KmMoney parse(String s)
    {
        try
        {
            KmString m = KmString.create(s.trim());

            if ( m.isEmpty() || m.isNull() )
                return null;

            return new KmMoney(new BigDecimal(s));
        }
        catch ( RuntimeException ex )
        {
            return null;
        }
    }

    public static String getDisplayString(KmMoney e)
    {
        if ( e == null )
            return "";

        return e.getDisplayString();
    }

    public static boolean validate(String s)
    {
        return parse(s) != null;
    }

    //##################################################
    //# constructor
    //##################################################

    public KmMoney()
    {
        super(null);
    }

    public KmMoney(double e)
    {
        super(e);
    }

    public KmMoney(int e)
    {
        super(e);
    }

    public KmMoney(long e)
    {
        super(e);
    }

    public KmMoney(BigDecimal e)
    {
        super(e);
    }

    //##################################################
    //# multiply
    //##################################################

    public KmMoney multiplySafe(KmFixedDecimal<?> e)
    {
        KmMoney value;

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
    //# cents
    //##################################################

    public static KmMoney fromCents(Long cents)
    {
        if ( cents == null )
            return null;

        BigDecimal hundred = new BigDecimal(100);
        BigDecimal bd = new BigDecimal(cents).divide(hundred);
        return new KmMoney(bd);
    }

    public static Long toCents(KmMoney e)
    {
        if ( e == null )
            return null;

        return e.toCents();
    }

    public Long toCents()
    {
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal bd = toBigDecimal().multiply(hundred);
        return bd.longValue();
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmMoney newFixed(BigDecimal e)
    {
        return new KmMoney(e);
    }

    @Override
    public KmMoney getZero()
    {
        return ZERO;
    }

    @Override
    public String toString()
    {
        if ( toBigDecimal() == null )
            return "";

        return getDisplayString();
    }

}
