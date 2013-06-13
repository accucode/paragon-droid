package com.kodemore.smtp;

import com.kodemore.smtp.types.KmSmtpRecipientType;

public class KmSmtpRecipient
{
    //##################################################
    //# variables
    //##################################################

    private String              _address;
    private KmSmtpRecipientType _type;

    //##################################################
    //# constructors
    //##################################################

    public KmSmtpRecipient()
    {
        _address = null;
        _type = null;
    }

    public KmSmtpRecipient(KmSmtpRecipientType type, String address)
    {
        _address = address;
        _type = type;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getAddress()
    {
        return _address;
    }

    public String getHeader()
    {
        if ( _type == null )
            return null;

        return _type.getHeader();
    }

    public void setRecipient(KmSmtpRecipientType type, String address)
    {
        _address = address;
        _type = type;
    }

    //##################################################
    //# type
    //##################################################

    public KmSmtpRecipientType getType()
    {
        return _type;
    }

    public boolean isTypeBcc()
    {
        return getType() == KmSmtpRecipientType.BCC;
    }

    public boolean isTypeCc()
    {
        return getType() == KmSmtpRecipientType.CC;
    }

    public boolean isTypeTo()
    {
        return getType() == KmSmtpRecipientType.TO;
    }

    public boolean isTypeUndefined()
    {
        return getType() == KmSmtpRecipientType.NULL;
    }
}
