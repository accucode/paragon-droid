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

import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;

public class KmLogPrintStream
    extends OutputStream
{
    //##################################################
    //# constants
    //##################################################

    private static final char CR = KmConstantsIF.CHAR_CR;
    private static final char LF = KmConstantsIF.CHAR_LF;

    //##################################################
    //# variables
    //##################################################

    private KmStringBuilder   _buffer;
    private int               _lastLineEnd;

    //##################################################
    //# constructor
    //##################################################

    public KmLogPrintStream()
    {
        _buffer = new KmStringBuilder();
    }

    //##################################################
    //# overwrite
    //##################################################

    @Override
    public void write(int b) throws IOException
    {
        char c = (char)b;

        if ( _lastLineEnd == CR && c == LF )
        {
            _lastLineEnd = 0;
            return;
        }

        _buffer.append(c);

        if ( isLineEnd(c) )
        {
            _lastLineEnd = c;
            flush();
        }
    }

    @Override
    public void flush() throws IOException
    {
        log(Kmu.toPrintable(_buffer));
        _buffer.clear();
        super.flush();
    }

    @Override
    public void close() throws IOException
    {
        flush();
        super.close();
    }

    //##################################################
    //# support
    //##################################################

    private boolean isLineEnd(char c)
    {
        return c == CR || c == LF;
    }

    protected void log(String s)
    {
        KmLog.print(s);
    }
}
