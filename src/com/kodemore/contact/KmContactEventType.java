package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Event;

public enum KmContactEventType
{
    ANNIVERSARY(Event.TYPE_ANNIVERSARY),
    BIRTHDAY(Event.TYPE_BIRTHDAY),
    OTHER(Event.TYPE_OTHER),
    CUSTOM(Event.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactEventType(int e)
    {
        _code = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getCode()
    {
        return _code;
    }
}