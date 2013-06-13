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

import com.kodemore.utility.Kmu;

/**
 * Parse a timestamp in XSD UTC format.  Assumes a 'T' is used to separate
 * the date and time.  Assumes a UTC timezone denoted with a 'Z' suffix.
 */
public class KmTimestampXsdUtcParser
    implements KmTimestampParserIF
{
    //##################################################
    //# static
    //##################################################

    public static KmTimestamp parseTimestamp(String s)
    {
        return new KmTimestampXsdUtcParser().parse(s);
    }

    //##################################################
    //# parse
    //##################################################

    @Override
    public KmTimestamp parse(String s)
    {
        if ( s == null )
            return null;

        int i = s.indexOf('T');
        if ( i < 0 )
            return null;

        String sd = s.substring(0, i);
        String st = s.substring(i + 1);

        if ( !st.endsWith("Z") )
            return null;
        st = Kmu.removeSuffix(st, "Z");

        KmDate d = KmDateParser.parseXsd(sd);
        KmTime t = getTimeParser().parse(st);

        if ( d == null )
            return null;
        if ( t == null )
            return null;
        return KmTimestamp.create(d, t);
    }

    public KmTimeParser getTimeParser()
    {
        return new KmTimeParser();
    }

}
