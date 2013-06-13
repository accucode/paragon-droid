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

import android.app.Dialog;
import android.view.View;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDialogManager;
import com.kodemore.view.KmTextField;
import com.kodemore.view.value.KmStringValue;

public class TyDialogWithMultilineTextFieldActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################//

    private KmDialogManager _dialogManager;
    private KmTextField     _nameField;
    private KmStringValue   _stringValue;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _stringValue = ui().newStringValue();
        _stringValue.setAutoSave();

        _dialogManager = newEnterNameDialogManager();
        _dialogManager.register(ui());
        _dialogManager.setAutoSave();
    }

    //==================================================
    //= init :: dialog
    //==================================================

    private KmDialogManager newEnterNameDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return createEnterNameDialog();
            }
        };
    }

    private Dialog createEnterNameDialog()
    {
        _nameField = new KmTextField(ui());
        _nameField.setMultiLine();

        if ( _stringValue != null && _stringValue.hasValue() )
            _nameField.setValue(_stringValue.getValue());

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("Enter Name");
        e.setView(_nameField);
        e.setPositiveButton("Ok", newSaveNameAction());
        e.setNegativeButton("Cancel");
        return e.create();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Show Dialog", showDialogAction());
        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################//

    @Override
    protected void onPause()
    {
        super.onPause();
        handleSaveName();
    }

    //##################################################
    //# action
    //##################################################//

    private KmAction showDialogAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowDialog();
            }
        };
    }

    private KmAction newSaveNameAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSaveName();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################//

    private void handleShowDialog()
    {
        _dialogManager.show();
    }

    private void handleSaveName()
    {
        if ( _nameField == null || _stringValue == null )
            return;

        String name = _nameField.getValue();

        _stringValue.setValue(name);

        toast("Saving name: %s...", name);
    }

}
