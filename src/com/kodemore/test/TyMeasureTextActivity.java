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

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextView;

/**
 * A simple layout.
 */
public class TyMeasureTextActivity
    extends KmActivity
{
    /**
     * (aaron) test class to measure width of text
     * without displaying it.
     */

    //##################################################
    //# variables
    //##################################################

    private KmTextView _measurementText;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _measurementText = new KmTextView(ui());
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        String text = "= $99,999.99";

        KmColumnLayout root;
        root = ui().newColumn();

        root.addText("Measuring text: [" + text + "]");
        root.addView(_measurementText);

        int width = measureTextWidth(text);

        _measurementText.setText("Width = " + width + "px");

        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private int measureTextWidth(CharSequence s)
    {
        return ui().measureTextWidth(s);
    }
}
