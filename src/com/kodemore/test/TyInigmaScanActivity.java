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

import android.content.Intent;
import android.view.View;

import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Use the I-nigma application to scan a barcode.
 * 
 * THIS CLASS CURRENTLY DOES NOT WORK.
 * I can start inigma application.  But don't know
 * how to get a programmatic response.  Sent email
 * to vendor.
 * 
 * Turns out there is no programmatic interface.  At
 * least not unless you are willing to pay thousands 
 * of dollars for it.
 */
public class TyInigmaScanActivity
    extends KmActivity
{
    //##################################################
    //# variables 
    //##################################################

    private KmSimpleIntentCallback _scanCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _scanCallback = newScanCallback();
        _scanCallback.register(ui());
    }

    private KmSimpleIntentCallback newScanCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public Intent getRequest()
            {
                Intent e;
                e = new Intent();
                e.setClassName(
                    "com.threegvision.products.inigma.Android",
                    "com.threegvision.products.inigma.Android.App");
                return e;
            }

            @Override
            public void handleOk(Intent data)
            {
                alert("Got Result!");
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
        root.addButton("Scan", _scanCallback);
        return root;
    }

}
