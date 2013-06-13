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

import android.view.View;
import android.widget.Checkable;

/**
 * This is the line view for the checked list.
 * It always contains a checkbox on the left side and handles the
 * toggling of the checkbox.  It also contains a frameView that 
 * fills there rest of the space in the line.
 */
public class KmCheckFrameLineView
    extends KmRowLayout
    implements Checkable
{
    //##################################################
    //# static 
    //##################################################

    public static KmCheckFrameLineView createOrCast(KmUiManager ui, Object e)
    {
        if ( e instanceof KmCheckFrameLineView )
            return (KmCheckFrameLineView)e;

        return new KmCheckFrameLineView(ui);
    }

    //##################################################
    //# variables
    //##################################################

    private final KmCheckBox    _checkBox;
    private final KmFrameLayout _frame;

    //##################################################
    //# constructor
    //##################################################

    public KmCheckFrameLineView(KmUiManager ui)
    {
        super(ui);

        _checkBox = new KmCheckBox(ui());
        _checkBox.setFocusable(false);
        _checkBox.setClickable(false);

        _frame = new KmFrameLayout(ui());

        addView(_checkBox);
        addViewFullWeight(_frame);
    }

    //##################################################
    //# accessing
    //##################################################

    public void setContentView(View e)
    {
        _frame.setView(e);
    }

    //##################################################
    //# check box
    //##################################################

    @Override
    public boolean isChecked()
    {
        return _checkBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked)
    {
        if ( isCheckEnabled() )
            _checkBox.setChecked(checked);
        else
            _checkBox.setChecked(false);
    }

    @Override
    public void toggle()
    {
        if ( isCheckEnabled() )
            _checkBox.toggle();
    }

    //##################################################
    //# disable check box
    //##################################################

    public void disableCheckBox()
    {
        enableCheckBox(false);
    }

    public void enableCheckBox()
    {
        enableCheckBox(true);
    }

    public void enableCheckBox(boolean b)
    {
        _checkBox.setEnabled(b);
    }

    public boolean isCheckEnabled()
    {
        return _checkBox.isEnabled();
    }
}
