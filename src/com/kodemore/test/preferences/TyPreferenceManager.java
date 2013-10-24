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

package com.kodemore.test.preferences;

import com.kodemore.preference.builder.KmBooleanPreferenceBuilder;
import com.kodemore.preference.builder.KmIntegerPreferenceBuilder;
import com.kodemore.preference.builder.KmListPreferenceBuilder;
import com.kodemore.preference.builder.KmStringPreferenceBuilder;

public class TyPreferenceManager
{
    //##################################################
    //# instance
    //##################################################

    public static final TyPreferenceManager _instance = new TyPreferenceManager();

    public static TyPreferenceManager getInstance()
    {
        return _instance;
    }

    //##################################################
    //# variables
    //##################################################

    private KmBooleanPreferenceBuilder _enabled;
    private KmListPreferenceBuilder    _color;
    private KmStringPreferenceBuilder  _email;
    private KmIntegerPreferenceBuilder _size;

    //##################################################
    //# constructor
    //##################################################

    public TyPreferenceManager()
    {
        _enabled = newEnabled();
        _color = newColor();
        _email = newEmail();
        _size = newSize();
    }

    private KmBooleanPreferenceBuilder newEnabled()
    {
        KmBooleanPreferenceBuilder e;
        e = new KmBooleanPreferenceBuilder();
        e.setKey("enabled");
        e.setTitle("Enabled");
        e.setDefaultValue(true);
        return e;
    }

    private KmListPreferenceBuilder newColor()
    {
        KmListPreferenceBuilder e;
        e = new KmListPreferenceBuilder();
        e.setKey("color");
        e.setTitle("Color");
        e.setDefaultValue("white");

        e.addOption("white");
        e.addOption("black");
        e.addOption("red");
        e.addOption("blue");
        e.addOption("green");

        return e;
    }

    private KmStringPreferenceBuilder newEmail()
    {
        KmStringPreferenceBuilder e;
        e = new KmStringPreferenceBuilder();
        e.setKey("email");
        e.setTitle("Email");
        return e;
    }

    private KmIntegerPreferenceBuilder newSize()
    {
        KmIntegerPreferenceBuilder e;
        e = new KmIntegerPreferenceBuilder();
        e.setKey("size");
        e.setTitle("Size");
        return e;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmBooleanPreferenceBuilder getEnabled()
    {
        return _enabled;
    }

    public KmListPreferenceBuilder getColor()
    {
        return _color;
    }

    public KmStringPreferenceBuilder getEmail()
    {
        return _email;
    }

    public KmIntegerPreferenceBuilder getSize()
    {
        return _size;
    }

}
