package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Nickname;

public enum KmContactNicknameType
{
    DEFAULT(Nickname.TYPE_DEFAULT),
    INITIALS(Nickname.TYPE_INITIALS),
    MAIDEN_NAME(Nickname.TYPE_MAINDEN_NAME),
    OTHER(Nickname.TYPE_OTHER_NAME),
    SHORT_NAME(Nickname.TYPE_SHORT_NAME),
    CUSTOM(Nickname.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactNicknameType(int e)
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