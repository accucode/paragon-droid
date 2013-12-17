package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Email;

public enum KmContactOrganizationType
{
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

    KmContactOrganizationType(int e)
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
