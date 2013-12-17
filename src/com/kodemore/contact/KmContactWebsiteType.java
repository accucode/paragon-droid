package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Website;

public enum KmContactWebsiteType
{
    BLOG(Website.TYPE_BLOG),
    FTP(Website.TYPE_FTP),
    HOME(Website.TYPE_HOME),
    HOMEPAGE(Website.TYPE_HOMEPAGE),
    OTHER(Website.TYPE_OTHER),
    PROFILE(Website.TYPE_PROFILE),
    WORK(Website.TYPE_WORK),
    CUSTOM(Website.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactWebsiteType(int e)
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
