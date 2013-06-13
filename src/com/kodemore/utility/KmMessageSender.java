/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.utility;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;

import com.kodemore.collection.KmList;


/**
 * Send a message. Sample usage:
 * 
 * KmMessageSender e;
 * e = new KmMessageSender(getContext());
 * e.setSubject("Hello");
 * e.setMessageText("World");
 * e.send(); 
 */
public class KmMessageSender
{
    //##################################################
    //# variables
    //##################################################

    private Context        _context;

    /**
     * The optional list of to's.
     */
    private KmList<String> _toList;

    /**
     * The optional list of cc's.
     */
    private KmList<String> _ccList;

    /**
     * The subject line.
     */
    private String         _subject;

    /**
     * The main message to send.  Managed as a CharSequence
     * so that clients can set simple string (text) values
     * or spanned (html) values.
     */
    private CharSequence   _message;

    /**
     * If false (the default) Android will simply launch
     * the default activity, if a default activity has 
     * been previously selected by the user.  If true,
     * then the user will be prompted to select an activity
     * even if a default is already selected.
     */
    private boolean        _chooser;

    /**
     * The message prompt to show when forcing.
     */
    private String         _chooserMessage;

    /**
     * The ~mime type used to send the message. This is what
     * determines the list of candidate activities available
     * to process the request.  Typically, the client does
     * not need to set this explicitly, it is automatically
     * set based on the usage of setMessage...
     */
    private String         _mimeType;

    //##################################################
    //# constructor
    //##################################################

    public KmMessageSender(Context e)
    {
        _context = e;
        _toList = new KmList<String>();
        _ccList = new KmList<String>();
        _chooser = false;
        _chooserMessage = "Complete action using";
        setMimeTypeText();
    }

    //##################################################
    //# context
    //##################################################

    public Context getContext()
    {
        return _context;
    }

    //##################################################
    //# to list
    //##################################################

    public KmList<String> getToList()
    {
        return _toList;
    }

    public String[] getToArray()
    {
        KmList<String> v = getToList();
        return v.toArray(new String[v.size()]);
    }

    public void setToList(KmList<String> v)
    {
        _toList = v;
    }

    public boolean hasToList()
    {
        return getToList().isNotEmpty();
    }

    public void addTo(String e)
    {
        _toList.add(e);
    }

    //##################################################
    //# cc list
    //##################################################

    public KmList<String> getCcList()
    {
        return _ccList;
    }

    public String[] getCcArray()
    {
        KmList<String> v = getCcList();
        return v.toArray(new String[v.size()]);
    }

    public void setCcList(KmList<String> v)
    {
        _ccList = v;
    }

    public boolean hasCcList()
    {
        return getCcList().isNotEmpty();
    }

    public void addCc(String e)
    {
        _ccList.add(e);
    }

    //##################################################
    //# message
    //##################################################

    public CharSequence getMessage()
    {
        return _message;
    }

    private void setMessage(CharSequence e)
    {
        _message = e;
    }

    public void setMessageText(String text)
    {
        setMessage(text);
        setMimeTypeText();
    }

    public void setMessageHtml(String html)
    {
        setMessageHtml(Html.fromHtml(html));
    }

    public void setMessageHtml(Spanned html)
    {
        setMessage(html);
        setMimeTypeHtml();
    }

    public boolean hasMessage()
    {
        return Kmu.hasValue(getMessage());
    }

    //##################################################
    //# subject
    //##################################################

    public String getSubject()
    {
        return _subject;
    }

    public void setSubject(String e)
    {
        _subject = e;
    }

    public boolean hasSubject()
    {
        return Kmu.hasValue(getSubject());
    }

    //##################################################
    //# chooser
    //##################################################

    public boolean getChooser()
    {
        return _chooser;
    }

    public void setChooser(boolean e)
    {
        _chooser = true;
    }

    public void setChooser()
    {
        setChooser(true);
    }

    public void setChooser(String e)
    {
        _chooser = true;
        _chooserMessage = e;
    }

    public String getChooserMessage()
    {
        return _chooserMessage;
    }

    //##################################################
    //# type
    //##################################################

    public String getMimeType()
    {
        return _mimeType;
    }

    public void setMimeType(String e)
    {
        _mimeType = e;
    }

    public void setMimeTypeText()
    {
        setMimeType("text/plain");
    }

    public void setMimeTypeHtml()
    {
        setMimeType("text/html");
    }

    public void setMimeTypeEmail()
    {
        setMimeType("message/rfc822");
    }

    //##################################################
    //# send
    //##################################################

    public void send()
    {
        Intent intent;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType(getMimeType());

        if ( hasToList() )
            intent.putExtra(Intent.EXTRA_EMAIL, getToArray());

        if ( hasCcList() )
            intent.putExtra(Intent.EXTRA_CC, getCcArray());

        if ( hasMessage() )
            intent.putExtra(Intent.EXTRA_TEXT, getMessage());

        if ( hasSubject() )
            intent.putExtra(Intent.EXTRA_SUBJECT, getSubject());

        if ( getChooser() )
            intent = Intent.createChooser(intent, getChooserMessage());

        getContext().startActivity(intent);
    }

}
