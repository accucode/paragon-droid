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

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * An immutable wrapper for strings.
 * Used to provide convenience methods for strings
 * since we are not allowed to extend String. 
 */
public class KmString
    implements CharSequence
{
    //##################################################
    //# constants
    //##################################################

    public static final KmString NULL  = new KmString(null);
    public static final KmString EMPTY = new KmString("");

    //##################################################
    //# instance creation
    //##################################################

    public static KmString create()
    {
        return NULL;
    }

    public static KmString create(CharSequence e)
    {
        if ( e == null )
            return NULL;

        if ( e.length() == 0 )
            return EMPTY;

        return new KmString(e.toString());
    }

    public static KmString create(Object e)
    {
        return e == null
            ? NULL
            : create(e.toString());
    }

    //##################################################
    //# variables
    //##################################################

    private String _inner;

    //##################################################
    //# constructor
    //##################################################

    private KmString(String e)
    {
        _inner = e;
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isNull()
    {
        return _inner == null;
    }

    public boolean isNotNull()
    {
        return !isNull();
    }

    public boolean isEmpty()
    {
        return isNull() || length() == 0;
    }

    public boolean hasValue()
    {
        return !isEmpty();
    }

    //##################################################
    //# accessing
    //##################################################

    //##################################################
    //# abstract accessing
    //##################################################

    public KmString safe()
    {
        return isNull()
            ? create("")
            : this;
    }

    public KmString capitalize()
    {
        if ( isEmpty() )
            return this;

        String s = _inner.substring(0, 1).toUpperCase() + _inner.substring(1);
        return create(s);
    }

    public KmString toUpper()
    {
        if ( isEmpty() )
            return this;

        return create(_inner.toUpperCase());
    }

    public KmString toLower()
    {
        if ( isEmpty() )
            return this;

        return create(_inner.toLowerCase());
    }

    public KmString reverse()
    {
        if ( isEmpty() )
            return this;

        StringBuilder out;
        out = new StringBuilder();
        out.append(_inner);
        out.reverse();
        return create(out);
    }

    public KmString getFirstLine()
    {
        try
        {
            if ( isEmpty() )
                return this;

            BufferedReader r;
            r = new BufferedReader(new StringReader(_inner));
            return create(r.readLine());
        }
        catch ( Exception ex )
        {
            return create();
        }
    }

    //##################################################
    //# format
    //##################################################

    public KmString toDisplay()
    {
        return create(Kmu.toDisplay(_inner));
    }

    public KmString format(Object... args)
    {
        if ( isEmpty() || args == null || args.length == 0 )
            return this;

        return create(String.format(_inner, args));
    }

    public byte[] toByteArray()
    {
        int n = length();
        byte[] arr = new byte[n];

        for ( int i = 0; i < n; i++ )
            arr[i] = (byte)charAt(i);

        return arr;
    }

    //##################################################
    //# strip
    //##################################################

    public KmString toPrintable()
    {
        if ( isEmpty() )
            return this;

        StringBuilder out = new StringBuilder();

        for ( char c : _inner.toCharArray() )
            if ( Kmu.isPrintable(c) )
                out.append(c);

        return out.length() == length()
            ? this
            : create(out.toString());
    }

    public KmString toLinePrintable()
    {
        if ( isEmpty() )
            return this;

        StringBuilder out = new StringBuilder();

        for ( char c : _inner.toCharArray() )
            if ( Kmu.isLinePrintable(c) )
                out.append(c);

        return out.length() == length()
            ? this
            : create(out.toString());
    }

    public KmString strip(char c)
    {
        return strip("" + c);
    }

    public KmString strip(CharSequence stripChars)
    {
        if ( isEmpty() || stripChars == null || stripChars.length() == 0 )
            return this;

        String strip = stripChars.toString();
        StringBuilder out = new StringBuilder();

        for ( char c : _inner.toCharArray() )
            if ( strip.indexOf(c) < 0 )
                out.append(c);

        return out.length() == length()
            ? this
            : create(out.toString());
    }

    public KmString stripCommas()
    {
        return strip(Kmu.CHAR_COMMA);
    }

    public KmString stripWhitespace()
    {
        return strip(Kmu.WHITESPACE_STRING);
    }

    public KmString stripSpaces()
    {
        return strip(Kmu.CHAR_SPACE);
    }

    public KmString stripNonPrintable()
    {
        return create(Kmu.stripNonPrintable(_inner));
    }

    public KmString trim()
    {
        return isNull()
            ? this
            : create(_inner.trim());
    }

    //##################################################
    //# parse
    //##################################################

    public Integer parseInteger()
    {
        if ( isEmpty() )
            return null;

        KmString s = toLinePrintable().stripWhitespace().stripCommas();
        if ( s.isEmpty() )
            return null;

        try
        {
            return Integer.parseInt(s.toString());
        }
        catch ( RuntimeException ex )
        {
            return null;
        }
    }

    public Double parseDouble()
    {
        if ( isEmpty() )
            return null;

        KmString s = toLinePrintable().stripWhitespace().stripCommas();
        if ( s.isEmpty() )
            return null;

        try
        {
            return Double.parseDouble(s.toString());
        }
        catch ( RuntimeException ex )
        {
            return null;
        }
    }

    //##################################################
    //# CharSequence
    //##################################################

    @Override
    public char charAt(int index)
    {
        return _inner.charAt(index);
    }

    @Override
    public int length()
    {
        return _inner.length();
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return _inner.subSequence(start, end);
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return _inner + "";
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return e instanceof KmString
            ? Kmu.isEqual(_inner, ((KmString)e)._inner)
            : false;
    }

    @Override
    public int hashCode()
    {
        return _inner == null
            ? 0
            : _inner.hashCode();
    }

}
