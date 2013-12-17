package com.kodemore.contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kodemore.collection.KmList;
import com.kodemore.time.KmDate;

/**
 * This is a utility designed to parse data from Android's contact data table
 * into usable KmDates.  The Android contact data table stored dates as strings
 * and the following formats:
 *  
 *      - YYYY-MM-DD (ex. 1994-01-06)
 *      - MMM DD, YYYY (ex. Jan 6, 1994) 
 */
public class KmContactDateParser
{
    //##################################################
    //# static
    //##################################################

    public static KmDate parseDate(String e)
    {
        return new KmContactDateParser().parse(e);
    }

    //##################################################
    //# variables
    //##################################################

    private KmList<String> _formats;

    //##################################################
    //# constructor
    //##################################################

    public KmContactDateParser()
    {
        _formats = new KmList<String>();
        addFormat("yyyy-MM-dd");
        addFormat("MMM dd, yyyy");
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<String> getFormats()
    {
        return _formats;
    }

    public void setFormats(KmList<String> e)
    {
        _formats = e;
    }

    public void addFormat(String e)
    {
        getFormats().add(e);
    }

    //##################################################
    //# parse
    //##################################################

    public KmDate parse(String date)
    {
        for ( String format : getFormats() )
        {
            KmDate d = parse(date, format);
            if ( d != null )
                return d;
        }

        return null;
    }

    public KmDate parse(String date, String format)
    {
        try
        {
            SimpleDateFormat f = new SimpleDateFormat(format);
            Date d = f.parse(date);
            return KmDate.createJavaDate(d);
        }
        catch ( ParseException ex )
        {
            return null;
        }
    }
}
