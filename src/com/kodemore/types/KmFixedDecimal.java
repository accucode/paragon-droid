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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmDisplayStringIF;
import com.kodemore.utility.Kmu;

/**
 * I represent decimal values that have a fixed number of places
 * to the right of the decimal, but which have unlimited precision
 * (unlimited digits to the left of the decimal).
 * 
 * IMPORTANT: This class should NOT be used when running calculations
 * against large sets of data, as I wrap the KmDecimal. See KmDecimal
 * for more details.
 */
public abstract class KmFixedDecimal<T extends KmFixedDecimal<?>>
    implements KmDisplayStringIF, Comparable<KmFixedDecimal<?>>, Cloneable, Serializable
{
    //##################################################
    //# variables
    //##################################################

    private BigDecimal _value;

    //##################################################
    //# constructor
    //##################################################

    public KmFixedDecimal(double value)
    {
        _value = newBigDecimal(value);
    }

    public KmFixedDecimal(int value)
    {
        _value = newBigDecimal(value);
    }

    public KmFixedDecimal(long value)
    {
        _value = newBigDecimal(value);
    }

    protected KmFixedDecimal(BigDecimal value)
    {
        _value = newBigDecimal(value);
    }

    //##################################################
    //# support
    //##################################################

    protected abstract T getZero();

    protected abstract T newFixed(BigDecimal value);

    protected T newFixed(double value)
    {
        return newFixed(newBigDecimal(value));
    }

    protected T newFixed(int value)
    {
        return newFixed(newBigDecimal(value));
    }

    private BigDecimal newBigDecimal(double value)
    {
        return newBigDecimal(new BigDecimal(value));
    }

    private BigDecimal newBigDecimal(int value)
    {
        return newBigDecimal(new BigDecimal(value));
    }

    private BigDecimal newBigDecimal(long value)
    {
        return newBigDecimal(new BigDecimal(value));
    }

    private BigDecimal newBigDecimal(BigDecimal value)
    {
        return scale(value);
    }

    private BigDecimal scale(BigDecimal e)
    {
        return e.setScale(getScale(), getRoundingMode());
    }

    //##################################################
    //# policy
    //##################################################

    public abstract int getScale();

    public int getDisplayScale()
    {
        return getScale();
    }

    public RoundingMode getRoundingMode()
    {
        return RoundingMode.HALF_UP;
    }

    //##################################################
    //# accessing
    //##################################################

    public int toIntValue()
    {
        return _value.intValue();
    }

    public long toLongValue()
    {
        return _value.longValue();
    }

    public double toDoubleValue()
    {
        return _value.doubleValue();
    }

    public BigDecimal toBigDecimal()
    {
        return _value;
    }

    //##################################################
    //# add
    //##################################################

    @SuppressWarnings("unchecked")
    public T add(KmFixedDecimal<?> e)
    {
        if ( e == null )
            return (T)this;

        return newFixed(_value.add(e.toBigDecimal()));
    }

    public T add(double e)
    {
        return add(newFixed(e));
    }

    public T add(int e)
    {
        return add(newFixed(e));
    }

    //##################################################
    //# subtract
    //##################################################

    public T subtract(KmFixedDecimal<?> e)
    {
        return newFixed(_value.subtract(e.toBigDecimal()));
    }

    public T subtract(double e)
    {
        return subtract(newFixed(e));
    }

    public T subtract(int e)
    {
        return subtract(newFixed(e));
    }

    //##################################################
    //# multiply
    //##################################################

    public T multiply(KmFixedDecimal<?> e)
    {
        return newFixed(_value.multiply(e.toBigDecimal()));
    }

    public T multiply(double e)
    {
        return multiply(newFixed(e));
    }

    public T multiply(int e)
    {
        return multiply(newFixed(e));
    }

    public T negate()
    {
        return multiply(-1);
    }

    //##################################################
    //# divide
    //##################################################

    public T divide(KmFixedDecimal<?> e)
    {
        return newFixed(_value.divide(e.toBigDecimal(), getRoundingMode()));
    }

    public T divide(double e)
    {
        return divide(newFixed(e));
    }

    public T divide(int e)
    {
        return divide(newFixed(e));
    }

    //##################################################
    //# misc
    //##################################################

    @SuppressWarnings("unchecked")
    public T constrain(T min, T max)
    {
        if ( isLessThan(min) )
            return min;

        if ( isGreaterThan(max) )
            return max;

        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public T getPositiveOrZero()
    {
        if ( isPositive() )
            return (T)this;
        return getZero();
    }

    @SuppressWarnings("unchecked")
    public T getMinimum(T e)
    {
        if ( isLessThan(e) )
            return (T)this;
        return e;
    }

    @SuppressWarnings("unchecked")
    public T getMaximum(T e)
    {
        if ( isGreaterThan(e) )
            return (T)this;
        return e;
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object o)
    {
        if ( o instanceof KmFixedDecimal<?> )
        {
            KmFixedDecimal<?> e = (KmFixedDecimal<?>)o;
            return e._value.equals(_value);
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }

    @Override
    public int compareTo(KmFixedDecimal<?> e)
    {
        return _value.compareTo(e.toBigDecimal());
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isZero()
    {
        return _value.signum() == 0;
    }

    public boolean isNotZero()
    {
        return !isZero();
    }

    public boolean isOne()
    {
        if ( isIntegerValue() )
            return _value.intValueExact() == 1;

        return false;
    }

    public boolean isNotOne()
    {
        return !isOne();
    }

    public boolean isNegative()
    {
        return _value.signum() == -1;
    }

    public boolean isPositive()
    {
        return _value.signum() == 1;
    }

    public boolean isNegativeOrZero()
    {
        return !isPositive();
    }

    public boolean isPositiveOrZero()
    {
        return !isNegative();
    }

    public boolean isGreaterThan(KmFixedDecimal<?> e)
    {
        return compareTo(e) > 0;
    }

    public boolean isGreaterThanOrEqualTo(KmFixedDecimal<?> e)
    {
        return compareTo(e) >= 0;
    }

    public boolean isLessThan(KmFixedDecimal<?> e)
    {
        return compareTo(e) < 0;
    }

    public boolean isLessThanOrEqualTo(KmFixedDecimal<?> e)
    {
        return compareTo(e) <= 0;
    }

    public boolean isIntegerValue()
    {
        try
        {
            toBigDecimal().intValueExact();
            return true;
        }
        catch ( ArithmeticException ex )
        {
            return false;
        }
    }

    //##################################################
    //# display
    //##################################################

    /**
     * Return the display string with the default scale
     */
    @Override
    public String toString()
    {
        return getDisplayString(getScale());
    }

    @Override
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
        return format(displayScale, commas);
    }

    //##################################################
    //# format
    //##################################################

    public String format(int scale, boolean commas)
    {
        return _format(_value, scale, commas);
    }

    private String format(BigDecimal source, int scale)
    {
        return _format(source, scale, true);
    }

    private String _format(BigDecimal source, int scale, boolean commas)
    {
        BigDecimal rounded;
        rounded = source.setScale(scale, getRoundingMode());

        String pattern;
        pattern = createPattern(scale, commas);

        DecimalFormat format;
        format = new DecimalFormat(pattern);

        return format.format(rounded);
    }

    private String createPattern(int scale, boolean commas)
    {
        String s = commas
            ? ",##0"
            : "0";

        if ( scale > 0 )
            s += ".";

        return s + getFractionPatternForScale(scale);
    }

    /**
     * Format a short version of this value.
     * If the value is less than a 1000 use the standard scale.
     * Otherwise, attempt to reduce the value to thousands, millions,
     * etc; with the associated abbreviation.
     * Examples:
     *      12.3 >> 12.3
     *      1,234.56 >> 1.2 K
     *      12,234,567.89 >> 1.2 M  
     */
    public String getShortDisplayString()
    {
        BigDecimal K = new BigDecimal("1000");
        BigDecimal temp = getRoundedValue();

        if ( isLessThan(temp.abs(), K) )
            return format(temp, getScale());

        for ( String suffix : getSuffixes() )
        {
            temp = temp.divide(K);

            if ( isLessThan(temp.abs(), K) )
                return format(temp, 1) + " " + suffix;
        }

        return "Overflow";
    }

    private KmList<String> getSuffixes()
    {
        KmList<String> v;
        v = new KmList<String>();
        v.add("K"); // thousand
        v.add("M"); // million
        v.add("B"); // billion
        v.add("T"); // trillion
        v.add("Q"); // quadrillion
        return v;
    }

    //##################################################
    //# utility
    //##################################################

    private boolean isLessThan(BigDecimal a, BigDecimal b)
    {
        return a.compareTo(b) < 0;
    }

    private BigDecimal getRoundedValue()
    {
        return getRoundedValue(getScale());
    }

    private BigDecimal getRoundedValue(int scale)
    {
        return _value.setScale(scale, getRoundingMode());
    }

    private String getFractionPatternForScale(int scale)
    {
        return Kmu.repeat('0', scale);
    }

}
