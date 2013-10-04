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

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.kodemore.utility.KmTimer;
import com.kodemore.utility.Kmu;

public abstract class KmAction
    implements OnClickListener, OnMenuItemClickListener, OnPreferenceChangeListener, TextWatcher,
    OnItemSelectedListener, OnItemClickListener, DialogInterface.OnClickListener,
    OnCheckedChangeListener, OnSeekBarChangeListener, OnEditorActionListener, OnShowListener,
    OnDismissListener, OnTouchListener
{
    //##################################################
    //# constants
    //##################################################

    /**
     * NO OPeration.  Does nothing.
     * 
     * Useful when a dummy action is needed, but doesn't actually
     * need to do anything.  E.g.: when adding a dummy button
     * to a simple dialog.
     */
    public static final KmAction NOOP = createNoop();

    private static KmAction createNoop()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                // none
            }
        };
    }

    //##################################################
    //# constructor
    //##################################################

    public KmAction()
    {
        // none
    }

    //##################################################
    //# interfaces
    //##################################################

    @Override
    public void onClick(View v)
    {
        fire();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        fire();
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object e)
    {
        fire();
        return true;
    }

    //##################################################
    //# text watcher
    //##################################################

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        fire();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        // ignored
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        // ignored
    }

    //##################################################
    //# OnItemSelectedListener 
    //##################################################

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int index, long id)
    {
        fire();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        fire();
    }

    //##################################################
    //# onItemClickListener
    //##################################################//

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id)
    {
        fire();
    }

    //##################################################
    //# DialogInterface.OnClickListener
    //##################################################

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        fire();
    }

    //##################################################
    //# OnCheckChangeListener
    //##################################################

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        fire();
    }

    //##################################################
    //# seek bar change listener
    //##################################################

    @Override
    public void onProgressChanged(SeekBar e, int progress, boolean fromUser)
    {
        fire();
    }

    @Override
    public void onStartTrackingTouch(SeekBar e)
    {
        // ignored
    }

    @Override
    public void onStopTrackingTouch(SeekBar e)
    {
        // ignored
    }

    //##################################################
    //# editor action listener 
    //##################################################

    /**
     * This will fire only on key down.  Allowing it to fire all actions
     *  causes unintended firing of action.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if ( event == null )
        {
            fire();
            return true;
        }

        if ( event.getAction() == KeyEvent.ACTION_DOWN )
            fire();

        return true;
    }

    //##################################################
    //# DialogInterface.OnShowListener
    //##################################################

    @Override
    public void onShow(DialogInterface e)
    {
        fire();
    }

    //##################################################
    //# DialogInterface.OnDismissListener
    //##################################################

    @Override
    public void onDismiss(DialogInterface e)
    {
        fire();
    }

    //##################################################
    //# OnTouchListener
    //##################################################

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        fire();
        return true;
    }

    //##################################################
    //# support
    //##################################################

    public void fire()
    {
        if ( catchesExceptions() )
            _fireTryCatch();
        else
            _fireTimed();
    }

    private void _fireTryCatch()
    {
        try
        {
            _fireTimed();
        }
        catch ( Exception ex )
        {
            Kmu.handleUnhandledException(ex);
            Kmu.toast(Kmu.formatMessage(ex));
        }
    }

    private void _fireTimed()
    {
        KmTimer t = KmTimer.run(KmAction.class, "fire");
        handle();
        t.check();
    }

    /**
     * By default, actions usually attempt to catch exceptions and handle
     * them politely.  Subclasses can override this method to disable this
     * behavior.
     */
    protected boolean catchesExceptions()
    {
        return true;
    }

    protected abstract void handle();

}
