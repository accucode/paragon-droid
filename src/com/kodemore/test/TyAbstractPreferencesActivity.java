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

import com.kodemore.preference.wrapper.KmSimplePreferenceWrapper;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmIntegerField;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;

/**
 * My subclasses demonstrate the difference between
 * using shared preferences vs private preferences.
 * 
 * Shared preferences are shared across the entire application.
 * Private preferences are specific to an individual activity.
 */
public abstract class TyAbstractPreferencesActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String KEY_COLOR = "color";
    private static final String KEY_SIZE  = "size";

    //##################################################
    //# variables
    //##################################################

    private KmTextField         _colorField;
    private KmIntegerField      _sizeField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _colorField = ui().newTextField();
        _colorField.setAutoSave();

        _sizeField = ui().newIntegerField();
        _sizeField.setAutoSave();
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
        row.addButton("Read", newReadAction());
        row.addButton("Write", newWriteAction());

        root.addText("Color");
        root.addView(_colorField);

        root.addText("Size");
        root.addView(_sizeField);
        return root;
    }

    //##################################################
    //# abstract
    //##################################################

    protected abstract KmSimplePreferenceWrapper getTestPreferences();

    //##################################################
    //# action
    //##################################################

    private KmAction newReadAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRead();
            }
        };
    }

    private KmAction newWriteAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleWrite();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        KmSimplePreferenceWrapper pp = getTestPreferences();

        String color = pp.getString(KEY_COLOR);
        Integer size = pp.getInteger(KEY_SIZE);

        _colorField.setValue(color);
        _sizeField.setValue(size);

        toast("read");
    }

    private void handleWrite()
    {
        String color = _colorField.getValue();
        Integer size = _sizeField.getValue();

        KmSimplePreferenceWrapper pp;
        pp = getTestPreferences();
        pp.setString(KEY_COLOR, color);
        pp.setInteger(KEY_SIZE, size);

        toast("write");
    }
}
