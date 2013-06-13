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

package com.kodemore.utility;

import java.util.List;
import java.util.Random;

public class KmRandom
{
    //##################################################
    //# constants
    //##################################################

    private static final String NUMERIC_STRING       = "0123456789";
    private static final char[] NUMERIC_ARRAY        = NUMERIC_STRING.toCharArray();

    private static final String LOWER_STRING         = "abcdefghijklmnopqrstuvwxyz";
    private static final char[] LOWER_ARRAY          = LOWER_STRING.toCharArray();

    private static final String UPPER_STRING         = LOWER_STRING.toUpperCase();
    private static final char[] UPPER_ARRAY          = UPPER_STRING.toCharArray();

    private static final String ALPHA_STRING         = LOWER_STRING + UPPER_STRING;
    private static final char[] ALPHA_ARRAY          = ALPHA_STRING.toCharArray();

    private static final String ALPHA_NUMERIC_STRING = ALPHA_STRING + NUMERIC_STRING;
    private static final char[] ALPHA_NUMERIC_ARRAY  = ALPHA_NUMERIC_STRING.toCharArray();

    private static final String SYMBOL_STRING        = "~`!@#$%^&*()-_=+[{]}|;:,./?'\"\\";
    private static final char[] SYMBOL_ARRAY         = SYMBOL_STRING.toCharArray();

    private static final String ALL_STRING           = ALPHA_NUMERIC_STRING + SYMBOL_STRING;
    private static final char[] ALL_ARRAY            = ALL_STRING.toCharArray();

    //##################################################
    //# variables
    //##################################################

    private static final Random _random              = new Random();

    //##################################################
    //# integer
    //##################################################

    public static int getInteger()
    {
        return _random.nextInt();
    }

    public static int getInteger(int max)
    {
        return _random.nextInt(max);
    }

    public static int getPositiveInteger()
    {
        return getInteger(Integer.MAX_VALUE - 1) + 1;
    }

    /**
     * Return a random value between min and max, inclusive.
     */
    public static int getIntegerIn(int min, int max)
    {
        return getInteger(max - min + 1) + min;
    }

    //##################################################
    //# percentage
    //##################################################

    public static int getPercentage()
    {
        int i = getInteger() % 100;
        i = Math.abs(i);
        return i;
    }

    //##################################################
    //# long
    //##################################################

    public static long getLong()
    {
        return _random.nextLong();
    }

    public static long getPositiveLong()
    {
        long i = getLong();

        if ( i > 0 )
            return i;

        if ( i == Long.MIN_VALUE )
            return Long.MAX_VALUE;

        return -i;
    }

    //##################################################
    //# boolean
    //##################################################

    public static boolean getBoolean()
    {
        return _random.nextBoolean();
    }

    //##################################################
    //# char
    //##################################################

    public static char getChar()
    {
        return getCharIn(ALL_ARRAY);
    }

    public static char getCharIn(char[] chars)
    {
        int n = chars.length;
        return chars[getInteger(n)];
    }

    public static char getDigit()
    {
        return getCharIn(NUMERIC_ARRAY);
    }

    public static char getPrintableCharacter()
    {
        return (char)getIntegerIn(0x20, 0x7E);
    }

    //##################################################
    //# double
    //##################################################

    public static double getDouble()
    {
        return _random.nextDouble();
    }

    public static double getDouble(double min, double max)
    {
        return _random.nextDouble() * (max - min) + min;
    }

    //##################################################
    //# list
    //##################################################

    public static Object getElement(List<?> v)
    {
        int i = getInteger(v.size());
        return v.get(i);
    }

    //##################################################
    //# string
    //##################################################

    public static String getSymbolString(int n)
    {
        return getRun(SYMBOL_ARRAY, n);
    }

    public static String getLowerString(int n)
    {
        return getRun(LOWER_ARRAY, n);
    }

    public static String getUpperString(int n)
    {
        return getRun(UPPER_ARRAY, n);
    }

    public static String getAlphaString(int n)
    {
        return getRun(ALPHA_ARRAY, n);
    }

    public static String getDigits(int n)
    {
        return getRun(NUMERIC_ARRAY, n);
    }

    public static String getAlphaNumericString(int n)
    {
        return getRun(ALPHA_NUMERIC_ARRAY, n);
    }

    private static String getRun(char[] arr, int n)
    {
        StringBuilder out = new StringBuilder();

        for ( int i = 0; i < n; i++ )
            out.append(getCharIn(arr));

        return out.toString();
    }

    public static String getPrintableString(int length)
    {
        StringBuilder sb = new StringBuilder(length);
        for ( int i = 0; i < length; i++ )
        {
            char c = KmRandom.getPrintableCharacter();
            sb.append(c);
        }
        return sb.toString();
    }

}
