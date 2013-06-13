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

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;

/**
 * Test the scrollable wheel widget.
 * Relies on the wheel library from
 * http://code.google.com/p/android-wheel/
 */
public class TyWheelIntegerActivity
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
        root.addText("Numeric Wheels");

        KmRowLayout row;
        row = root.addRow();
        row.addView(newNumericWheel(1, 10));
        row.addView(newNumericWheel(50, 80));
        row.addView(newNumericWheel(1, 999));
        row.addView(newNumericWheel(100, 999));

        return root;
    }

    private View newNumericWheel(int min, int max)
    {
        int len = (max + "").length();
        String format = "%0" + len + "d";

        WheelViewAdapter a;
        a = new NumericWheelAdapter(getContext(), min, max, format);

        WheelView e;
        e = new WheelView(getContext());
        e.setViewAdapter(a);

        return e;
    }
}
