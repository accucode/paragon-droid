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
public class KmOneLineView
    extends KmColumnLayout
{
    //##################################################
    //# static 
    //##################################################

    public static KmOneLineView createOrCast(KmUiManager ui, Object e)
    {
        if ( e instanceof KmOneLineView )
            return (KmOneLineView)e;

        return new KmOneLineView(ui);
    }

    //##################################################
    //# variables
    //##################################################

    private final KmTextView _textView;

    //##################################################
    //# constructor
    //##################################################

    public KmOneLineView(KmUiManager ui)
    {
        super(ui);

        _textView = addText();
        _textView.setSingleLine();
        _textView.setTextSize(20);
        _textView.getHelper().setPadding(10);
    }

    public KmOneLineView(KmUiManager ui, Object e)
    {
        this(ui);
        setText(e);
    }

    //##################################################
    //# accessing
    //##################################################

    public KmTextView getTextView()
    {
        return _textView;
    }

    public void setText(Object e)
    {
        _textView.setText(Kmu.toDisplay(e));
    }

    public void setTextBold(Object e)
    {
        _textView.setText(Kmu.toDisplay(e));

        KmTextViewHelper h = _textView.getHelper();
        h.setBold();
    }

    public void setTextLightGray(Object e)
    {
        setText(e);
        setTextColor(Color.LTGRAY);
    }

    public void setTextColor(int color)
    {
        _textView.getHelper().setTextColor(color);
    }
}
