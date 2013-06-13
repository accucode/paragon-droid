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

package com.kodemore.string;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * Split a string into pieces.  The string is split around
 * the specified delimiters.
 */
public class KmStringTokenizer
{
    //##################################################
    //# variables
    //##################################################

    private KmList<String> _delimiters;
    private boolean        _trimValues;
    private boolean        _ignoreEmptyValues;

    //##################################################
    //# constructor
    //##################################################

    public KmStringTokenizer()
    {
        _delimiters = new KmList<String>();
        _trimValues = false;
        _ignoreEmptyValues = false;
    }

    //##################################################
    //# accessing (delimiters)
    //##################################################

    public KmList<String> getDelimiters()
    {
        return _delimiters;
    }

    public void setDelimiters(KmList<String> v)
    {
        _delimiters.replaceAll(v);
    }

    public void addDelimiter(char c)
    {
        addDelimiter("" + c);

    }

    public void addDelimiter(String e)
    {
        _delimiters.add(e);
    }

    public void addCommaDelimiter()
    {
        addDelimiter(KmConstantsIF.CHAR_COMMA);
    }

    public void addSemicolonDelimiter()
    {
        addDelimiter(KmConstantsIF.CHAR_SEMICOLON);
    }

    public void addSpaceDelimiter()
    {
        addDelimiter(KmConstantsIF.CHAR_SPACE);
    }

    public void addWhitespaceDelimiters()
    {
        addDelimiter(KmConstantsIF.CHAR_SPACE);
        addDelimiter(KmConstantsIF.CHAR_TAB);
        addDelimiter(KmConstantsIF.CHAR_CR);
        addDelimiter(KmConstantsIF.CHAR_LF);
    }

    //##################################################
    //# accessing (misc)
    //##################################################

    public boolean getTrimValues()
    {
        return _trimValues;
    }

    public void setTrimValues(boolean e)
    {
        _trimValues = e;
    }

    public void setTrimValues()
    {
        setTrimValues(true);
    }

    public boolean getIgnoreEmptyValues()
    {
        return _ignoreEmptyValues;
    }

    public void setIgnoreEmptyValues(boolean e)
    {
        _ignoreEmptyValues = e;
    }

    public void setIgnoreEmptyValues()
    {
        setIgnoreEmptyValues(true);
    }

    //##################################################
    //# split
    //##################################################

    public KmList<String> split(String source)
    {
        if ( Kmu.isEmpty(source) )
            return new KmList<String>();

        KmList<String> v = _split(source);

        if ( _trimValues )
            Kmu.trimValues(v);

        if ( getIgnoreEmptyValues() )
            Kmu.removeEmptyValues(v);

        return v;
    }

    private KmList<String> _split(String source)
    {
        KmList<String> v = new KmList<String>();
        StringBuilder out = new StringBuilder();
        int i = 0;

        while ( !atEnd(source, i) )
        {
            while ( !atEnd(source, i) && !isDelimiter(source, i) )
            {
                out.append(source.charAt(i));
                i++;
            }

            if ( out.length() > 0 )
            {
                v.add(out.toString());
                out.setLength(0);
            }

            while ( !atEnd(source, i) && isDelimiter(source, i) )
                i++;
        }

        return v;
    }

    private boolean atEnd(String source, int i)
    {
        return i >= source.length();
    }

    private boolean isDelimiter(String source, int i)
    {
        for ( String s : _delimiters )
            if ( isDelimiter(source, i, s) )
                return true;
        return false;
    }

    private boolean isDelimiter(String source, int i, String s)
    {
        if ( i + s.length() > source.length() )
            return false;

        return source.indexOf(s, i) == i;
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String... args)
    {
        KmStringTokenizer e;
        e = new KmStringTokenizer();
        e.addCommaDelimiter();
        e.addSemicolonDelimiter();

        KmList<String> v = e.split("one,two;three;;four;");
        int n = v.size();

        System.out.println("Size: " + n);
        for ( int i = 0; i < n; i++ )
            System.out.printf("%2s - [%s]\n", i, v.get(i));
    }
}
