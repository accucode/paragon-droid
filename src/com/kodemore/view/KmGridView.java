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

import android.app.Activity;
import android.widget.GridView;

import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.Kmu;

public class KmGridView
    extends GridView
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmGridView(KmUiManager ui)
    {
        super(ui.getContext());

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
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    private boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# columns
    //##################################################

    public void setColumnCount(int n)
    {
        setNumColumns(n);
    }

    public void setColumnCountAuto()
    {
        setColumnCount(-1);
    }

    //##################################################
    //# buttons
    //##################################################

    public KmButton addButton(CharSequence text, KmAction action)
    {
        KmButton e = ui().newButton(text, action);
        addView(e);
        return e;
    }

    public KmButton addButton(CharSequence text, KmSimpleIntentCallback callback)
    {
        return addButton(text, callback.newAction());
    }

    public KmButton addButton(CharSequence text, Class<? extends Activity> activity)
    {
        KmAction action = Kmu.newStartAction(getContext(), activity);
        return addButton(text, action);
    }

    public KmButton addButton(final Class<? extends Activity> activity)
    {
        String s = Kmu.formatActivity(activity);
        return addButton(s, activity);
    }

    //##################################################
    //# stretch
    //##################################################

    public void setStretchNone()
    {
        setStretchMode(NO_STRETCH);
    }

    public void setStretchSpacing()
    {
        setStretchMode(STRETCH_SPACING);
    }

    public void setStretchUniform()
    {
        setStretchMode(STRETCH_SPACING_UNIFORM);
    }

    public void setStretchColumn()
    {
        setStretchMode(STRETCH_COLUMN_WIDTH);
    }
}
