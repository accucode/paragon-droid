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

package com.kodemore.time;

/**
 * I provide a default implementation of KmDateFormatterIF that
 * supports a variety of date format based on the codes defined
 * in KmDateUtility.format(KmDate).
 */
public class KmDateFormatter
    implements KmDateFormatterIF
{
    //##################################################
    //# static
    //##################################################

    public static String formatDate(KmDate date, String format)
    {
        return new KmDateFormatter(format).format(date);
    }

    //##################################################
    //# variables
    //##################################################

    private String _format;

    //##################################################
    //# constructor
    //##################################################

    public KmDateFormatter()
    {
        this("{mm}/{dd}/{yyyy}");
    }

    public KmDateFormatter(String s)
    {
        _format = s;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getFormat()
    {
        return _format;
    }

    public void setFormat(String e)
    {
        _format = e;
    }

    //##################################################
    //# format
    //##################################################

    @Override
    public String format(KmDate d)
    {
        return KmDateUtility.format(d, _format);
    }
}
