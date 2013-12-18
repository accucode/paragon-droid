package com.kodemore.callLog;

import android.provider.CallLog.Calls;

public enum KmCallLogType
{
    //##################################################
    //# values 
    //##################################################

    INCOMING(Calls.INCOMING_TYPE),
    OUTGOING(Calls.OUTGOING_TYPE),
    MISSED(Calls.MISSED_TYPE);

    //##################################################
    //# variables
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    private KmCallLogType(int code)
    {
        _code = code;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getCode()
    {
        return _code;
    }
}
