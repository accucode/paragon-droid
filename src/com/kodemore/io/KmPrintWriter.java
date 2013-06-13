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
import java.io.PrintWriter;
import java.io.Writer;

public class KmPrintWriter
    extends PrintWriter
{
    //##################################################
    //# constructor
    //##################################################

    public KmPrintWriter(Writer e)
    {
        super(e);
    }

    public KmPrintWriter(Writer e, boolean autoFlush)
    {
        super(e, autoFlush);
    }

    public KmPrintWriter(OutputStream e)
    {
        super(e);
    }

    public KmPrintWriter(OutputStream e, boolean autoFlush)
    {
        super(e, autoFlush);
    }

    //##################################################
    //# convenience
    //##################################################

    public void printfln(String msg, Object... args)
    {
        printf(msg, args);
        println();
    }
}
