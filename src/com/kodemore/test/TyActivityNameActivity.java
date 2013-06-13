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

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;

/**
 * I show how to set the activity name in the android activity name bar at the
 * top of your device.
 */
public class TyActivityNameActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _textField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addLabel("Activity Name");
        root.addView(_textField);

        root.addButton("Get Activity Name", newGetNameAction());
        root.addButton("Set Activity Name", newSetNameAction());

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newGetNameAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleGetName();
            }
        };
    }

    private KmAction newSetNameAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetName();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleGetName()
    {
        _textField.setValue(getTitle().toString());
    }

    private void handleSetName()
    {
        setTitle(_textField.getValue());
    }

}
