package com.kodemore.smtp.types;

public enum KmSmtpEncoding
{
    ASCII_US("US-ASCII"),
    BASE64("BASE64"),
    UTF8("UTF-8"),
    ISO8859("ISO-8859-1");

    private String _header;

    private KmSmtpEncoding(String encoding)
    {
        _header = encoding;
    }

    public String getType()
    {
        return _header;
    }
}
