package com.kodemore.smtp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;

import android.net.Uri;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmApplicationFilePath;
import com.kodemore.file.KmExternalFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.smtp.types.KmSmtpContentType;
import com.kodemore.smtp.types.KmSmtpRecipientType;
import com.kodemore.utility.Kmu;

public abstract class KmSmtpMessage
{
    //##################################################
    //# enum
    //##################################################

    protected enum BaseContent
    {
        PLAIN("text/plain"),
        MIME("multipart/mixed"),
        FILE("");

        private final String _type;

        private BaseContent(String type)
        {
            _type = type;
        }

        public String getContentType()
        {
            return _type;
        }
    }

    //##################################################
    //# constants
    //##################################################

    private static final String      BOUNDARY_TAG   = "base";
    protected static final String    END_SENDER     = "---end-sender---";
    protected static final String    END_RECIPIENTS = "---end-recipients---";

    //##################################################
    //# variables
    //##################################################

    private KmList<KmSmtpAttachment> _attachments;
    private KmList<KmSmtpRecipient>  _recipients;
    private BaseContent              _baseContent;
    private String                   _uid;
    private String                   _from;
    private String                   _subject;
    private Writer                   _writer;

    //##################################################
    //# constructors
    //##################################################

    protected KmSmtpMessage()
    {
        _uid = Kmu.newUid();
        _attachments = new KmList<KmSmtpAttachment>();
        _recipients = new KmList<KmSmtpRecipient>();
    }

    //##################################################
    //# abstract
    //##################################################

    protected abstract void composeMessage() throws IOException;

    //##################################################
    //# compose
    //##################################################

    /**
     * Composes the message and sends it to the specified writer
     */
    public void composeOn(Writer w) throws IOException
    {
        setWriter(w);
        composeMessage();
    }

    //##################################################
    //# writer
    //##################################################

    private void setWriter(Writer e)
    {
        _writer = e;
    }

    protected void write(String s) throws IOException
    {
        if ( s == null )
            return;

        _writer.write(s);
    }

    protected void write(int c) throws IOException
    {
        _writer.write(c);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getFrom()
    {
        return _from;
    }

    public void setFrom(String e)
    {
        _from = e;
    }

    public String getSubject()
    {
        return _subject;
    }

    public void setSubject(String e)
    {
        _subject = e;
    }

    //##################################################
    //# message/content type
    //##################################################    

    public String getContentType()
    {
        return _baseContent.getContentType();
    }

    protected void setBaseContent(BaseContent type)
    {
        _baseContent = type;
    }

    protected BaseContent getBaseContent()
    {
        return _baseContent;
    }

    //##################################################
    //# recipient
    //##################################################    

    public KmList<KmSmtpRecipient> getRecipients()
    {
        return _recipients;
    }

    public KmList<KmSmtpRecipient> getRecipientsOfTypeTo()
    {
        KmList<KmSmtpRecipient> recipients = new KmList<KmSmtpRecipient>();

        for ( KmSmtpRecipient v : getRecipients() )
            if ( v.isTypeTo() )
                recipients.add(v);

        return recipients;
    }

    public KmList<KmSmtpRecipient> getRecipientsOfTypeBcc()
    {
        KmList<KmSmtpRecipient> recipients = new KmList<KmSmtpRecipient>();

        for ( KmSmtpRecipient v : getRecipients() )
            if ( v.isTypeBcc() )
                recipients.add(v);

        return recipients;
    }

    public KmList<KmSmtpRecipient> getRecipientsOfTypeCc()
    {
        KmList<KmSmtpRecipient> recipients = new KmList<KmSmtpRecipient>();

        for ( KmSmtpRecipient v : getRecipients() )
            if ( v.isTypeCc() )
                recipients.add(v);

        return recipients;
    }

    public boolean hasRecipients()
    {
        return _recipients.isNotEmpty();
    }

    public boolean addRecipient(KmSmtpRecipient r)
    {
        return _recipients.addNonNull(r);
    }

    public boolean addRecipient(String addr)
    {
        KmSmtpRecipientType type = KmSmtpRecipientType.NULL;
        KmSmtpRecipient e = new KmSmtpRecipient(type, addr);
        return addRecipient(e);
    }

    public KmSmtpRecipient removeRecipient(int i)
    {
        return _recipients.remove(i);
    }

    public boolean removeRecipient(KmSmtpRecipient r)
    {
        return _recipients.remove(r);
    }

    public void clearRecipients()
    {
        _recipients.clear();
    }

    public boolean addTo(String email)
    {
        return addRecipient(new KmSmtpRecipient(KmSmtpRecipientType.TO, email));
    }

    public boolean addCc(String email)
    {
        return addRecipient(new KmSmtpRecipient(KmSmtpRecipientType.CC, email));
    }

    //##################################################
    //# attachment
    //##################################################

    public KmList<KmSmtpAttachment> getAttachments()
    {
        return _attachments;
    }

    public boolean addAttachment(KmSmtpAttachment e)
    {
        return _attachments.addNonNull(e);
    }

    public KmSmtpAttachment removeAttachment(int i)
    {
        return _attachments.remove(i);
    }

    public boolean removeAttachment(KmSmtpAttachment e)
    {
        return _attachments.remove(e);
    }

    public boolean hasAttachments()
    {
        return _attachments.isNotEmpty();
    }

    public void clearAttachments()
    {
        _attachments.clear();
        _attachments = new KmList<KmSmtpAttachment>();
    }

    public void cleanUpAttachments()
    {
        for ( KmSmtpAttachment e : getAttachments() )
            e.cleanUp();
    }

    //##################################################
    //# uid
    //##################################################

    public String getUid()
    {
        return _uid;
    }

    //##################################################
    //# save to disk
    //##################################################

    /**
     * Writes the message to the SD card so it can be used 
     * later. Returns null if you are unable to write the file.
     */
    public Uri saveToDisk(String path)
    {
        Writer w = null;

        try
        {
            KmFilePath f;
            String uid = Kmu.newUid();

            if ( path == null || path.length() < 1 )
                f = new KmApplicationFilePath(Kmu.formatSafeFileName(uid, ".txt"));
            else
                f = new KmApplicationFilePath(path + "/" + Kmu.formatSafeFileName(uid, ".txt"));

            if ( !f.isAvailable() )
                throw new IOException("SD Card is read-only or unavailable.");

            w = f.openFileWriter();

            setWriter(w);

            writeln(getFrom());
            writeln(END_SENDER);

            if ( getRecipients().size() >= 1 )
                for ( KmSmtpRecipient e : getRecipients() )
                    writeln(e.getAddress());

            writeln(END_RECIPIENTS);

            composeMessage();

            return f.toUri();
        }
        catch ( Exception ex )
        {
            return null;
        }
        finally
        {
            Kmu.closeSafely(w);
        }
    }

    /**
     * Writes the message to the SD card so it can be used 
     * later. Returns null if you are unable to write the file.
     */

    public Uri saveToDisk()
    {
        return saveToDisk(null);
    }

    //##################################################
    //# convenience
    //##################################################    

    /**
     *  This is required as the first thing in any email.
     *  It constructs all the headers of the message such
     *  as to, from, etc.
     */
    protected void writeBaseHeaders() throws IOException
    {
        writeMime();
        writeSubject();
        writeFrom();
        writeRecipients();
    }

    protected void writePlainTextBody(String body) throws IOException
    {
        KmSmtpContentType type = KmSmtpContentType.TEXT;

        writeln("Content-type: "
            + type.getContentTypeName()
            + "; charset=\""
            + type.getEncodingTypeName()
            + "\"");
        writeln();
        write(body);
        writeln();
    }

    protected void writeHtmlTextBody(String html) throws IOException
    {
        KmSmtpContentType type = KmSmtpContentType.HTML;

        writeln("Content-type: "
            + type.getContentTypeName()
            + "; charset=\""
            + type.getEncodingTypeName()
            + "\"");
        writeln("Content-Disposition: inline");
        writeln("Content-Transfer-Encoding: quoted-printable");
        writeln();
        write(html);
        writeln();
    }

    private void writeRecipients() throws IOException
    {
        for ( KmSmtpRecipient r : getRecipients() )
            if ( !r.isTypeBcc() )
                writeRecipientHeader(r);
    }

    protected void writeAttachments() throws IOException
    {
        KmList<KmSmtpAttachment> attachments = getAttachments();

        for ( KmSmtpAttachment e : attachments )
            writeAttachment(e);
    }

    //##################################################
    //# support
    //##################################################  

    private String getBoundary()
    {
        String s = Kmu.getSimpleNameFor(this) + BOUNDARY_TAG;
        return "" + s.hashCode();
    }

    protected void writeBoundary() throws IOException
    {
        writeln("--" + getBoundary());
    }

    protected void writeEndBoundary() throws IOException
    {
        writeln("--" + getBoundary() + "--");
    }

    private void writeMime() throws IOException
    {
        writeln("MIME-Version: 1.0");
    }

    protected void writeMimeContentHeader() throws IOException
    {
        writeln("Content-type: " + getContentType() + "; boundary=\"" + getBoundary() + "\"");
        writeln();
    }

    protected void writeMimeContentHeader(String type, String boundary) throws IOException
    {
        writeln("Content-type: " + type + "; boundary=\"" + boundary + "\"");
        writeln();
    }

    protected void writeln() throws IOException
    {
        write(Kmu.CR_LF);
    }

    protected void writeln(String s) throws IOException
    {
        if ( s == null )
            return;

        write(s);
        writeln();
    }

    protected void writeLines(int n) throws IOException
    {
        for ( int i = 0; i < n; i++ )
            writeln();
    }

    private void writeSubject() throws IOException
    {
        write("Subject: ");
        writeln(getSubject());
    }

    private void writeFrom() throws IOException
    {
        write("From: ");
        writeln(getFrom());
    }

    private void writeRecipientHeader(KmSmtpRecipient r) throws IOException
    {
        writeln(r.getHeader() + ": " + r.getAddress());
    }

    private void writeAttachment(KmSmtpAttachment e) throws IOException
    {
        if ( e == null )
            return;

        if ( !e.isValidAttachment() )
            return;

        Uri uri = e.createEncodedAttachment();

        if ( uri == null )
            return;

        KmFilePath file = new KmExternalFilePath(uri.getPath());

        if ( !file.exists() || !file.isAvailable() )
            return;

        writeBoundary();
        writeln("Content-Type: " + e.getFileTypeName() + "; name=\"" + e.getFileName() + "\"");
        writeln("Content-Disposition: attachment; filename=\"" + e.getFileName() + "\"");
        writeln("Content-Transfer-Encoding: " + e.getEncodingName());
        writeln();

        FileInputStream in = null;
        try
        {
            in = new FileInputStream(file.getRealFile());

            while ( true )
            {
                int c = in.read();

                if ( c == -1 )
                    break;

                write(c);
            }
        }
        finally
        {
            Kmu.closeSafely(in);
        }

        writeln();

        e.cleanUp();
    }

}
