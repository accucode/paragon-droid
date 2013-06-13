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

package com.kodemore.stf;

import com.kodemore.utility.Kmu;

/**
 * I am used to identify parse errors for specific conditions
 * such as unterminated comments, or element names with 
 * invalid characters.  All polite errors thrown by the 
 * parser should be a parseError.  Any generic RuntimeExceptions
 * are due to some unexpected condition and are likely a bug in
 * the parse.
 */
public class KmStfError
    extends RuntimeException
{
    //##################################################
    //# variables
    //##################################################

    private String  _lineText;
    private Integer _lineNumber;

    //##################################################
    //# constructor
    //##################################################

    public KmStfError(String msg, Object... args)
    {
        super(Kmu.format(msg, args));
    }

    //##################################################
    //# accessing
    //##################################################

    public String getLineText()
    {
        return _lineText;
    }

    public void setLineText(String e)
    {
        _lineText = e;
    }

    public Integer getLineNumber()
    {
        return _lineNumber;
    }

    public void setLineNumber(Integer e)
    {
        _lineNumber = e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        if ( _lineNumber == null )
            return getMessage();

        return Kmu.format("Parse Error, line %s: %s", _lineNumber, getMessage());
    }
}
