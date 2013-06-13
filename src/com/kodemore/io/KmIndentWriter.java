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

package com.kodemore.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

public class KmIndentWriter
    extends KmWrapperWriter
    implements KmConstantsIF
{
    //##################################################
    //# variables
    //##################################################

    private int     _indentLevel;
    private String  _indentString;
    private boolean _isStartOfLine;
    private boolean _indentationEnabled;

    //##################################################
    //# constructor
    //##################################################

    public KmIndentWriter(Writer out)
    {
        super(out);
        init();
    }

    public KmIndentWriter(OutputStream out)
    {
        super(out);
        init();
    }

    private void init()
    {
        _indentLevel = 0;
        _isStartOfLine = true;
        _indentationEnabled = true;

        setIndentSpaces(4);
    }

    //##################################################
    //# accessing
    //##################################################

    public boolean isIndentationEnabled()
    {
        return _indentationEnabled;
    }

    public void setIndentationEnabled(boolean e)
    {
        _indentationEnabled = e;
    }

    //##################################################
    //# indentation
    //##################################################

    public void indent()
    {
        _indentLevel++;
    }

    public void undent()
    {
        _indentLevel--;
    }

    public int getIndentLevel()
    {
        return _indentLevel;
    }

    public void setIndentLevel(int i)
    {
        _indentLevel = i;
    }

    public String getIndentString()
    {
        return _indentString;
    }

    public void setIndentString(String s)
    {
        _indentString = s;
    }

    public void setIndentSpaces(int i)
    {
        String s = Kmu.repeat(SPACE, i);
        setIndentString(s);
    }

    //##################################################
    //# write
    //##################################################

    @Override
    public void write(char buf[], int off, int len) throws IOException
    {
        synchronized (lock)
        {
            while ( len > 0 )
            {
                _write(buf[off]);
                off++;
                len--;
            }
        }
    }

    public void _write(char c) throws IOException
    {
        _checkIndent(c);
        getDelegate().write(c);
    }

    public void _checkIndent(char c) throws IOException
    {
        if ( !_indentationEnabled )
            return;

        if ( c == CHAR_LF || c == CHAR_CR || c == CHAR_FF )
        {
            _isStartOfLine = true;
            return;
        }

        if ( _isStartOfLine )
        {
            int n = _indentLevel;
            for ( int i = 0; i < n; i++ )
                getDelegate().write(_indentString);

            _isStartOfLine = false;
        }
    }

}
