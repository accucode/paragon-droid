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
import android.preference.EditTextPreference;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.widget.EditText;

import com.kodemore.utility.KmLog;

/**
 * See superclass.
 */
public abstract class KmTextPreferenceBuilder<T>
    extends KmPreferenceBuilder<T>
{
    //##################################################
    //# constructor
    //##################################################

    public KmTextPreferenceBuilder()
    {
        super();
    }

    //##################################################
    //# accessing
    //##################################################

    protected String getRawValue(SharedPreferences pp)
    {
        try
        {
            return pp.contains(getKey())
                ? pp.getString(getKey(), null)
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

    protected void setRawValue(Editor edit, String value)
    {
        if ( value == null )
            edit.remove(getKey());
        else
            edit.putString(getKey(), value);
    }

    @Override
    public void installOn(PreferenceScreen scr)
    {
        Context context = scr.getContext();
        SharedPreferences prefs = scr.getSharedPreferences();

        EditTextPreference p;
        p = new EditTextPreference(context);
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

    protected InputFilter[] getFilters()
    {
        return null;
    }
}
