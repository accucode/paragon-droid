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
import android.preference.CheckBoxPreference;
import android.preference.PreferenceScreen;

/**
 * See superclass.
 */
public class KmBooleanPreferenceBuilder
    extends KmPreferenceBuilder<Boolean>
{
    //##################################################
    //# vairables
    //##################################################

    private String _trueSummary;
    private String _falseSummary;

    //##################################################
    //# constructor
    //##################################################

    public KmBooleanPreferenceBuilder()
    {
        setTrueSummary("On");
        setFalseSummary("Off");
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Boolean getValue(SharedPreferences pp)
    {
        return pp.getBoolean(getKey(), getDefaultValue());
    }

    @Override
    public void setValue(Editor edit, Boolean value)
    {
        if ( value == null )
            edit.remove(getKey());
        else
            edit.putBoolean(getKey(), value);
    }

    @Override
    public void installOn(PreferenceScreen scr)
    {
        CheckBoxPreference p;
        p = new CheckBoxPreference(scr.getContext());
        p.setKey(getKey());
        p.setDefaultValue(getDefaultValue());
        p.setTitle(getTitle());
        p.setSummaryOn(formatSummaryFor(true));
        p.setSummaryOff(formatSummaryFor(false));

        scr.addPreference(p);
    }

    @Override
    public String formatSummaryFor(Boolean value)
    {
        return value
            ? _trueSummary
            : _falseSummary;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getTrueSummary()
    {
        return _trueSummary;
    }

    public void setTrueSummary(String e)
    {
        _trueSummary = e;
    }

    public String getFalseSummary()
    {
        return _falseSummary;
    }

    public void setFalseSummary(String e)
    {
        _falseSummary = e;
    }

}
