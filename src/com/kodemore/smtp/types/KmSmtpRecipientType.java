package com.kodemore.smtp.types;

public enum KmSmtpRecipientType
{
    TO("To"),
    CC("Cc"),
    BCC("Bcc"),
    NULL("");

    private final String _header;

    private KmSmtpRecipientType(String header)
    {
        _header = header;
    }

    public String getHeader()
    {
        return _header;
    }
}
