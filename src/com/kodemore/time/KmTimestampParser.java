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
 * Assumes a space separates the date and time portions, and
 * that the date and time portions are parseable using the
 * default parse formats implemented by KmDateParser and KmTimeParser.
 */
public class KmTimestampParser
    implements KmTimestampParserIF
{
    //##################################################
    //# static
    //##################################################

    public static KmTimestamp parseTimestamp(String s)
    {
        return new KmTimestampParser().parse(s);
    }

    //##################################################
    //# parse
    //##################################################

    @Override
    public KmTimestamp parse(String s)
    {
        if ( s == null )
            return null;

        s = s.trim();
        int i = s.indexOf(' ');
        if ( i < 0 )
            return null;

        KmDate d = KmDateParser.parseDate(s.substring(0, i));
        KmTime t = KmTimeParser.parseTime(s.substring(i + 1));

        if ( d == null )
            return null;

        if ( t == null )
            return null;

        return KmTimestamp.create(d, t);
    }

}
