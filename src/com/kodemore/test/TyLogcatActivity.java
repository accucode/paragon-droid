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

import android.view.View;

import com.kodemore.utility.KmLog;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;

/**
 * I demonstrate the use of logcat.
 */
public class TyLogcatActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _messageField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _messageField = ui().newTextField("Test");
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(_messageField);
        root.addButton("Alert", newAlertAction());
        root.addButton("Debug", newDebugAction());
        root.addButton("System.out", newSystemOutAction());
        root.addButton("System.err", newSystemErrAction());
        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newAlertAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAlert();
            }
        };
    }

    private KmAction newDebugAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleDebug();
            }
        };
    }

    private KmAction newSystemOutAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSystemOut();
            }
        };
    }

    private KmAction newSystemErrAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSystemErr();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleDebug()
    {
        KmLog.debug(getMessage());
    }

    private void handleAlert()
    {
        alert(getMessage());
    }

    private void handleSystemOut()
    {
        // System.out is redirected to LogCat in MyApplication.
        System.out.println("Printed to System.out.");
    }

    private void handleSystemErr()
    {
        // System.err is redirected to LogCat in MyApplication.
        System.err.println("Printed to System.err.");
    }

    //##################################################
    //# utility
    //##################################################

    private String getMessage()
    {
        return _messageField.getText().toString();
    }

}
