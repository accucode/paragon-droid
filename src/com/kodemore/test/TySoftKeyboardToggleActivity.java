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
 * Hide the soft keyboard.
 */
public class TySoftKeyboardToggleActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    KmTextField _textField;
    KmTextField _textField2;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
        _textField2 = ui().newTextField();

        _textField2.setSoftKeyDisabled();
        _textField2.setValue("You can't edit me manually.");
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addLabel("Text");
        root.addView(_textField);
        root.addLabel("Text2");
        root.addView(_textField2);
        root.addButton("Show Keyboard", newSoftKeyAction("show"));
        root.addButton("Hide Keyboard", newSoftKeyAction("hide"));
        root.addButton("Enable Suggestions", newSoftKeyAction("enablesuggest"));
        root.addButton("Disable Suggestions", newSoftKeyAction("disablesuggest"));

        return root;
    }

    //##################################################
    //# action
    //##################################################
    private KmAction newSoftKeyAction(final String keyOption)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSoftKey(keyOption);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSoftKey(String keyOption)
    {
        if ( keyOption.equals("hide") )
            hideSoftKey();
        else
            if ( keyOption.equals("show") )
                showSoftKey();

        if ( keyOption.equals("disablesuggest") )
            _textField.disableSoftKeySuggest();
        else
            if ( keyOption.equals("enablesuggest") )
                _textField.enableSoftKeySuggest();
    }
}
