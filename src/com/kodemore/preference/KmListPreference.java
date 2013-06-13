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

package com.kodemore.preference;

import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;

import com.kodemore.collection.KmAssociation;
import com.kodemore.collection.KmList;

public class KmListPreference
    extends KmAbstractPreference<String>
{
    //##################################################
    //# variables
    //##################################################//

    private KmList<KmAssociation<String,String>> _options;

    //##################################################
    //# constructor
    //##################################################//

    public KmListPreference()
    {
        super();
        _options = new KmList<KmAssociation<String,String>>();
    }

    //##################################################
    //# accessing
    //##################################################//

    @Override
    public String getValue(SharedPreferences pp)
    {
        return pp.getString(getKey(), getDefaultValue());
    }

    @Override
    public void setValue(Editor edit, String value)
    {
        if ( value == null )
            edit.remove(getKey());
        else
            edit.putString(getKey(), value);
    }

    @Override
    public void installOn(PreferenceScreen scr)
    {
        SharedPreferences prefs = scr.getSharedPreferences();

        ListPreference p;
        p = new ListPreference(scr.getContext());
        p.setKey(getKey());
        p.setTitle(getTitle());
        p.setSummary(formatSummary(getValue(prefs)));
        p.setEntries(getOptionLabels());
        p.setEntryValues(getOptionValues());
        p.setOnPreferenceChangeListener(newEchoListener());

        scr.addPreference(p);
    }

    @Override
    public String formatSummary(String value)
    {
        for ( KmAssociation<String,String> e : _options )
            if ( e.hasKey(value) )
                return e.getValue();

        return value;
    }

    //##################################################
    //# options
    //##################################################//

    public void addOption(String value)
    {
        String label = value;
        addOption(label, value);
    }

    public void addOptions(List<String> v)
    {
        for ( String e : v )
            addOption(e);
    }

    public void addOption(String value, String label)
    {
        _options.add(new KmAssociation<String,String>(value, label));
    }

    private String[] getOptionLabels()
    {
        int n = _options.size();
        String[] arr = new String[n];

        for ( int i = 0; i < n; i++ )
            arr[i] = _options.get(i).getValue();

        return arr;
    }

    private String[] getOptionValues()
    {
        int n = _options.size();
        String[] arr = new String[n];

        for ( int i = 0; i < n; i++ )
            arr[i] = _options.get(i).getKey();

        return arr;
    }
}
