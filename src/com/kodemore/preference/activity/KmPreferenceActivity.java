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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.kodemore.utility.KmAppInfo;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActions;
import com.kodemore.view.KmDisplay;
import com.kodemore.view.KmMenuHelper;
import com.kodemore.view.KmSoftKeyFlags;
import com.kodemore.view.KmToastAction;

public abstract class KmPreferenceActivity
    extends PreferenceActivity
{
    //##################################################
    //# variables
    //##################################################

    private boolean _isRestart;

    //##################################################
    //# override 
    //##################################################

    @Override
    public final void onCreate(Bundle savedInstanceState)
    {
        try
        {
            onCreateTry(savedInstanceState);
        }
        catch ( RuntimeException ex )
        {
            Kmu.handleUnhandledException(ex);
        }
    }

    protected void onCreateTry(Bundle savedInstanceState)
    {
        if ( savedInstanceState != null )
            _isRestart = true;

        super.onCreate(savedInstanceState);

        installDefaultTitle();
        installPreferenceScreen();
        checkDefaultFocus();
    }

    protected abstract void installPreferenceScreen();

    //##################################################
    //# lifecycle
    //##################################################

    @Override
    protected void onPause()
    {
        super.onPause();

        KmSoftKeyFlags flag = softKeyOnPause();

        switch ( flag )
        {
            case HIDE:
                hideSoftKey();
                break;

            case SHOW:
                showSoftKey();
                break;

            case NORMAL:
                break;
        }

        _isRestart = true;
    }

    /**
     * Used instead of onResumeAsync() due to no User Interface interaction.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        if ( isRestart() )
        {
            preserveSoftKey();
            return;
        }

        KmSoftKeyFlags flags = softKeyOnResume();
        switch ( flags )
        {
            case HIDE:
                hideSoftKey();
                break;

            case SHOW:
                showSoftKey();
                break;

            case NORMAL:
                break;
        }
    }

    //##################################################
    //# menu
    //##################################################

    @Override
    public final boolean onCreateOptionsMenu(Menu m)
    {
        return onCreateOptionsMenu(new KmMenuHelper(m));
    }

    public boolean onCreateOptionsMenu(KmMenuHelper h)
    {
        return false;
    }

    //##################################################
    //# title
    //##################################################

    private void installDefaultTitle()
    {
        String s = getDefaultTitle();
        if ( s != null )
            setTitle(s);
    }

    protected String getDefaultTitle()
    {
        return Kmu.formatActivity(this);
    }

    //##################################################
    //# convenience
    //##################################################

    /**
     * An active is, itself, a context.
     * However, using an explicit method has several advantages.
     * - Clarifies the intent.
     * - Anonomous inner classes don't need to quality "this".
     * - Consistency; this allows code to be more easily moved or refactored.
     */
    protected Context getContext()
    {
        return this;
    }

    protected KmPreferenceActivity getActivity()
    {
        return this;
    }

    protected KmAppInfo getAppInfo()
    {
        return new KmAppInfo(getContext());
    }

    public KmDisplay getDisplay()
    {
        return new KmDisplay(this);
    }

    public int getScreenWidth()
    {
        return getDisplay().getHorizontalPixels();
    }

    public int getScreenHeight()
    {
        return getDisplay().getVerticalPixels();
    }

    public boolean isPortrait()
    {
        return getDisplay().isPortrait();
    }

    public boolean isLandscape()
    {
        return getDisplay().isLandscape();
    }

    protected boolean hasLastNonConfigurationInstance()
    {
        return getLastNonConfigurationInstance() != null;
    }

    protected boolean hasWindow()
    {
        return getWindow() != null;
    }

    protected boolean hasCurrentFocus()
    {
        return getCurrentFocus() != null;
    }

    protected KmPreferenceActivityHelper newHelper(String name)
    {
        return new KmPreferenceActivityHelper(this, name);
    }

    //##################################################
    //# default focus
    //##################################################

    private void checkDefaultFocus()
    {
        if ( isRestart() )
            return;

        if ( hasDefaultField() )
            getDefaultField().requestFocus();
    }

    /**
     * This provides a convenient way to specify which field
     * has default focus.  There are a couple of reasons why 
     * this is useful.  
     * 
     * First, you cannot request focus until
     * after the field has been attached to the activity via
     * setContentView.  This means that you cannot request
     * default focus during the createFields or createLayout
     * methods.
     * 
     * Also, we typically DON'T want to reapply default focus
     * when the application is restarting, e.g.: due to an
     * orientation change.  Rather we want to preserve focus
     * on the current field when we rotate the screen.  This
     * method is only used when entering activity is first
     * started.
     */
    protected View getDefaultField()
    {
        return null;
    }

    private boolean hasDefaultField()
    {
        return getDefaultField() != null;
    }

    //##################################################
    //# alerts
    //##################################################

    protected void alert(String msg)
    {
        Kmu.alert(getContext(), msg);
    }

    protected void alert(String title, CharSequence msg)
    {
        Kmu.alert(getContext(), title, msg);
    }

    //##################################################
    //# toasts
    //##################################################

    protected void toast(CharSequence msg)
    {
        Kmu.toast(msg);
    }

    protected void toast(String msg, Object... args)
    {
        Kmu.toast(msg, args);
    }

    protected KmAction newToastAction(String msg)
    {
        return new KmToastAction(msg);
    }

    //##################################################
    //# first start
    //##################################################

    protected boolean isRestart()
    {
        return _isRestart;
    }

    protected boolean isFirstStart()
    {
        return !isRestart();
    }

    //##################################################
    //# soft-key control
    //##################################################

    protected KmSoftKeyFlags softKeyOnPause()
    {
        return KmSoftKeyFlags.HIDE;
    }

    protected KmSoftKeyFlags softKeyOnResume()
    {
        return KmSoftKeyFlags.HIDE;
    }

    /* Below methods set the soft-key hidden or not two ways. 
     * The first is done to set soft key hidden in onResume(), 
     * the second is to hide/show during onPause() as well as 
     * during actions or other listeners. 
     */

    protected void hideSoftKey()
    {
        if ( hasWindow() )
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if ( hasCurrentFocus() )
        {
            InputMethodManager mgr;
            mgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void showSoftKey()
    {
        if ( hasWindow() )
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if ( hasCurrentFocus() )
        {
            InputMethodManager mgr;
            mgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            mgr.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        }
    }

    private void preserveSoftKey()
    {
        if ( hasWindow() )
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);

        if ( hasCurrentFocus() )
        {
            InputMethodManager mgr;
            mgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            mgr.showSoftInput(getCurrentFocus(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    //##################################################
    //# dialogs
    //##################################################

    protected Builder newDialogBuilder()
    {
        return new AlertDialog.Builder(getContext());
    }

    //##################################################
    //# actions
    //##################################################

    protected KmAction newFinishAction()
    {
        return KmActions.newFinishAction(this);
    }

    protected KmAction newBackAction()
    {
        return KmActions.newBackAction(this);
    }
}
