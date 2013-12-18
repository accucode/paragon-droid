/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.callLog;

import com.kodemore.contact.KmContactPhoneType;
import com.kodemore.time.KmTimestamp;

public class KmCallLog
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The cached name associated with the phone number, if it exists. 
     * This value is not guaranteed to be current, if the contact information 
     * associated with this number has changed.
     */
    private String             _cachedName;

    /**
     * The cached number label, for a custom number type, associated with the 
     * phone number, if it exists. This value is not guaranteed to be current, 
     * if the contact information associated with this number has changed.
     */
    private String             _cachedNumberLabel;

    /**
     * The cached number type (Home, Work, etc) associated with the phone number, 
     * if it exists. This value is not guaranteed to be current, if the contact 
     * information associated with this number has changed.
     */
    private KmContactPhoneType _cachedNumberType;

    private KmTimestamp        _timestamp;

    /**
     * The Duration of the call in seconds.
     */
    private Integer            _duration;

    private boolean            _new;

    private String             _number;

    private KmCallLogType      _callType;

    //##################################################
    //# constructors
    //##################################################

    public KmCallLog()
    {
        // todo_aaron:  
    }

    //##################################################
    //# accessing
    //##################################################

    public String getCachedName()
    {
        return _cachedName;
    }

    public void setCachedName(String e)
    {
        _cachedName = e;
    }

    public String getCachedNumberLabel()
    {
        return _cachedNumberLabel;
    }

    public void setCachedNumberLabel(String e)
    {
        _cachedNumberLabel = e;
    }

    public KmContactPhoneType getCachedNumberType()
    {
        return _cachedNumberType;
    }

    public void setCachedNumberType(KmContactPhoneType e)
    {
        _cachedNumberType = e;
    }

    public KmTimestamp getTimestamp()
    {
        return _timestamp;
    }

    public void setTimestamp(KmTimestamp e)
    {
        _timestamp = e;
    }

    public Integer getDuration()
    {
        return _duration;
    }

    public void setDuration(Integer e)
    {
        _duration = e;
    }

    public boolean getNew()
    {
        return _new;
    }

    public void setNew(boolean e)
    {
        _new = e;
    }

    public String getNumber()
    {
        return _number;
    }

    public void setNumber(String e)
    {
        _number = e;
    }

    public KmCallLogType getCallType()
    {
        return _callType;
    }

    public void setCallType(KmCallLogType e)
    {
        _callType = e;
    }

    public boolean hasCallType()
    {
        return getCallType() != null;
    }

    public String getCallTypeName()
    {
        return hasCallType()
            ? getCallType().name()
            : null;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setTimestampFromLong(Long e)
    {
        KmTimestamp ts;
        ts = KmTimestamp.createFromMsSince1970(e);
        setTimestamp(ts);
    }

    public void setNewFromInt(Integer e)
    {
        boolean b = e != null && e == 1;
        setNew(b);
    }

    public void setCallTypeFromInt(Integer e)
    {
        if ( e == null )
        {
            setCachedNumberType(null);
            return;
        }

        for ( KmCallLogType Type : KmCallLogType.values() )
            if ( e == Type.getCode() )
                setCallType(Type);
    }

    public void setCachedNumberTypeFromInt(Integer e)
    {
        if ( e == null )
        {
            setCachedNumberType(null);
            return;
        }

        for ( KmContactPhoneType type : KmContactPhoneType.values() )
            if ( e == type.getCode() )
                setCachedNumberType(type);
    }
}
