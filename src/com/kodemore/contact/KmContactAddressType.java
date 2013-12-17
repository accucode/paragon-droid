package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;

public enum KmContactAddressType
{
    HOME(StructuredPostal.TYPE_HOME),
    OTHER(StructuredPostal.TYPE_OTHER),
    WORK(StructuredPostal.TYPE_WORK),
    CUSTOM(StructuredPostal.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactAddressType(int e)
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