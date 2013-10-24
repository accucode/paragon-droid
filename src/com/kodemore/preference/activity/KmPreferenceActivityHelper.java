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

package com.kodemore.preference.activity;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.kodemore.preference.builder.KmPreferenceBuilder;

public class KmPreferenceActivityHelper
{
    //##################################################
    //# vairables
    //##################################################

    private PreferenceActivity _activity;
    private PreferenceScreen   _screen;

    //##################################################
    //# constructor
    //##################################################

    public KmPreferenceActivityHelper(PreferenceActivity activity, String name)
    {
        PreferenceManager mgr;
        mgr = activity.getPreferenceManager();
        mgr.setSharedPreferencesName(name);
        mgr.setSharedPreferencesMode(Activity.MODE_PRIVATE);

        _activity = activity;
        _screen = mgr.createPreferenceScreen(activity);
    }

    //##################################################
    //# setup
    //##################################################

    public void add(KmPreferenceBuilder<?> e)
    {
        e.installOn(_screen);
    }

    public void finish()
    {
        _activity.setPreferenceScreen(_screen);
    }

}
