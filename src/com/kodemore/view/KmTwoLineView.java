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

package com.kodemore.view;

import android.graphics.Color;

import com.kodemore.utility.Kmu;

/**
 * A simple view, useful for custom lists. 
 */
public class KmTwoLineView
    extends KmColumnLayout
{
    //##################################################
    //# static 
    //##################################################

    public static KmTwoLineView createOrCast(KmUiManager ui, Object e)
    {
        if ( e instanceof KmTwoLineView )
            return (KmTwoLineView)e;

        return new KmTwoLineView(ui);
    }

    //##################################################
    //# variables
    //##################################################

    private final KmTextView _textView1;
    private final KmTextView _textView2;

    //##################################################
    //# constructor
    //##################################################

    public KmTwoLineView(KmUiManager ui)
    {
        super(ui);

        _textView1 = addText();
        _textView1.setTextSize(20);
        _textView1.getHelper().setBold();
        _textView1.getHelper().setTextColor(Color.WHITE);
        _textView1.getHelper().setPadding(10);
        _textView1.getHelper().setPaddingBottom(0);

        _textView2 = addText();
        _textView2.setTextSize(14);
        _textView2.getHelper().setPadding(10);
        _textView2.getHelper().setPaddingTop(0);
    }

    public KmTwoLineView(KmUiManager ui, Object one, Object two)
    {
        this(ui);
        setLine1(one);
        setLine2(two);
    }

    //##################################################
    //# accessing
    //##################################################

    public KmTextView getTextView1()
    {
        return _textView1;
    }

    public KmTextView getTextView2()
    {
        return _textView2;
    }

    public void setLine1(Object e)
    {
        _textView1.setText(Kmu.toDisplay(e));
    }

    public void setLine2(Object e)
    {
        _textView2.setText(Kmu.toDisplay(e));
    }

}
