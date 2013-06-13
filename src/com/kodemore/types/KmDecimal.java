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

import com.kodemore.utility.KmCopyIF;
import com.kodemore.utility.Kmu;

/**
 * I wrap the BigDouble class to provide an mutable decimal class. I
 * provide convience methods for manipulating my value as well as methods
 * to track my rounding mode and scale. You might use this class when
 * managing small sets of doubles that need to be displayed.
 *
 * IMPORTANT: This class should NOT be used when running calculations
 * against large sets of data. Instead use primative doubles for any
 * calculations, then create a KmDecimal with the result to apply any
 * rounding and scaling before extracting the result. This class is
 * much slower than primatives when doing calculations over sets of
 * data due to the overhead of creating new objects for each call.
 * Calculating against a set of 1,000 takes around 0.11 sec, and
 * 10,000 takes around 0.41 sec where primitives are in around
 * 0.015 for the same calculation.
 *
 */
public class KmDecimal
    implements KmCopyIF, Comparable<KmDecimal>, Serializable
{
    //##################################################
    //# constants
    //##################################################

    private static final int ROUNDING = BigDecimal.ROUND_HALF_UP;

    //##################################################
    //# variables
    //##################################################

    private BigDecimal       _value;

    //##################################################
    //# constructor
    //##################################################

    public KmDecimal()
    {
        _value = BigDecimal.ZERO;
    }

    public KmDecimal(String s)
    {
        _value = new BigDecimal(s);
    }

    public KmDecimal(Double value, int scale)
    {
        if ( value == null )
            _value = BigDecimal.ZERO;
        else
        {
            _value = new BigDecimal(value);
            _value = _value.setScale(scale, ROUNDING);
        }
    }

    public KmDecimal(Integer value, int scale)
    {
        if ( value == null )
            _value = BigDecimal.ZERO;
        else
        {
            _value = new BigDecimal(value);
            setScale(scale);
        }
    }

    //##################################################
    //# accessing
    //##################################################

    public double doubleValue()
    {
        return _value.doubleValue();
    }

    public void set(double e)
    {
        _value = new BigDecimal(e);
    }

    public void set(int e)
    {
        _value = new BigDecimal(e);
    }

    public BigDecimal getDecimalValue()
    {
        return _value;
    }

    public void setScale(int scale)
    {
        _value = _value.setScale(scale, ROUNDING);
    }

    //##################################################
    //# math (add)
    //##################################################

    /**
     * Sets the value to the result of (this + d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void add(double d)
    {
        _add(new BigDecimal(d));
    }

    /**
     * Sets the value to the result of (this + d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void add(KmDecimal d)
    {
        _add(d.getDecimalValue());
    }

    /**
     * Sets the value to the result of (this + d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void _add(BigDecimal d)
    {
        _value = _value.add(d);
    }

    /**
     * Sets the value to the result of (this - d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    //##################################################
    //# math (subtract)
    //##################################################
    public void subtract(double d)
    {
        _subtract(new BigDecimal(d));
    }

    /**
     * Sets the value to the result of (this - d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void subtract(KmDecimal d)
    {
        _subtract(d.getDecimalValue());
    }

    /**
     * Sets the value to the result of (this - d), whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void _subtract(BigDecimal d)
    {
        _value = _value.subtract(d);
    }

    //##################################################
    //# math (mulitply)
    //##################################################

    /**
     * Sets the value to the result of (this * d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void multiply(double d)
    {
        _multiply(new BigDecimal(d));
    }

    /**
     * Sets the value to the result of (this * d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void multiply(KmDecimal d)
    {
        _multiply(d.getDecimalValue());
    }

    /**
     * Sets the value to the result of (this * d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void _multiply(BigDecimal d)
    {
        _value = _value.multiply(d);
    }

    //##################################################
    //# math (divide)
    //##################################################

    /**
     * Sets the value to the result of (this / d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void divide(double d)
    {
        _divide(new BigDecimal(d));
    }

    /**
     * Sets the value to the result of (this / d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void divide(KmDecimal d)
    {
        _divide(d.getDecimalValue());
    }

    /**
     * Sets the value to the result of (this / d), whose scale
     * is (this.scale + d.scale). Note: this will update the
     * internal scale value.
     */
    public void _divide(BigDecimal d)
    {
        _value = _value.divide(d, ROUNDING);
    }

    //##################################################
    //# math (other)
    //##################################################

    /**
     * Sets the value to the absolute value of value, whose scale
     * is this.scale. Note: this does not change the internal
     * scale value.
     */
    public void abs()
    {
        _value = _value.abs();
    }

    /**
     * Sets the value to (-this), whose scale is this.scale.
     * Note: this does not change the internal scale value.
     */
    public void negate()
    {
        _value = _value.negate();
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmDecimal) )
            return false;

        return ((KmDecimal)e)._value.equals(_value);
    }

    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }

    @Override
    public int compareTo(KmDecimal e)
    {
        return _value.compareTo(e.getDecimalValue());
    }

    //##################################################
    //# copy
    //##################################################

    @Override
    public KmDecimal getCopy()
    {
        return Kmu.getSerializedCopy(this);
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return _value.toPlainString();
    }

    public String format(int scale)
    {
        return format(scale, true);
    }

    public String format(int scale, boolean commas)
    {
        return format(scale, commas, RoundingMode.HALF_UP);
    }

    /**
     * Returns the display string for this decimal using the internal
     * scale, formated with commas.
     */
    public String format(int scale, boolean commas, RoundingMode rounding)
    {
        BigDecimal d = _value.setScale(scale, rounding);
        String s = d.toPlainString();
        int i = s.indexOf(".");
        if ( i <= 0 )
            return s;
        if ( !commas )
            return s;

        boolean isNegative = s.startsWith("-");
        String prefix = isNegative
            ? s.substring(1, i)
            : s.substring(0, i);
        StringBuilder sb = new StringBuilder(prefix);
        sb.reverse();
        int x = 3;
        while ( x < sb.length() )
        {
            sb.insert(x, ',');
            x += 4;
        }
        sb.reverse();

        s = sb.toString() + s.substring(i);
        if ( isNegative )
            s = "-" + s;
        return s;
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String[] args)
    {
        KmDecimal d;

        d = new KmDecimal(1.23, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(12.34, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(123.45, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(1234.56, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(12345.67, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(123456.78, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(1234567.89, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(12345678.90, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-1.23, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-12.34, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-123.45, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-1234.56, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-12345.67, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-123456.78, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-1234567.89, 2);
        System.out.println("d = " + d.format(2, true));

        d = new KmDecimal(-12345678.90, 2);
        System.out.println("d = " + d.format(2, true));
    }
}
