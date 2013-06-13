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

/**
 * Convert the input string into a series of word wrapped lines.
 * in a variety of situations. This class should generally not
 * have any dependencies on other packages.
 */
public class KmWordWrapUtility
{
    //##################################################
    //# constants
    //##################################################

    private static final char CHAR_CR = 13;
    private static final char CHAR_LF = 10;

    //##################################################
    //# constructor
    //##################################################

    public KmWordWrapUtility(String value, int columnCount)
    {
        _value = value;
        _columnCount = columnCount;
    }

    //##################################################
    //# variables
    //##################################################

    private String         _value;
    private int            _columnCount;
    private int            _index;
    private KmList<String> _rows;
    private StringBuilder  _line;
    private StringBuilder  _token;

    //##################################################
    //# constructor
    //##################################################

    public KmWordWrapUtility()
    {
        _rows = new KmList<String>();
        _line = new StringBuilder();
        _token = new StringBuilder();
    }

    //##################################################
    //# public
    //##################################################

    public KmList<String> wordWrap()
    {
        while ( _index < _value.length() )
        {
            char c = _value.charAt(_index++);
            if ( c == CHAR_CR )
                skipLineFeed();
            if ( c == ' ' || c == CHAR_CR || c == CHAR_LF )
                appendToken(c);
            else
                _token.append(c);
        }
        appendRemainder();
        return _rows;
    }

    //##################################################
    //# private
    //##################################################

    public void skipLineFeed()
    {
        int next = _index + 1;
        if ( next < _value.length() )
        {
            char c = _value.charAt(next);
            if ( c == CHAR_LF )
                _index = next;
        }
    }

    public void appendToken(char c)
    {
        if ( hasExceededColumnLength() )
            addLine();
        else
            appendSpace();
        _line.append(_token);
        if ( c == CHAR_CR || c == CHAR_LF )
            addLine();
        _token = new StringBuilder();
    }

    public void addLine()
    {
        String line = _line.toString();
        _rows.add(line);
        _line = new StringBuilder();
    }

    public boolean hasExceededColumnLength()
    {
        int length = _token.length() + _line.length();
        return length + 1 > _columnCount;
    }

    public void appendSpace()
    {
        if ( _line.length() != 0 )
            _line.append(" ");
    }

    public void appendRemainder()
    {
        if ( _line.length() + _token.length() == 0 )
            return;
        appendSpace();
        _line.append(_token);
        addLine();
    }
}
