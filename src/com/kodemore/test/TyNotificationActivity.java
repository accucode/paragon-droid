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

package com.kodemore.test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.kodemore.utility.KmNotificationHelper;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.RR;

/**
 * Add a notification.  Typically used for background
 * notifications, e.g.: new mail, new sms, etc...
 */
public class TyNotificationActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Notify Original", newNotifyOriginalAction());
        root.addButton("Notify Wrapper", newNotifyWrappedAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newNotifyOriginalAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleNotifyOriginal();
            }
        };
    }

    private KmAction newNotifyWrappedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleNotifyWrapped();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    /**
     * Create a notification using the original code pattern.
     * This is just silly.  And painful.
     */
    private void handleNotifyOriginal()
    {
        Context context = getContext();
        Context appContext = getApplicationContext();

        int icon = RR.drawable.alertIcon;
        String ticker = "my ticker";
        long when = System.currentTimeMillis();

        int id = 1;
        String title = "my title";
        String msg = "my text";

        int requestCode = 0;
        int flags = 0;

        Intent intent = new Intent(context, TyMessageActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
            appContext,
            requestCode,
            intent,
            flags);

        Notification e;
        e = new Notification(icon, ticker, when);
        e.setLatestEventInfo(appContext, title, msg, pendingIntent);

        String svc = Activity.NOTIFICATION_SERVICE;

        NotificationManager mgr;
        mgr = (NotificationManager)getSystemService(svc);
        mgr.notify(id, e);
    }

    /**
     * Create the same notification using our helper.
     * Much better. 
     */
    private void handleNotifyWrapped()
    {
        KmNotificationHelper h;
        h = new KmNotificationHelper(getContext());
        h.setIconSend();
        h.setTicker("my tickerr");
        h.setTitle("my titlee");
        h.setMessage("my messagee");
        h.setOnClick(TyMessageActivity.class);
        h.send();
    }

}
