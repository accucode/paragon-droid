/*
  Copyright (c) 2005-2012 Wyatt Love

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

package com.kodemore.contact;

import com.kodemore.time.KmDate;

public class KmContactEvent
{
    //##################################################
    //# variables
    //##################################################

    private String             _rawContactId;
    private KmDate             _startDate;
    private String             _startDateString;
    private KmContactEventType _type;

    /**
     * This holds the label of a custom event type
     */
    private String             _label;

    //##################################################
    //# constructors
    //##################################################

    public KmContactEvent()
    {
        // none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getRawContactId()
    {
        return _rawContactId;
    }

    public void setRawContactId(String e)
    {
        _rawContactId = e;
    }

    public KmDate getStartDate()
    {
        return _startDate;
    }

    public void setStartDate(KmDate e)
    {
        _startDate = e;
    }

    public boolean hasStartDate()
    {
        return getStartDate() != null;
    }

    public String getStartDateString()
    {
        return _startDateString;
    }

    public void setStartDateString(String e)
    {
        _startDateString = e;
    }

    public boolean hasStartDateString()
    {
        return getStartDateString() != null;
    }

    public KmContactEventType getType()
    {
        return _type;
    }

    public void setType(KmContactEventType e)
    {
        _type = e;
    }

    public String getLabel()
    {
        return _label;
    }

    public void setLabel(String e)
    {
        _label = e;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setTypeAnniversary()
    {
        setType(KmContactEventType.ANNIVERSARY);
        setLabel(null);
    }

    public void setTypeBirthday()
    {
        setType(KmContactEventType.BIRTHDAY);
        setLabel(null);
    }

    public void setTypeOther()
    {
        setType(KmContactEventType.OTHER);
        setLabel(null);
    }

    public void setTypeCustom()
    {
        setType(KmContactEventType.CUSTOM);
    }

    public void setTypeCustom(String label)
    {
        setTypeCustom();
        setLabel(label);
    }

    public String getTypeName()
    {
        return getType().name();
    }

    public int getTypeCode()
    {
        return getType().getCode();
    }

    public void setTypeFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactEventType e : KmContactEventType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }

    /**
     * review_aaron attempt to parse date
     * 
     * Convenience method that will attempt to parse the string that contains the date.
     * The original string will be stored in the _startDateString field. 
     */
    public void setStartDateFromString(String e)
    {
        KmDate d;
        d = KmContactDateParser.parseDate(e);
        setStartDate(d);

        if ( d == null )
            setStartDateString(e);
    }

    /**
     * If a date was successfully parsed, this will return the date's formatted string.  If
     * a date was not successfully parsed, this will return the raw date string pulled from 
     * the android contact data table.  
     */
    public String formatStartDate()
    {
        if ( hasStartDate() )
            return getStartDate().format_mm_dd_yyyy();

        return getStartDateString();
    }
}
