package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Phone;

public enum KmContactPhoneType
{
    ASSISTANT(Phone.TYPE_ASSISTANT),
    CALLBACK(Phone.TYPE_CALLBACK),
    CAR(Phone.TYPE_CAR),
    COMPANY_MAIN(Phone.TYPE_COMPANY_MAIN),
    FAX_HOME(Phone.TYPE_FAX_HOME),
    FAX_OTHER(Phone.TYPE_OTHER_FAX),
    FAX_WORK(Phone.TYPE_FAX_WORK),
    HOME(Phone.TYPE_HOME),
    ISDN(Phone.TYPE_ISDN),
    MAIN(Phone.TYPE_MAIN),
    MMS(Phone.TYPE_MMS),
    MOBILE(Phone.TYPE_MOBILE),
    OTHER(Phone.TYPE_OTHER),
    PAGER(Phone.TYPE_PAGER),
    WORK(Phone.TYPE_WORK),
    RADIO(Phone.TYPE_RADIO),
    TELEX(Phone.TYPE_TELEX),
    TTY_TDD(Phone.TYPE_TTY_TDD),
    WORK_MOBILE(Phone.TYPE_WORK_MOBILE),
    WORK_PAGER(Phone.TYPE_WORK_PAGER),
    CUSTOM(Phone.TYPE_CUSTOM);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactPhoneType(int e)
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
