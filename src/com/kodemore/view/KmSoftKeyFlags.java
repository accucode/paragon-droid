package com.kodemore.view;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public enum KmSoftKeyFlags
{
    //##################################################
    //# values
    //##################################################

    HIDE,
    SHOW,
    NORMAL;

    //##################################################
    //# apply
    //##################################################

    public void applyTo(Activity activity)
    {
        KmSoftKeyFlags e = this;
        switch ( e )
        {
            case SHOW:
                showOn(activity);
                break;

            case HIDE:
                hideOn(activity);
                break;

            case NORMAL:
                preserveOn(activity);
                break;
        }
    }

    /** 
     * The methods below update the soft-key two ways. 
     * The first is done to set soft key hidden in onResume(), 
     * the second is to hide/show during onPause() as well as 
     * during actions or other listeners. 
     */
    private void showOn(Activity a)
    {
        setWindowInputMode(a, WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        showSoftInput(a, InputMethodManager.SHOW_FORCED);
    }

    private void hideOn(Activity a)
    {
        setWindowInputMode(a, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hideSoftInput(a);
    }

    private void preserveOn(Activity a)
    {
        setWindowInputMode(a, WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);

        /**
         * This looks incorrect, however the "showSoftInput" method ultimately
         * calls InputManager.showSoftInput(____) which by default is a way to set
         * flags for the soft key. It is correct.
         */
        showSoftInput(a, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //##################################################
    //# utility
    //##################################################

    private void setWindowInputMode(Activity a, int mode)
    {
        Window window = a.getWindow();
        if ( window != null )
            window.setSoftInputMode(mode);
    }

    private void showSoftInput(Activity a, int flags)
    {
        View view = a.getCurrentFocus();
        if ( view != null )
            getInputManagerFor(a).showSoftInput(view, flags);
    }

    private void hideSoftInput(Activity a)
    {
        View view = a.getCurrentFocus();
        if ( view != null )
            getInputManagerFor(a).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private InputMethodManager getInputManagerFor(Activity a)
    {
        return (InputMethodManager)a.getSystemService(a.INPUT_METHOD_SERVICE);
    }

}
