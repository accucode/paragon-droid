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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * This class is not safe yet.
 * This pattern does not account for activity lifecycle event.
 * For example: pause/resume, stop/start.
 */
public abstract class KmBackgroundAction
    extends KmAction
{
    //##################################################
    //# variables
    //##################################################

    private Context _context;
    private String  _title;

    //##################################################
    //# constructor
    //##################################################

    public KmBackgroundAction(Context context, String title)
    {
        super();

        _context = context;
        _title = title;
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected void handle()
    {
        newTask().execute();
    }

    protected abstract void handleBackground();

    //##################################################
    //# support
    //##################################################

    private AsyncTask<Void,Void,Void> newTask()
    {
        return new AsyncTask<Void,Void,Void>()
        {
            private ProgressDialog _dialog;

            @Override
            protected void onPreExecute()
            {
                String msg = "Please wait...";
                _dialog = ProgressDialog.show(_context, _title, msg);
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                handleBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                if ( _dialog != null )
                    _dialog.dismiss();
            }
        };
    }

}
