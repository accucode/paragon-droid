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

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;

import com.kodemore.utility.KmString;

/**
 * I simplify the process of composing preference activities.
 * Without the builders, composing PreferenceActivities programmatically
 * is unduly complex and error prone. 
 * 
 * See MyPreferenceActivity for an example.
 */
public abstract class KmPreferenceBuilder<T>
{
    //##################################################
    //# variables
    //##################################################

    private String _key;
    private T      _defaultValue;
    private String _title;

    //##################################################
    //# constructor
    //##################################################

    public KmPreferenceBuilder()
    {
        // none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getKey()
    {
        return _key;
    }

    public void setKey(String e)
    {
        _key = e;
    }

    public T getDefaultValue()
    {
        return _defaultValue;
    }

    public void setDefaultValue(T e)
    {
        _defaultValue = e;
    }

    public void clearDefaultValue()
    {
        setDefaultValue(null);
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String e)
    {
        _title = e;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public abstract T getValue(SharedPreferences pp);

    public abstract void setValue(Editor edit, T value);

    public abstract void installOn(PreferenceScreen scr);

    //##################################################
    //# on change
    //##################################################

    protected OnPreferenceChangeListener newChangeListener()
    {
        return new OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference p, Object value)
            {
                String s;
                s = formatSummaryObjectFor(value);
                s = KmString.create(s).getFirstLine().toString();

                p.setSummary(s);
                return true;
            }
        };
    }

    protected String formatSummaryFor(T value)
    {
        if ( value == null )
            return "<undefined>";

        return value + "";
    }

    @SuppressWarnings("unchecked")
    private final String formatSummaryObjectFor(Object value)
    {
        try
        {
            return formatSummaryFor((T)value);
        }
        catch ( RuntimeException ex )
        {
            return "";
        }
    }

}
