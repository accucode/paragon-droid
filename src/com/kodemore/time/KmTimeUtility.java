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

public class KmTimeUtility
{
    //##################################################
    //# constants
    //##################################################

    public static final String COLON = ":";
    public static final String DOT   = ".";
    public static final String AM    = "a";
    public static final String PM    = "p";

    //##################################################
    //# format
    //##################################################

    /**
     * I provide a very simple, and non-optimized, facility for formatting
     * dates in a variety of formats.  Valid format codes are:
     * {H}    : hour (1-12)
     * {HH}   : hour (01-12)
     * {H24}  : hour (0-23)
     * {HH24} : hour (00-23)
     * {M}    : minute (0-59)
     * {MM}   : minute (00-59)
     * {S}    : second (0-59)
     * {SS}   : second (00-59)
     * {MS}   : millisecond (000-999)
     * {am/pm}: am/pm (am, pm)
     */
    public static String format(KmTime e, String format)
    {
        if ( e == null )
            return "";

        String s = format;
        s = Kmu.replaceAll(s, "{H}", e.getHour12() + "");
        s = Kmu.replaceAll(s, "{HH}", pad2(e.getHour12()));
        s = Kmu.replaceAll(s, "{H24}", e.getHour() + "");
        s = Kmu.replaceAll(s, "{HH24}", pad2(e.getHour()));

        s = Kmu.replaceAll(s, "{M}", e.getMinute() + "");
        s = Kmu.replaceAll(s, "{MM}", pad2(e.getMinute()));

        s = Kmu.replaceAll(s, "{S}", e.getSecond() + "");
        s = Kmu.replaceAll(s, "{SS}", pad2(e.getSecond()));

        s = Kmu.replaceAll(s, "{MS}", pad3(e.getMillisecond()));

        s = Kmu.replaceAll(s, "{/p}", e.isAm()
            ? ""
            : "p");
        s = Kmu.replaceAll(s, "{/pm}", e.isAm()
            ? ""
            : "pm");
        s = Kmu.replaceAll(s, "{a/p}", e.isAm()
            ? "a"
            : "p");
        s = Kmu.replaceAll(s, "{A/P}", e.isAm()
            ? "A"
            : "P");
        s = Kmu.replaceAll(s, "{am/pm}", e.isAm()
            ? "am"
            : "pm");
        s = Kmu.replaceAll(s, "{AM/PM}", e.isAm()
            ? "AM"
            : "PM");
        return s;
    }

    //##################################################
    //# format
    //##################################################

    public static String format_h_mm_am(KmTime t)
    {
        if ( t == null )
            return "";

        int hh = t.getHour();
        int mm = t.getMinute();
        String suffix = AM;
        if ( t.isPm() )
        {
            hh -= 12;
            suffix = PM;
        }
        if ( hh == 0 )
            hh = 12;
        return hh + COLON + pad2(mm) + suffix;
    }

    public static String format_h_mm_ss_am(KmTime t)
    {
        if ( t == null )
            return "";

        int hh = t.getHour();
        int mm = t.getMinute();
        int ss = t.getSecond();
        String suffix = AM;
        if ( t.isPm() )
        {
            hh -= 12;
            suffix = PM;
        }
        if ( hh == 0 )
            hh = 12;
        return hh + COLON + pad2(mm) + COLON + pad2(ss) + suffix;
    }

    public static String format_h_mm_ss_msss_am(KmTime t)
    {
        if ( t == null )
            return "";

        int hh = t.getHour();
        int mm = t.getMinute();
        int ss = t.getSecond();
        int ms = t.getMillisecond();
        String suffix = AM;
        if ( t.isPm() )
        {
            hh -= 12;
            suffix = PM;
        }
        if ( hh == 0 )
            hh = 12;
        return hh + COLON + pad2(mm) + COLON + pad2(ss) + DOT + pad3(ms) + suffix;
    }

    public static String format_h_mm_ss_msss(KmTime t)
    {
        if ( t == null )
            return "";

        int hh = t.getHour();
        int mm = t.getMinute();
        int ss = t.getSecond();
        int ms = t.getMillisecond();
        return hh + COLON + pad2(mm) + COLON + pad2(ss) + DOT + pad3(ms);
    }

    public static String format_hh24_mm(KmTime t)
    {
        return format(t, "{HH24}:{MM}");
    }

    public static String format_hh24_mm_ss(KmTime t)
    {
        return format(t, "{HH24}:{MM}:{SS}");
    }

    public static String format_hh24mmss(KmTime t)
    {
        return format(t, "{HH24}{MM}{SS}");
    }

    //##################################################
    //# parsing
    //##################################################

    public static KmTime parse_24HHMM(String s)
    {
        if ( Kmu.isEmpty(s) )
            throw new RuntimeException("Argument cannot be empty or null.");

        if ( s.charAt(2) != ':' )
            throw new NumberFormatException(Kmu.format("Invalid format for HH24:MM (%s)", s));

        int hh = Kmu.parse_int(s.substring(0, 2));
        int mm = Kmu.parse_int(s.substring(3));
        return KmTime.create(hh, mm, 0, 0);

    }

    //##################################################
    //# private
    //##################################################

    private static String pad2(int i)
    {
        return Kmu.padLeft(i, 2, '0');
    }

    private static String pad3(int i)
    {
        return Kmu.padLeft(i, 3, '0');
    }

}
