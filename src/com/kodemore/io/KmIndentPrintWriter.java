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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class KmIndentPrintWriter
    extends KmPrintWriter
{
    //##################################################
    //# variables
    //##################################################

    private KmIndentWriter _indentWriter;

    //##################################################
    //# constructor
    //##################################################

    public KmIndentPrintWriter(Writer e)
    {
        this(e, false);
    }

    public KmIndentPrintWriter(Writer e, boolean autoFlush)
    {
        this(new KmIndentWriter(e), autoFlush);
    }

    public KmIndentPrintWriter(OutputStream e)
    {
        this(e, false);
    }

    public KmIndentPrintWriter(OutputStream e, boolean autoFlush)
    {
        this(new OutputStreamWriter(e), autoFlush);
    }

    private KmIndentPrintWriter(KmIndentWriter e, boolean autoFlush)
    {
        super(e, autoFlush);
        _indentWriter = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmIndentWriter getIndentWriter()
    {
        return _indentWriter;
    }

    public void indent(int n)
    {
        for ( int i = 0; i < n; i++ )
            indent();
    }

    public void indent()
    {
        getIndentWriter().indent();
    }

    public void undent()
    {
        getIndentWriter().undent();
    }

    public void setIndentString(String s)
    {
        getIndentWriter().setIndentString(s);
    }
}
