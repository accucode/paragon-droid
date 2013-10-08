package com.kodemore.smtp;

import java.io.IOException;

/**
 *  KmSmtpSimpleMessage should be used for plain text emails
 *  or for plain text emails with file attachments.
 *  
 *  See KmSmtpAlternativeMessage if you wish to use 
 *  multipart/alternative formatting
 */
public class KmSmtpSimpleMessage
    extends KmSmtpMessage
{
    //##################################################
    //# variables
    //##################################################

    private String _body;

    //##################################################
    //# constructor
    //##################################################

    public KmSmtpSimpleMessage()
    {
        setContentPlainText();
    }

    //##################################################
    //# accessing
    //##################################################

    public void setBody(String e)
    {
        _body = e;
    }

    public String getBody()
    {
        return _body;
    }

    //##################################################
    //# message type
    //##################################################

    /** 
     * Set email as plain text. Note if you have added any
     * attachments they will be cleared.
     */
    public void setContentPlainText()
    {
        setBaseContent(BaseContent.PLAIN);
    }

    public boolean isPlainText()
    {
        return getBaseContent() == BaseContent.PLAIN;
    }

    /** 
     * Set email as multipart/mixed. Sends an email message as 
     * a simple text part but also allows attachments.
     */
    public void setContentMultipart()
    {
        setBaseContent(BaseContent.MIME);
    }

    public boolean isMultipart()
    {
        return getBaseContent() == BaseContent.MIME;
    }

    /**
     * Set the content type automatically based on the current
     * configuration of the message.
     */
    public void setContentAuto()
    {
        if ( hasAttachments() )
            setContentMultipart();
        else
            setContentPlainText();
    }

    //##################################################
    //# compose
    //##################################################

    @Override
    protected void composeMessage() throws IOException
    {
        switch ( getBaseContent() )
        {
            case MIME:
                composeMime();
                break;

            case PLAIN:
                composePlain();
                break;

            case FILE:
                break;

            default:
                break;
        }
    }

    private void composePlain() throws IOException
    {
        writeBaseHeaders();
        writePlainTextBody(getBody());
    }

    private void composeMime() throws IOException
    {
        writeBaseHeaders();
        writeMimeContentHeader();
        writeBoundary();
        writePlainTextBody(getBody());
        writeAttachments();
        writeEndBoundary();
    }

}
