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

import android.content.ContentResolver;
import android.provider.Settings.Secure;
import android.view.View;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Demonstrate accessing the device's persistent, unique id.
 * 
 * Unfortunately, this is known to be broken for a number of 
 * manufacturers and devices.  Apparently, on those devices
 * that have a problem, the androidId is reported as "9774d56d682e549c".
 * The bug is specific to Android 2.2, so once we are no longer
 * trying to provide backward compatibility with 2.2, this should
 * be safe to use.
 * 
 * Even while supporting 2.2, this may provide a good-enough solution
 * in many cases.  As of Feb 2013, only 10% of the devices in the market
 * are running 2.2 or earlier, and many of the 2.2 devices don't have 
 * this bug anyway.  We are usually targeting customers that are likely
 * to be running newer devices.  And finally, in many cases it's not
 * really a problem if some of the devices happen to share the same id.
 */
public class TyAndroidIdActivity
    extends KmActivity
{
    //##################################################
    //# create
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
        root.addButton("test", newTestAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleTest();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleTest()
    {
        ContentResolver r = getContext().getContentResolver();
        String androidId = Secure.getString(r, Secure.ANDROID_ID);

        alert("Android ID", androidId);
    }
}
