package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Im;

public enum KmContactImProtocol
{
    AIM(Im.PROTOCOL_AIM),
    MSN(Im.PROTOCOL_MSN),
    GOOGLE_TALK(Im.PROTOCOL_GOOGLE_TALK),
    ICQ(Im.PROTOCOL_ICQ),
    JABBER(Im.PROTOCOL_JABBER),
    NETMEETING(Im.PROTOCOL_NETMEETING),
    QQ(Im.PROTOCOL_QQ),
    SKYPE(Im.PROTOCOL_SKYPE),
    YAHOO(Im.PROTOCOL_YAHOO),
    CUSTOM(Im.PROTOCOL_CUSTOM);

    private int _code;

    KmContactImProtocol(int code)
    {
        _code = code;
    }

    public int getCode()
    {
        return _code;
    }
}