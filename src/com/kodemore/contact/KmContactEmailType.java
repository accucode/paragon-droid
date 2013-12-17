package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Email;

public enum KmContactEmailType
{
    HOME(Email.TYPE_HOME),
    MOBILE(Email.TYPE_MOBILE),
    OTHER(Email.TYPE_OTHER),
    WORK(Email.TYPE_WORK),
    CUSTOM(Email.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactEmailType(int e)
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
