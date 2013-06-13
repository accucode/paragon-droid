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

package com.kodemore.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextSwitcher;

import com.kodemore.utility.KmRunnable;

public class KmTextSwitcher
    extends TextSwitcher
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private Boolean     _autoSave;

    //##################################################
    //# constructor
    //##################################################

    public KmTextSwitcher(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
        _autoSave = false;

        setFactory(newViewFactory());
        setSimpleFadeAnimation(500);
    }

    //##################################################
    //# core
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    public void setAutoSave()
    {
        _autoSave = true;
        setId();
        setSaveEnabled(true);
    }

    public KmTextSwitcherHelper getHelper()
    {
        return new KmTextSwitcherHelper(this);
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# view factory
    //##################################################

    private ViewFactory newViewFactory()
    {
        return new ViewFactory()
        {
            @Override
            public View makeView()
            {
                KmTextView v = ui().newTextView();
                v.setAutoSave();

                return v;
            }
        };
    }

    //##################################################
    //# accessing
    //##################################################

    public CharSequence getText()
    {
        View child = getChildAt(getDisplayedChild());

        /* note that we probably need the following line
         * because someone could call myTextSwitcher.addView(View a);
         */
        if ( child instanceof KmTextView )
            return ((KmTextView)child).getText();

        return null;
    }

    public CharSequence getText(int index)
    {
        View child = getChildAt(index);

        if ( child == null )
            return null;

        if ( child instanceof KmTextView )
            return ((KmTextView)child).getText();

        return null;
    }

    public void setTextFast(CharSequence s)
    {
        setCurrentText(s);
    }

    //##################################################
    //# animation
    //##################################################

    public void setSimpleFadeAnimation(int totalMs)
    {
        int ms = totalMs / 2;

        setOutAnimation(KmAnimations.fadeOut(ms));
        setInAnimation(KmAnimations.fadeIn(ms, ms));
    }

    //##################################################
    //# state saving
    //##################################################

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Parcelable superState = super.onSaveInstanceState();

        Bundle bundle = new Bundle();
        bundle.putBoolean("autoSave", _autoSave);
        bundle.putInt("childIndex", getDisplayedChild());
        bundle.putParcelable("superState", superState);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if ( state instanceof Bundle )
        {
            Bundle bundle = (Bundle)state;
            _autoSave = bundle.getBoolean("autoSave");

            if ( _autoSave == true )
            {
                int index = bundle.getInt("childIndex");
                setDisplayedChild(index);
            }

            super.onRestoreInstanceState(bundle.getParcelable("superState"));

            return;
        }

        super.onRestoreInstanceState(state);
    }

    //##################################################
    //# async
    //##################################################

    public void setTextAsyncSafe(final CharSequence e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setText(e);
            }
        }.runOnUiThread(ui());
    }

}
