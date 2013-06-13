package com.kodemore.smtp.types;

public enum KmSmtpContentType
{
    TEXT("text/plain", KmSmtpEncoding.ISO8859),
    HTML("text/html", KmSmtpEncoding.ISO8859),
    FILE("application/octet-stream", KmSmtpEncoding.BASE64),
    PDF("application/pdf", KmSmtpEncoding.BASE64);

    private final String         _content;
    private final KmSmtpEncoding _kmSmtpEncoding;

    private KmSmtpContentType(String content, KmSmtpEncoding kmSmtpEncoding)
    {
        _content = content;
        _kmSmtpEncoding = kmSmtpEncoding;
    }

    public String getContentType()
    {
        return _content;
    }

    public KmSmtpEncoding getEncoding()
    {
        return _kmSmtpEncoding;
    }

    public String getEncodingType()
    {
        return _kmSmtpEncoding.getType();
    }
}
