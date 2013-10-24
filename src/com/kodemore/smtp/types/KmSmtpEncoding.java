package com.kodemore.smtp.types;

public enum KmSmtpEncoding
{
    //##################################################
    //# values
    //##################################################

    ASCII_US("US-ASCII"),
    BASE64("BASE64"),
    UTF8("UTF-8"),
    ISO8859("ISO-8859-1");

    //##################################################
    //# variables
    //##################################################

    private String _header;

    //##################################################
    //# constructor
    //##################################################

    private KmSmtpEncoding(String encoding)
    {
        _header = encoding;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getType()
    {
        return _header;
    }
}
