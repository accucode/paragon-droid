package com.kodemore.view;

import android.content.pm.ActivityInfo;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmCodedEnumIF;

public enum KmScreenOrientation
    implements KmCodedEnumIF
{
    //##################################################
    //# values
    //##################################################

    PORTRAIT("P"),
    LANDSCAPE("L"),
    UNSPECIFIED("U");

    //##################################################
    //# static
    //##################################################

    public static KmList<KmScreenOrientation> getValues()
    {
        KmList<KmScreenOrientation> v;
        v = new KmList<KmScreenOrientation>();
        v.addAll(values());
        return v;
    }

    public static KmScreenOrientation findCode(String code)
    {
        for ( KmScreenOrientation e : values() )
            if ( e.hasCode(code) )
                return e;

        return null;
    }

    //##################################################
    //# variables
    //##################################################

    private String _code;

    //##################################################
    //# constructor
    //##################################################

    private KmScreenOrientation(String code)
    {
        _code = code;
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public String getCode()
    {
        return _code;
    }

    public boolean hasCode(String e)
    {
        return getCode().equals(e);
    }

    @Override
    public String getName()
    {
        return name();
    }

    public int getActivityInfoValue()
    {
        KmScreenOrientation e = this;
        switch ( e )
        {
            case PORTRAIT:
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

            case LANDSCAPE:
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

            case UNSPECIFIED:
                return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }

        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

}
