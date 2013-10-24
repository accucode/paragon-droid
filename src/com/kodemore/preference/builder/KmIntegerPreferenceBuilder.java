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

package com.kodemore.preference.builder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.widget.EditText;

import com.kodemore.filter.KmIntegerFilter;
import com.kodemore.preference.KmEditIntegerPreference;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.KmString;

/**
 * See superclass.
 */
public class KmIntegerPreferenceBuilder
    extends KmPreferenceBuilder<Integer>
{
    //##################################################
    //# constructor
    //##################################################

    public KmIntegerPreferenceBuilder()
    {
        super();
    }

    //##################################################
    //# accessing
    //##################################################

    protected Integer getRawValue(SharedPreferences pp)
    {
        try
        {
            return pp.contains(getKey())
                ? pp.getInt(getKey(), 0)
                : null;
        }
        catch ( Exception ex )
        {
            Editor edit;
            edit = pp.edit();
            edit.remove(getKey());
            edit.commit();

            KmLog.warn(getClass(), ex);
            return null;
        }
    }

    protected void setRawValue(Editor edit, Integer value)
    {
        if ( value == null )
            edit.remove(getKey());
        else
            edit.putInt(getKey(), value);
    }

    protected InputFilter[] getFilters()
    {
        return new InputFilter[]
        {
            new KmIntegerFilter()
        };
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Integer getValue(SharedPreferences pp)
    {
        return KmString.create(getRawValue(pp)).parseInteger();
    }

    @Override
    public void setValue(Editor edit, Integer value)
    {
        if ( value == null )
            setRawValue(edit, null);
        else
            setRawValue(edit, value);
    }

    @Override
    public void installOn(PreferenceScreen scr)
    {
        Context context = scr.getContext();
        SharedPreferences prefs = scr.getSharedPreferences();

        KmEditIntegerPreference p;
        p = new KmEditIntegerPreference(context);
        p.setKey(getKey());
        p.setDefaultValue(getDefaultValue());
        p.setTitle(getTitle());
        p.setDialogTitle(getTitle());
        p.setSummary(formatSummaryFor(getValue(prefs)));
        p.setOnPreferenceChangeListener(newChangeListener());

        EditText field;
        field = p.getEditText();
        field.setSingleLine();

        InputFilter[] filters = getFilters();
        if ( filters != null )
            field.setFilters(filters);

        scr.addPreference(p);
    }

}
