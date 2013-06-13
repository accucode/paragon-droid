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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * I provide a sample of how an application might 
 * access complex preferences that are backed by a 
 * PreferenceManager.
 * 
 * This seems overly cumbersome.
 * Needs another pass at some point.
 */
public class TyComplexPreferences
{
    //##################################################
    //# variables
    //##################################################

    private Context           _context;
    private SharedPreferences _inner;
    private Editor            _editor;

    //##################################################
    //# constructor
    //##################################################

    public TyComplexPreferences(Context e)
    {
        _context = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public Boolean getEnabled()
    {
        return getManager().getEnabled().getValue(getInner());
    }

    public void setEnabled(Boolean e)
    {
        getManager().getEnabled().setValue(getEditor(), e);
    }

    public String getColor()
    {
        return getManager().getColor().getValue(getInner());
    }

    public void setColor(String e)
    {
        getManager().getColor().setValue(getEditor(), e);
    }

    public String getEmail()
    {
        return getManager().getEmail().getValue(getInner());
    }

    public void setEmail(String e)
    {
        getManager().getEmail().setValue(getEditor(), e);
    }

    public Integer getSize()
    {
        return getManager().getSize().getValue(getInner());
    }

    public void setSize(Integer e)
    {
        getManager().getSize().setValue(getEditor(), e);
    }

    //##################################################
    //# inner
    //##################################################

    public SharedPreferences getInner()
    {
        if ( _inner == null )
            _inner = _context.getSharedPreferences(getInnerName(), getInnerMode());

        return _inner;
    }

    public String getInnerName()
    {
        return "preferences";
    }

    private int getInnerMode()
    {
        return Context.MODE_PRIVATE;
    }

    private Editor getEditor()
    {
        if ( _editor == null )
            _editor = getInner().edit();

        return _editor;
    }

    public void commit()
    {
        getEditor().commit();
        rollback();
    }

    public void rollback()
    {
        _inner = null;
        _editor = null;
    }

    //##################################################
    //# support
    //##################################################

    private TyPreferenceManager getManager()
    {
        return TyPreferenceManager.getInstance();
    }
}
