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
 * I represent weight in kilograms. I provide utility methods for
 * displaying weights at the proper scale and formatting weight to
 * a database scale (independent of the display scale).
 *
 * IMPORTANT: This class should NOT be used when running calculations
 * against large sets of data, as I wrap the KmDecimal. See KmDecimal
 * for more details.
 *
 */
public abstract class KmFixedDecimal<T extends KmFixedDecimal<?>>
    implements KmDisplayStringIF, Comparable<KmFixedDecimal<?>>, Cloneable, Serializable
{
    //##################################################
    //# constants (template keys)
    //##################################################

    public static final String      VALUE_KEY          = "value";
    public static final String      DATABASE_SCALE_KEY = "databaseScale";
    public static final String      DISPLAY_SCALE_KEY  = "displayScale";

    private static final BigDecimal THOUSAND           = new BigDecimal("1000");

    //##################################################
    //# variables
    //##################################################

    private BigDecimal              _value;

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
        BigDecimal e = scale(value);
        validate(e);
        return e;
    }

    private void validate(BigDecimal e)
    {
        if ( e.precision() > getMaxPrecision() )
            Kmu.fatal(
                "%s with precision of %s exceeds maximum precision of %s.",
                e.toPlainString(),
                e.precision(),
                getMaxPrecision());
    }

    private BigDecimal scale(BigDecimal e)
    {
        return e.setScale(getScale(), getRoundingMode());
    }

    //##################################################
    //# policy
    //##################################################

    public abstract int getMaxPrecision();

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

    public String format(int scale, boolean commas)
    {
        return _format(_value, commas, scale);
    }

    private String formatValueToScale(BigDecimal source, int scale)
    {
        return _format(source, true, scale);
    }

    private String _format(BigDecimal source, boolean commas, int scale)
    {
        source = source.setScale(scale, getRoundingMode());

        String pattern;
        pattern = createPattern(scale, commas);

        DecimalFormat formatter;
        formatter = getFormatterUsing(pattern);

        return formatter.format(source);
    }

    private String createPattern(int scale, boolean commas)
    {
        String prefix = commas
            ? ",##0"
            : "0";

        String suffix = scale > 0
            ? "." + getFractionPatternForScale(scale)
            : getFractionPatternForScale(scale);

        return prefix + suffix;
    }

    /**
     * The purpose of this method is to format a large number into a more
     * compact format.  First we check to see if the absolute value of the
     * original number is greater than 1000; if not, we format it and return
     * it unchanged.  Next we multiply our original 1000 by 1000 and
     * do the comparison again.  If we find our value is less than our factor
     * we normalize it, format it then return the value followed with the 
     * appropriate suffix.  This is repeated for each suffix in our list.  
     * If we run out of suffixes to try we return a message indicating a
     * very large number beyond the applications ability to render it.
     */
    public String getShortDisplayString()
    {
        BigDecimal factor = THOUSAND;
        KmList<String> suffixes;
        suffixes = createSuffixList();

        BigDecimal temp = getRoundedValue();

        // If value is negative, negate comparison value.
        // The temp value is used to determine the factor.
        // The formatRounded class deals with negative numbers.
        if ( isNegative() )
            temp = temp.negate();

        // If the temp value is less than 1000 format it and return the value.
        if ( isTempValueLessThanFactor(temp, factor) )
            return formatValueToScale(getRoundedValue(), getScale());

        // The temp value is used to determine the factor required to format our value.
        for ( String suffix : suffixes )
        {
            factor = factor.multiply(THOUSAND);

            if ( isTempValueLessThanFactor(temp, factor) )
                return formatValueToScale(getNormalizedValue(factor), 1) + " " + suffix;
        }

        return "Overflow";
    }

    private KmList<String> createSuffixList()
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
    //# convenience
    //##################################################

    private boolean isTempValueLessThanFactor(BigDecimal tempValue, BigDecimal factor)
    {
        return tempValue.compareTo(factor) == -1;
    }

    private BigDecimal getNormalizedValue(BigDecimal factor)
    {
        // Factor is used to shift the decimal so we can format the value.
        return getRoundedValue().divide(factor).multiply(THOUSAND);
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

    private DecimalFormat getFormatterUsing(String pattern)
    {
        return new DecimalFormat(pattern);
    }

}
