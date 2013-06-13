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

package com.kodemore.csv;

import com.kodemore.time.KmDate;
import com.kodemore.time.KmTime;
import com.kodemore.time.KmTimestamp;
import com.kodemore.time.KmTimestampUtility;
import com.kodemore.types.KmKilogram;
import com.kodemore.types.KmMoney;
import com.kodemore.types.KmQuantity;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * I am a simple utility to help format csv files.
 */
public class KmCsvBuilder
    implements CharSequence
{
    //##################################################
    //# constants
    //##################################################

    private static final String CR    = "\r";
    private static final String LF    = "\n";
    private static final String CRLF  = CR + LF;
    private static final String SPACE = " ";

    //##################################################
    //# variables
    //##################################################

    private String              _endOfLine;
    private String              _fieldSeparator;
    private char                _quote;

    private StringBuilder       _buffer;
    private StringBuilder       _record;

    private boolean             _quoteAll;
    private boolean             _emptyRecordFlag;
    private boolean             _convertLineEndsToSpaces;

    /**
     * When true (the default), an apostophe will be added to the
     * beginning of the file, if it would otherwise start with
     * the (2) characters: "ID".   This is because MS Excel
     * misinterprets? .csv files that whose content starts with ID.
     */
    private boolean             _excelSylkCompatibility;

    //##################################################
    //# constructor
    //##################################################

    public KmCsvBuilder()
    {
        _endOfLine = CRLF;
        _fieldSeparator = ",";
        _quote = '"';
        _quoteAll = false;
        _emptyRecordFlag = true;
        _convertLineEndsToSpaces = false;
        _excelSylkCompatibility = true;

        _buffer = new StringBuilder();
        _record = new StringBuilder();
    }

    //##################################################
    //# accessing
    //##################################################

    public String getEndOfLine()
    {
        return _endOfLine;
    }

    public void setEndOfLine(String endOfLine)
    {
        _endOfLine = endOfLine;
    }

    public String getFieldSeparator()
    {
        return _fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator)
    {
        _fieldSeparator = fieldSeparator;
    }

    public char getQuote()
    {
        return _quote;
    }

    public void setQuote(char quote)
    {
        _quote = quote;
    }

    public boolean getQuoteAll()
    {
        return _quoteAll;
    }

    public void setQuoteAll(boolean e)
    {
        _quoteAll = e;
    }

    public boolean getConvertLineEndsToSpaces()
    {
        return _convertLineEndsToSpaces;
    }

    public void setConvertLineEndsToSpaces(boolean e)
    {
        _convertLineEndsToSpaces = e;
    }

    public boolean getExcelSylkCompatibility()
    {
        return _excelSylkCompatibility;
    }

    public void setExcelSylkCompatibility(boolean e)
    {
        _excelSylkCompatibility = e;
    }

    public String getBuffer()
    {
        String s = _buffer.toString();

        if ( _excelSylkCompatibility && s.startsWith("ID") )
            return KmConstantsIF.CHAR_TICK + s;

        return s;
    }

    public void clearBuffer()
    {
        _buffer.setLength(0);
        _record.setLength(0);
    }

    //##################################################
    //# fields
    //##################################################

    public void printField(Object e)
    {
        if ( e == null )
        {
            printBlankField();
            return;
        }

        if ( e instanceof String )
        {
            _addField((String)e);
            return;
        }

        if ( e instanceof Integer )
        {
            _addField(format((Integer)e));
            return;
        }

        if ( e instanceof Boolean )
        {
            _addField(format((Boolean)e));
            return;
        }

        if ( e instanceof Double )
        {
            _addField(format((Double)e));
            return;
        }

        if ( e instanceof KmTimestamp )
        {
            _addField(format((KmTimestamp)e));
            return;
        }

        if ( e instanceof KmDate )
        {
            _addField(format((KmDate)e));
            return;
        }

        if ( e instanceof KmTime )
        {
            _addField(format((KmTime)e));
            return;
        }

        if ( e instanceof KmMoney )
        {
            _addField(format((KmMoney)e));
            return;
        }

        if ( e instanceof KmKilogram )
        {
            _addField(format((KmKilogram)e));
            return;
        }

        if ( e instanceof KmQuantity )
        {
            _addField(format((KmQuantity)e));
            return;
        }

        _addField(e.toString());
    }

    public void printBlankFields(int n)
    {
        for ( int i = 0; i < n; i++ )
            printBlankField();
    }

    public void printBlankField()
    {
        _addField("");
    }

    //##################################################
    //# record
    //##################################################

    public void endRecord()
    {
        _buffer.append(_record);
        _buffer.append(_endOfLine);
        _record.setLength(0);
        _emptyRecordFlag = true;
    }

    //##################################################
    //# private
    //##################################################

    private void _addField(String s)
    {
        if ( !_emptyRecordFlag )
            _record.append(_fieldSeparator);

        _record.append(format(s));
        _emptyRecordFlag = false;
    }

    //##################################################
    //# format
    //##################################################

    private String format(String s)
    {
        if ( s == null )
            return "";

        if ( getConvertLineEndsToSpaces() )
        {
            s = Kmu.replaceAll(s, CRLF, SPACE);
            s = Kmu.replaceAll(s, CR, SPACE);
            s = Kmu.replaceAll(s, LF, SPACE);
        }

        if ( needsEscape(s) )
            return escape(s);

        return s;
    }

    private String format(Integer e)
    {
        return e + "";
    }

    private String format(Double e)
    {
        return e + "";
    }

    private String format(Boolean e)
    {
        return e
            ? "yes"
            : "no";
    }

    private String format(KmTimestamp ts)
    {
        return KmTimestampUtility.format_mm_dd_yyyy_HH24_MM_SS(ts);
    }

    private String format(KmDate e)
    {
        return e.format_m_d_yyyy();
    }

    private String format(KmTime e)
    {
        return e.format_hh24_mm_ss();
    }

    private String format(KmMoney e)
    {
        return format(e.toDoubleValue());
    }

    private String format(KmKilogram e)
    {
        return format(e.toDoubleValue());
    }

    private String format(KmQuantity e)
    {
        return format(e.toDoubleValue());
    }

    //##################################################
    //# escape
    //##################################################

    private boolean needsEscape(String s)
    {
        if ( _quoteAll )
            return true;

        if ( s.indexOf(_quote) >= 0 )
            return true;

        if ( Kmu.containsAny(s, _endOfLine) )
            return true;

        if ( Kmu.containsAny(s, _fieldSeparator) )
            return true;

        if ( s.indexOf(_quote) >= 0 )
            return true;

        return false;
    }

    private String escape(String s)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(_quote);
        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( c == _quote )
                sb.append(_quote);
            sb.append(c);
        }
        sb.append(_quote);
        return sb.toString();
    }

    //##################################################
    //# display
    //##################################################

    /**
     * I return the composed csv buffer.
     * I am safe for clients to use directly.
     * 
     */
    @Override
    public String toString()
    {
        return getBuffer();
    }

    //##################################################
    //# char sequence
    //##################################################

    @Override
    public char charAt(int index)
    {
        return toString().charAt(index);
    }

    @Override
    public int length()
    {
        return toString().length();
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return toString().subSequence(start, end);
    }

}
