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

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;

import com.kodemore.string.KmStringBuilder;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * This listens for changed is the call state and will display a toast message
 * when there is either an incoming or outgoing call.
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.READ_LOGS" />
 */
public class TyPhoneStateActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private PhoneStateListener _listener;
    private TelephonyManager   _manager;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _listener = newPhoneStateListener();
        _manager = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    private PhoneStateListener newPhoneStateListener()
    {
        return new PhoneStateListener()
        {
            @Override
            public void onCallStateChanged(int state, String incomingNumber)
            {
                switch ( state )
                {
                    case TelephonyManager.CALL_STATE_RINGING:
                        toast("Incoming: " + incomingNumber);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        toast("Off Hook");
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        toast("Idle");
                        break;
                }
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addText(createMessage());
        return root;
    }

    private String createMessage()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("This listens for changes in the phone state.");
        out.println("This states that the phone can be in are Incoming, Off Hook, and Idle.");
        out.println();
        out.println("Incoming means the device is receiving a call and is rigning.  When this "
            + "state is detected, a toast is displayed showing the number of the incoming call.");
        out.println("Off Hook is when the phone is currently active, during a call.");
        out.println("Idle is the state where the phone is not active, nor currently ringing.");
        return out.toString();
    }

    //##################################################
    //# life cycle
    //##################################################

    @Override
    protected void onResume()
    {
        super.onResume();

        _manager.listen(_listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        _manager.listen(_listener, PhoneStateListener.LISTEN_NONE);
    }

    //##################################################
    //# action
    //##################################################

    //##################################################
    //# handle
    //##################################################

    //##################################################
    //# utility
    //##################################################
}
