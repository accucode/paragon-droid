package com.kodemore.smtp;

import java.io.IOException;

import com.kodemore.utility.Kmu;

public class KmSmtpAlternativeMessage
    extends KmSmtpMessage
{
    //##################################################
    //# constants
    //##################################################

    private static final String MULTI_ALT        = "multipart/alternative";
    private static final String SUB_BOUNDARY_TAG = "sub";

    //##################################################
    //# variables
    //##################################################

    private String              _text;
    private String              _html;

    //##################################################
    //# constructor
    //##################################################

    /**
     *  KmSmtpAlternativedMessage should be used for emails
     *  that implement multipart/alternative MIME format
     */
    public KmSmtpAlternativeMessage()
    {
        setBaseContent(BaseContent.MIME);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getText()
    {
        return _text;
    }

    public void setText(String e)
    {
        _text = e;
    }

    public String getHtml()
    {
        return _html;
    }

    public void setHtml(String e)
    {
        _html = e;
    }

    //##################################################
    //# compose
    //##################################################

    @Override
    protected void composeMessage() throws IOException
    {
        writeBaseHeaders();
        writeMimeContentHeader();
        writeBoundary();

        writeMimeContentSubHeader();

        writeSubBoundary();
        writePlainTextBody(_text);

        writeSubBoundary();
        writeHtmlTextBody(_html);

        writeEndSubBoundary();

        writeBoundary();
        writeAttachments();

        writeEndBoundary();
    }

    //##################################################
    //# utility
    //##################################################

    private String getSubBoundary()
    {
        String s = Kmu.getSimpleNameFor(this) + SUB_BOUNDARY_TAG;
        return "" + s.hashCode();
    }

    private void writeSubBoundary() throws IOException
    {
        writeln("--" + getSubBoundary());
    }

    private void writeEndSubBoundary() throws IOException
    {
        writeln("--" + getSubBoundary() + "--");
    }

    private void writeMimeContentSubHeader() throws IOException
    {
        writeMimeContentHeader(MULTI_ALT, getSubBoundary());
        writeln();
    }
}
