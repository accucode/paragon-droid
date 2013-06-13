package com.kodemore.utility;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmFilePath;
import com.kodemore.intent.KmIntentUtility;

public class KmEmail
{
    //##################################################
    //# enums
    //##################################################

    private enum Type
    {
        PLAIN,
        HTML;
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * This must be an activity context.
     * A service or application context is NOT suitable.
     */
    private Context        _context;

    private KmList<String> _to;
    private KmList<String> _cc;
    private KmList<String> _bcc;
    private KmList<Uri>    _attachmentUris;
    private String         _subject;
    private String         _body;
    private String         _chooserTitle;

    private Type           _type;

    /**
     * If true (the default), the send() method will automatically
     * check to see if email is supported on the current device
     * before starting the intent. 
     */
    private boolean        _autoCheck;

    //##################################################
    //# constructor
    //##################################################

    public KmEmail(Context context)
    {
        _context = context;

        _to = new KmList<String>();
        _cc = new KmList<String>();
        _bcc = new KmList<String>();
        _attachmentUris = new KmList<Uri>();
        _subject = null;
        _body = null;
        setTypeHtml();
        _chooserTitle = "Email:";

        _autoCheck = true;
    }

    //##################################################
    //# is supported
    //##################################################

    /**
     * Return true if it appears that email is supported on 
     * this device.
     */
    public boolean isSupported()
    {
        return new KmPackageManager().supports(getIntent());
    }

    /**
     * Check if email is supported and return true if it is.
     * If not, then ask the user if they want to install an 
     * email client and search the marketplace for a suitable
     * app.
     */
    public boolean checkSupported()
    {
        if ( isSupported() )
            return true;

        KmIntentUtility.searchForApp(_context, "gmail");
        return false;
    }

    //##################################################
    //# accessing
    //##################################################

    public Type getType()
    {
        return _type;
    }

    public void setType(Type type)
    {
        _type = type;
    }

    public void setTypeHtml()
    {
        setType(KmEmail.Type.HTML);
    }

    public void setTypePlain()
    {
        setType(KmEmail.Type.PLAIN);
    }

    public KmList<String> getTo()
    {
        return _to;
    }

    public void setTo(String to)
    {
        _to.clear();
        addTo(to);
    }

    public void setTo(KmList<String> to)
    {
        _to = to;
    }

    public void addTo(String to)
    {
        _to.add(to);
    }

    public KmList<String> getCc()
    {
        return _cc;
    }

    public void setCc(KmList<String> cc)
    {
        _cc = cc;
    }

    public void addCc(String cc)
    {
        _cc.add(cc);
    }

    public KmList<String> getBcc()
    {
        return _bcc;
    }

    public void setBcc(KmList<String> bcc)
    {
        _bcc = bcc;
    }

    public void addBcc(String bcc)
    {
        _bcc.add(bcc);
    }

    public KmList<Uri> getAttachmentUris()
    {
        return _attachmentUris;
    }

    public void setAttachmentUris(KmList<Uri> attachmentUris)
    {
        _attachmentUris = attachmentUris;
    }

    public void addAttachmentUri(Uri uri)
    {
        _attachmentUris.add(uri);
    }

    public void addAttachmentUri(KmFilePath file)
    {
        addAttachmentUri(file.toUri());
    }

    public void addAttachmentUri(File file)
    {
        Uri uri = Uri.fromFile(file);

        _attachmentUris.add(uri);
    }

    public String getSubject()
    {
        return _subject;
    }

    public void setSubject(String subject)
    {
        _subject = subject;
    }

    public String getBody()
    {
        return _body;
    }

    public void setBody(String body)
    {
        _body = body;
    }

    public String getChooserTitle()
    {
        return _chooserTitle;
    }

    public void setChooserTitle(String chooserTitle)
    {
        _chooserTitle = chooserTitle;
    }

    public boolean getAutoCheck()
    {
        return _autoCheck;
    }

    public void setAutoCheck(boolean e)
    {
        _autoCheck = e;
    }

    public void disableAutoCheck()
    {
        setAutoCheck(false);
    }

    //##################################################
    //# utility
    //##################################################

    public Intent getIntent()
    {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE, Uri.parse("mailto:"));

        switch ( _type )
        {
            case HTML:
                intent.setType("text/html");
                if ( _body != null )
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(_body));
                break;

            case PLAIN:
                intent.setType("text/plain");
                if ( _body != null )
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, _body);
                break;
        }

        if ( !_to.isEmpty() )
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, _to.toStringArray());

        if ( !_cc.isEmpty() )
            intent.putExtra(android.content.Intent.EXTRA_CC, _cc.toStringArray());

        if ( !_bcc.isEmpty() )
            intent.putExtra(android.content.Intent.EXTRA_BCC, _bcc.toStringArray());

        if ( !_attachmentUris.isEmpty() )
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, _attachmentUris.toArrayList());

        if ( _subject != null )
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, _subject);

        return intent;
    }

    public void send()
    {
        if ( getAutoCheck() )
            if ( !checkSupported() )
                return;

        _context.startActivity(Intent.createChooser(getIntent(), "Send Email"));
    }

}
