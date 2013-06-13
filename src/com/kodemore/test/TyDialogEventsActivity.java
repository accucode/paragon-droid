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

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDialogManager;

/**
 * Test some dialog events.
 */
public class TyDialogEventsActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmDialogManager _dialogManager;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _dialogManager = newDialogManager();
    }

    //==================================================
    //= init :: dialog
    //==================================================

    private KmDialogManager newDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                KmDialogBuilder e;
                e = new KmDialogBuilder(ui());
                e.setTitle("Some Title");
                e.setMessage("Some Message");
                e.setPositiveButton("Yes", newYesAction());
                e.setNegativeButton("No", newNoAction());

                AlertDialog d;
                d = e.create();
                d.setOnShowListener(newShowAction());
                d.setOnDismissListener(newDismissAction());
                return d;
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
        root.addText(getMessage());
        root.addFiller();
        root.addButton("Show", _dialogManager);
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newShowAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShow();
            }
        };
    }

    private KmAction newDismissAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleDismiss();
            }
        };
    }

    private KmAction newYesAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleYes();
            }
        };
    }

    private KmAction newNoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleNo();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleShow()
    {
        toast("show");
    }

    private void handleDismiss()
    {
        toast("dismiss");
    }

    private void handleYes()
    {
        toast("yes");
    }

    private void handleNo()
    {
        toast("no");
    }

    //##################################################
    //# utility
    //##################################################

    private String getMessage()
    {
        return ""
            + "Unfortunately, this demonstrates that onDismiss is called _after_ "
            + "either the positive or negative buttons.  This makes it awkward "
            + "to hook up cleanup code since cleanup code needs to be handled "
            + "_before_ we process the yes/no actions.";
    }
}
