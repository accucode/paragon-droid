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

package com.kodemore.types;

import java.io.Serializable;

import com.kodemore.time.KmDate;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmCopyIF;
import com.kodemore.utility.KmReadOnlyException;
import com.kodemore.utility.KmReadOnlyIF;
import com.kodemore.utility.Kmu;

/**
 * I represent the days of the week a flight is scheduled to operate.
 * I provide utility methods for determining which day of the week
 * are contained in my frequency.
 */
public class KmDayFrequency
    implements KmConstantsIF, KmReadOnlyIF, KmCopyIF,
    Comparable<KmDayFrequency>, Serializable
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmDayFrequency createFrequency(String s)
    {
        return new KmDayFrequency(s);
    }

    //##################################################
    //# variables
    //##################################################

    private String  _days;
    private boolean _readOnly;

    //##################################################
    //# constructor
    //##################################################

    public KmDayFrequency()
    {
        setDays("");
    }

    public KmDayFrequency(String e)
    {
        setDays(e);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getDays()
    {
        return _days;
    }

    public void setDays(String s)
    {
        s = normalize(s);
        _days = s;
    }

    public boolean hasDays(String s)
    {
        s = normalize(s);
        return _days.equals(s);
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public void addMonday()
    {
        addDay(DOW_MONDAY);
    }

    public void removeMonday()
    {
        removeDay(DOW_MONDAY);
    }

    public void addTuesday()
    {
        addDay(DOW_TUESDAY);
    }

    public void removeTuesday()
    {
        removeDay(DOW_TUESDAY);
    }

    public void addWednesday()
    {
        addDay(DOW_WEDNESDAY);
    }

    public void removeWednesday()
    {
        removeDay(DOW_WEDNESDAY);
    }

    public void addThursday()
    {
        addDay(DOW_THURSDAY);
    }

    public void removeThursday()
    {
        removeDay(DOW_THURSDAY);
    }

    public void addFriday()
    {
        addDay(DOW_FRIDAY);
    }

    public void removeFriday()
    {
        removeDay(DOW_FRIDAY);
    }

    public void addSaturday()
    {
        addDay(DOW_SATURDAY);
    }

    public void removeSaturday()
    {
        removeDay(DOW_SATURDAY);
    }

    public void addSunday()
    {
        addDay(DOW_SUNDAY);
    }

    public void removeSunday()
    {
        removeDay(DOW_SUNDAY);
    }

    public void addDay(String e)
    {
        if ( hasDay(e) )
            return;
        e += getDays();
        setDays(e);
    }

    public void removeDay(String e)
    {
        if ( !hasDay(e) )
            return;
        Kmu.replaceAll(_days, e, "");
    }

    public String normalize(String s)
    {
        if ( s == null )
            s = "";

        StringBuilder sb = new StringBuilder(".......");

        if ( s.indexOf(DOW_CHAR_MONDAY) >= 0 )
            sb.setCharAt(0, DOW_CHAR_MONDAY);

        if ( s.indexOf(DOW_CHAR_TUESDAY) >= 0 )
            sb.setCharAt(1, DOW_CHAR_TUESDAY);

        if ( s.indexOf(DOW_CHAR_WEDNESDAY) >= 0 )
            sb.setCharAt(2, DOW_CHAR_WEDNESDAY);

        if ( s.indexOf(DOW_CHAR_THURSDAY) >= 0 )
            sb.setCharAt(3, DOW_CHAR_THURSDAY);

        if ( s.indexOf(DOW_CHAR_FRIDAY) >= 0 )
            sb.setCharAt(4, DOW_CHAR_FRIDAY);

        if ( s.indexOf(DOW_CHAR_SATURDAY) >= 0 )
            sb.setCharAt(5, DOW_CHAR_SATURDAY);

        if ( s.indexOf(DOW_CHAR_SUNDAY) >= 0 )
            sb.setCharAt(6, DOW_CHAR_SUNDAY);

        return sb.toString();
    }

    //##################################################
    //# testing
    //##################################################

    public boolean hasMonday()
    {
        return hasDay(DOW_MONDAY);
    }

    public boolean hasTuesday()
    {
        return hasDay(DOW_TUESDAY);
    }

    public boolean hasWednesday()
    {
        return hasDay(DOW_WEDNESDAY);
    }

    public boolean hasThursday()
    {
        return hasDay(DOW_THURSDAY);
    }

    public boolean hasFriday()
    {
        return hasDay(DOW_FRIDAY);
    }

    public boolean hasSaturday()
    {
        return hasDay(DOW_SATURDAY);
    }

    public boolean hasSunday()
    {
        return hasDay(DOW_SUNDAY);
    }

    public boolean hasDay(String e)
    {
        int i = getDays().indexOf(e);
        return i >= 0;
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( !(e instanceof KmDayFrequency) )
            return false;

        return ((KmDayFrequency)e).getDays().equals(_days);
    }

    @Override
    public int hashCode()
    {
        return _days.hashCode();
    }

    @Override
    public int compareTo(KmDayFrequency e)
    {
        return _days.compareTo(e.getDays());
    }

    //##################################################
    //# copy
    //##################################################

    @Override
    public KmDayFrequency getCopy()
    {
        KmDayFrequency e;
        e = new KmDayFrequency();
        e.setDays(_days);
        return e;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return getDisplayString();
    }

    /**
     * This string must be compatible with setDays.
     */
    public String getDisplayString()
    {
        return getDays();
    }

    //##################################################
    //# read only
    //##################################################

    @Override
    public boolean isReadOnly()
    {
        return _readOnly;
    }

    @Override
    public void setReadOnly(boolean b)
    {
        _readOnly = b;
    }

    public void checkReadOnly()
    {
        if ( _readOnly )
            throw new KmReadOnlyException(this);
    }

    //##################################################
    //# date
    //##################################################

    public boolean matchesDate(KmDate d)
    {
        return d.matchesFrequency(this);
    }
}
