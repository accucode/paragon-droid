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
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.kodemore.time.KmDate;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDatePicker;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmTimePicker;

/**
 * Display the date and time pickers.
 */
public class TyDatePickActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextView   _dateText;
    private KmDatePicker _datePicker;

    private KmTextView   _timeText;
    private KmTimePicker _timePicker;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _dateText = ui().newTextView();
        _dateText.setAutoSave();

        _datePicker = ui().newDatePicker();
        _datePicker.init(getInitialDate(), newDateAction());
        _datePicker.setAutoSave();

        _timeText = ui().newTextView();
        _timeText.setAutoSave();

        _timePicker = ui().newTimePicker();
        _timePicker.setCurrentHour(8);
        _timePicker.setCurrentMinute(30);
        _timePicker.setOnTimeChangedListener(newTimeAction());
        _timePicker.setAutoSave();
    }

    private KmDate getInitialDate()
    {
        return KmDate.create(2013, 1, 1);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addSpace();
        root.addView(_dateText);
        root.addView(_datePicker);
        root.addSpace();
        root.addView(_timeText);
        root.addView(_timePicker);

        updateDateText();
        updateTimeText();

        return root;
    }

    //##################################################
    //# listener
    //##################################################    

    private OnTimeChangedListener newTimeAction()
    {
        return new OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                updateTimeText();
            }
        };
    }

    //##################################################
    //# action
    //##################################################

    private OnDateChangedListener newDateAction()
    {
        return new OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                updateDateText();
            }
        };
    }

    //##################################################
    //# utility
    //##################################################

    private void updateDateText()
    {
        int yy = _datePicker.getYear();
        int mm = _datePicker.getMonth() + 1;
        int dd = _datePicker.getDayOfMonth();

        String msg = Kmu.format("Date: %s/%s/%s", mm, dd, yy);
        _dateText.setText(msg);
    }

    private void updateTimeText()
    {
        int hh = _timePicker.getCurrentHour();
        int mm = _timePicker.getCurrentMinute();

        String msg = Kmu.format("Time: %s:%s", hh, mm);
        _timeText.setText(msg);
    }

}
