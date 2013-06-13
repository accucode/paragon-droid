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

package com.kodemore.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract.Contacts;

import com.kodemore.utility.KmLog;
import com.kodemore.view.KmUiManager;

/**
 * I am used to conveniently manage result callbacks.
 */
public abstract class KmIntentCallback<T>
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private int         _requestCode;

    //##################################################
    //# constructor
    //##################################################

    public KmIntentCallback()
    {
        // none
    }

    public void register(KmUiManager ui)
    {
        _ui = ui;
        _ui.register(this);

        _requestCode = _ui.nextId();
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    protected Activity getActivity()
    {
        return ui().getActivity();
    }

    protected Context getContext()
    {
        return ui().getContext();
    }

    //##################################################
    //# accessing
    //##################################################

    public int getRequestCode()
    {
        return _requestCode;
    }

    public boolean hasRequestCode(int i)
    {
        return _requestCode == i;
    }

    //##################################################
    //# run
    //##################################################

    public boolean _run(T param)
    {
        Intent intent = _getRequest(param);
        if ( intent == null )
            return false;

        getActivity().startActivityForResult(intent, getRequestCode());
        return true;
    }

    public abstract Intent _getRequest(T param);

    //##################################################
    //# handle
    //##################################################

    public final void handle(int resultCode, Intent data)
    {
        switch ( resultCode )
        {
            case Activity.RESULT_OK:
                handleOk(data);
                break;

            case Activity.RESULT_CANCELED:
                handleCancel(data);
                break;

            default:
                handleOther(resultCode, data);
                break;
        }
    }

    public abstract void handleOk(Intent data);

    public void handleCancel(Intent data)
    {
        // ignored.
    }

    public void handleOther(int resultCode, Intent data)
    {
        KmLog.warn(getClass(), "Unhandled result code: " + resultCode);
    }

    //##################################################
    //# common (intents)
    //##################################################

    protected Intent newIntentOn(Class<? extends Activity> c)
    {
        return new Intent(getContext(), c);
    }

    protected Intent getPickContactIntent()
    {
        return new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
    }
}
