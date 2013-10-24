package com.kodemore.smtp.types;

public enum KmSmtpContentType
{
    //##################################################
    //# values
    //##################################################

    TEXT("text/plain", KmSmtpEncoding.ISO8859),
    HTML("text/html", KmSmtpEncoding.ISO8859),
    FILE("application/octet-stream", KmSmtpEncoding.BASE64),
    PDF("application/pdf", KmSmtpEncoding.BASE64);

    //##################################################
    //# variables
    //##################################################

    private final String         _content;
    private final KmSmtpEncoding _encoding;

    //##################################################
    //# constructor
    //##################################################

    private KmSmtpContentType(String content, KmSmtpEncoding encoding)
    {
        _content = content;
        _encoding = encoding;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getContentTypeName()
    {
        return _content;
    }

    public KmSmtpEncoding getEncoding()
    {
        return _encoding;
    }

    public String getEncodingTypeName()
    {
        return _encoding.getType();
    }
}
