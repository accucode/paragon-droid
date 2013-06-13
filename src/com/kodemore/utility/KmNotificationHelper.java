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

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.kodemore.view.RR;

/**
 * I simplify the process of sending notifications.
 */
public class KmNotificationHelper
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The context.
     */
    private Context _context;

    /**
     * Identifies the notification so it can be subsequently 
     * updated or cancelled.  Defaults to 0, but should
     * usually be set to a semi-unique value.
     */
    private int     _id;

    /**
     * The message displayed across the top of the screen.
     * This should be _very_ short.
     */
    private String  _ticker;

    /**
     * The title displayed when the notification is opened.
     */
    private String  _title;

    /**
     * The message displayed when the notification is opened.
     * This can be a relatively long message.
     */
    private String  _message;

    /**
     * Generally when the event happened.
     * Defaults to now.
     */
    private long    _when;

    /**
     * The icon resource identifier.  
     */
    private int     _icon;

    /**
     * The action to take when the user clicks on the notification.
     */
    private Intent  _onClick;

    //##################################################
    //# constructor 
    //##################################################

    public KmNotificationHelper(Context context)
    {
        _context = context;
        _id = 0;
        _when = System.currentTimeMillis();
    }

    //##################################################
    //# accessing
    //##################################################

    public void setTicker(String e)
    {
        _ticker = e;
    }

    public void setTitle(String e)
    {
        _title = e;
    }

    public void setMessage(String e)
    {
        _message = e;
    }

    public void setWhen(long ms)
    {
        _when = ms;
    }

    //##################################################
    //# on click
    //##################################################

    public void setOnClick(Class<? extends Activity> e)
    {
        _onClick = new Intent(getContext(), e);
    }

    //##################################################
    //# icons
    //##################################################

    public void setIconSend()
    {
        _icon = RR.drawable.sendIcon;
    }

    public void setIconWarning()
    {
        _icon = RR.drawable.warningIcon;
    }

    //##################################################
    //# run
    //##################################################

    /**
     * Send the notification.
     */
    public void send()
    {
        Context appContext = getContext().getApplicationContext();

        PendingIntent intent;
        intent = getPendingIntent();

        Notification notification;
        notification = new Notification(_icon, _ticker, _when);
        notification.setLatestEventInfo(appContext, _title, _message, intent);
        notification.flags = Kmu.addBitFlagsTo(notification.flags, Notification.FLAG_AUTO_CANCEL);

        String svc;
        svc = Activity.NOTIFICATION_SERVICE;

        NotificationManager mgr;
        mgr = (NotificationManager)appContext.getSystemService(svc);
        mgr.notify(_id, notification);
    }

    private PendingIntent getPendingIntent()
    {
        // support various options, but not commonly used.
        int flags = 0;

        // not used as per google api docs
        int requestCode = 0;

        Context appContext = getContext().getApplicationContext();

        Intent intent = _onClick;
        if ( intent == null )
            intent = new Intent();

        return PendingIntent.getActivity(appContext, requestCode, intent, flags);
    }

    //##################################################
    //# support
    //##################################################

    private Context getContext()
    {
        return _context;
    }

}
