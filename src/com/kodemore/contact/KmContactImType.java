package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Im;

public enum KmContactImType
{
    HOME(Im.TYPE_HOME),
    OTHER(Im.TYPE_OTHER),
    WORK(Im.TYPE_WORK),
    CUSTOM(Im.TYPE_CUSTOM);

    private int _code;

    KmContactImType(int code)
    {
        _code = code;
    }

    public int getCode()
    {
        return _code;
    }
}