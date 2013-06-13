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

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.CheckBox;

public class KmCheckBox
    extends CheckBox
{
    //##################################################
    //# constants
    //##################################################

    private static final String STATE_ORIGINAL = "original";
    private static final String STATE_CHECKED  = "checked";

    //##################################################
    //# variables
    //##################################################

    private KmUiManager         _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmCheckBox(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
    }

    public KmCheckBox(KmUiManager ui, int style)
    {
        super(ui.getContext(), null, style);

        _ui = ui;
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    public KmViewHelper getHelper()
    {
        return new KmViewHelper(this);
    }

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
        setFreezesText(true);
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# convenience
    //##################################################

    public boolean isNotChecked()
    {
        return !isChecked();
    }

    //##################################################
    //# save state
    //##################################################

    @Override
    public Parcelable onSaveInstanceState()
    {
        Parcelable original = super.onSaveInstanceState();

        Bundle e;
        e = new Bundle();
        e.putParcelable(STATE_ORIGINAL, original);
        e.putBoolean(STATE_CHECKED, isChecked());
        return e;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if ( state instanceof Bundle )
        {
            Bundle bundle = (Bundle)state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_ORIGINAL));
            setChecked(bundle.getBoolean(STATE_CHECKED));
            return;
        }

        super.onRestoreInstanceState(state);
    }
}
