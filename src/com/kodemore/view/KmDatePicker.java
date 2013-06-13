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

import android.widget.DatePicker;

import com.kodemore.time.KmDate;

public class KmDatePicker
    extends DatePicker
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmDatePicker(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
    }

    public void init(KmDate date, OnDateChangedListener onDateChangedListener)
    {
        int yy = date.getYear();
        int mm = date.getMonth() - 1;
        int dd = date.getDay();

        super.init(yy, mm, dd, onDateChangedListener);
    }

    //##################################################
    //# core
    //##################################################

    private KmUiManager ui()
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

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# value
    //##################################################

    public KmDate getValue()
    {
        int yy = getYear();
        int mm = getMonth() + 1;
        int dd = getDayOfMonth();

        return KmDate.create(yy, mm, dd);
    }

    public void setValue(KmDate e)
    {
        int yy = e.getYear();
        int mm = e.getMonth() - 1;
        int dd = e.getDay();

        updateDate(yy, mm, dd);
    }
}
