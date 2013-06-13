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

public class TyFocusSoftKeyToggleActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    KmTextField _textField;
    KmTextField _textField2;
    KmTextField _statusField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
        _textField2 = ui().newTextField();

        _statusField = ui().newTextField();
        _statusField.setSoftKeyDisabled();
        _statusField.setValue("off");
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addLabel("Field 1");
        root.addView(_textField);
        root.addLabel("Field 2");
        root.addView(_textField2);
        root.addLabel("Soft Key Status");
        root.addView(_statusField);
        root.addButton("Keyboard Toggle", newSoftKeyAction());
        root.addButton("Set Focus Field 1", newFieldOneAction());
        root.addButton("Set Focus Field 2", newFieldTwoAction());

        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################

    /**
     * Used instead of onResumeAsync() due to no User Interface interaction.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        hideSoftKey();
    }

    //##################################################
    //# action
    //##################################################
    private KmAction newSoftKeyAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSoftKey();
            }
        };
    }

    private KmAction newFieldOneAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleFieldOne();
            }
        };
    }

    private KmAction newFieldTwoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleFieldTwo();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSoftKey()
    {
        if ( _statusField.getValue().equals("on") )
        {
            hideSoftKey();
            _statusField.setValue("off");
        }
        else
            if ( _statusField.getValue().equals("off") )
            {
                showSoftKey();
                _statusField.setValue("on");
            }
    }

    private void handleFieldOne()
    {
        _textField.requestFocus();
    }

    private void handleFieldTwo()
    {
        _textField2.requestFocus();
    }
}
