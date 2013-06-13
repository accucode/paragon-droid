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

import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmClock;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmPopupWindow;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;

/**
 * I demonstrate a simple popup window
 */
public class TyPopupWindowActivity
    extends KmActivity
{
    //##################################################
    //# init
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
        row.addView(createButton());
        row.addViewFullWeight(createButton());
        row.addView(createButton());

        root.addFiller();

        row = root.addRow();
        row.addView(createButton());
        row.addViewFullWeight(createButton());
        row.addView(createButton());

        return root;
    }

    private View createButton()
    {
        KmButton e;
        e = ui().newButton();
        e.setText("Show Popup");
        e.setOnClickListener(newShowPopupAction(e));
        return e;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newShowPopupAction(final View anchor)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowPopup(anchor);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleShowPopup(View anchor)
    {
        KmPopupWindow e;
        e = getPopup();
        e.setContentView(getPopupContent());
        e.showAt(anchor);
    }

    //##################################################
    //# utility
    //##################################################

    private View getPopupContent()
    {
        KmTextView e;
        e = ui().newLabel();
        e.setGravityCenter();
        e.setText(getPopupMessage());
        return e;
    }

    private String getPopupMessage()
    {
        String now = KmClock.getNowLocal().getTime().format_h_mm_ss_am();

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("This Popup window is anchored to the");
        out.println("location of the button");
        out.println();
        out.println("The popup window can diplay any");
        out.println("arbitrary View you wish");
        out.println();
        out.println("Dismiss the popup by tapping the");
        out.println("button, pressing the back button,");
        out.println("or by tapping the Popup.");
        out.println();
        out.println("The time is now: " + now);

        return out.toString().trim();
    }

}
