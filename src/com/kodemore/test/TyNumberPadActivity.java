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

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTableLayout;
import com.kodemore.view.KmTableRow;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmUiManager;

/**
 * Sample number pad.
 * This just shows a sample layout.
 * The logic isn't hooked up yet.  
 */
public class TyNumberPadActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _field;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _field = ui().newTextField();
        _field.setEnabled(false);
        _field.setCursorVisible(false);
        _field.setFocusable(false);
        _field.setFocusableInTouchMode(false);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(_field);
        root.addSpace();
        root.addView(createDigitTable());
        root.addSpace();
        root.addButton("Ok", newToastAction("Ok"));
        return root;
    }

    private KmTableLayout createDigitTable()
    {
        KmUiManager ui = ui();

        KmTableLayout table;
        table = ui.newTableLayout();
        table.setGravity(Gravity.FILL);

        KmTableRow row;
        row = table.addRow();
        addButton(row, "7");
        addButton(row, "8");
        addButton(row, "9");

        row = table.addRow();
        addButton(row, "4");
        addButton(row, "5");
        addButton(row, "6");

        row = table.addRow();
        addButton(row, "1");
        addButton(row, "2");
        addButton(row, "3");

        row = table.addRow();
        addButton(row, "<");
        addButton(row, "0");
        addButton(row, ".");

        return table;
    }

    private void addButton(KmTableRow row, String s)
    {
        KmButton button;
        button = ui().newButton(s, newKeyAction(s));
        button.setGravity(Gravity.CENTER);
        button.setHeight(100);
        button.setTextSize(36);

        if ( s == null )
            button.getHelper().hide();

        row.addView(button);

        TableRow.LayoutParams params;
        params = (TableRow.LayoutParams)button.getLayoutParams();
        params.weight = 1;
        params.height = ViewGroup.LayoutParams.FILL_PARENT;
        params.width = ViewGroup.LayoutParams.FILL_PARENT;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newKeyAction(final String s)
    {
        if ( s == null )
            return null;

        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleKey(s);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleKey(String s)
    {
        _field.setText(_field.getText().toString() + s);
    }

}
