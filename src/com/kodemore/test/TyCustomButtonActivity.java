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
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.RR;

/**
 * Test some custom buttons.
 */
public class TyCustomButtonActivity
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

        KmRowLayout row;
        row = root.addRow();
        row.addButton("Normal", newTestAction());
        row.addButton("Normal", newTestAction());
        row.addButton("Normal", newTestAction());

        row = root.addRow();
        row.addView(newCustomLeftButton());
        row.addView(newCustomMiddleButton());
        row.addView(newCustomRightButton());

        root.addFiller();

        row = root.addEvenRow();
        row.addView(newCustomLeftButton());
        row.addView(newCustomMiddleButton());
        row.addView(newCustomRightButton());

        return root;
    }

    private KmButton newCustomMiddleButton()
    {
        KmButton e;
        e = new KmButton(ui());
        e.setText("Custom");
        e.setOnClickListener(newTestAction());
        return e;
    }

    private KmButton newCustomLeftButton()
    {
        KmButton e;
        e = new KmButton(ui());
        e.setFirstImage(RR.drawable.backIcon);
        e.setText("Custom");
        e.setOnClickListener(newTestAction());
        return e;
    }

    private KmButton newCustomRightButton()
    {
        KmButton e;
        e = new KmButton(ui());
        e.setText("Custom");
        e.setSecondImage(RR.drawable.finishIcon);
        e.setOnClickListener(newTestAction());
        return e;
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
        toast("hi");
        System.out.println("TyWyattActivity.handleTest");
    }
}
