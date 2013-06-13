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
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.collection.KmList;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDialogManager;
import com.kodemore.view.KmTextView;

/**
 * I use a popup dialog, and preserve it across
 * restarts (e.g.: when you reorient the screen).
 * 
 * Automatically manage the dialog if the app restarts.
 */
public class TyListDialog1Activity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmDialogManager _askDialogManager;
    private KmTextView      _selected;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _askDialogManager = newAskDialogManager();
        _askDialogManager.register(ui());

        _selected = ui().newTextView();
    }

    //==================================================
    //= init :: dialog
    //==================================================

    private KmDialogManager newAskDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return createDialog();
            }
        };
    }

    private Dialog createDialog()
    {
        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("Select One");
        e.setItems(getList().toStringArray(), newDialogClickListener());
        e.setNegativeButton("Cancel");
        return e.create();
    }

    private KmList<String> getList()
    {
        KmList<String> v;
        v = new KmList<String>();

        v.add("Item 1");
        v.add("Item 2");
        v.add("Item 3");
        v.add("Item 4");
        v.add("Item 5");

        return v;
    }

    private OnClickListener newDialogClickListener()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int index)
            {
                _selected.setText("Selected: " + getList().get(index));
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
        root.addButton("List", _askDialogManager);
        root.addView(_selected);
        return root;
    }

}
