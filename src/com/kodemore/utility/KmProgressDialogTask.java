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

package com.kodemore.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class KmProgressDialogTask
    implements KmTaskIF
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The inner task.  We wrap this as a delegate rather
     * than subclassing in order to simplify the client 
     * interface and avoid confusion.
     */
    private InnerTask      _inner;

    /**
     * The re-attachable context that is monitoring this task.
     */
    private Context        _context;

    /**
     * The internally managed progress dialog.
     */
    private ProgressDialog _dialog;

    /**
     * The progress dialog's style, this should be one of the following:
     * ProgressDialog.STYLE_HORIZONTAL
     * ProgressDialog.STYLE_SPINNER
     */
    private Integer        _dialogStyle;

    /**
     * The anticipated maximum progress.  
     * Null is used for an indeterminate limit.
     */
    private Integer        _max;

    /**
     * The current progress.
     * Null indicates indeterminate progress.
     */
    private Integer        _progress;

    /**
     * The dialog's title.  We can set this during onBackground,
     * then use it during onProgress to update the ui.
     * 
     * Note: If you don't set an initial title before the dialog
     * is displayed, then you won't be able to update it later.
     * This seems to be a limitation of the ProgressDialog interface.
     */
    private String         _title;

    /**
     * The dialog's message.  We can set this during onBackground,
     * then use it during onProgress to update the ui.
     * 
     * Note: If you don't set an initial message before the dialog
     * is displayed, then you won't be able to update it later.
     * This seems to be a limitation of the ProgressDialog interface.
     */
    private String         _message;

    /**
     * Keep track of the time spend running the task.
     */
    private KmTimer        _timer;

    //##################################################
    //# context
    //##################################################

    protected Context getContext()
    {
        return _context;
    }

    private void setContext(Context e)
    {
        _context = e;
    }

    public boolean hasContext()
    {
        return _context != null;
    }

    /**
     * I provide convenient access to the global application context. 
     */
    protected Context getApplicationContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

    //##################################################
    //# dialog
    //##################################################

    private ProgressDialog getDialog()
    {
        return _dialog;
    }

    private boolean hasDialog()
    {
        return _dialog != null;
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String msg, Object... args)
    {
        _title = Kmu.format(msg, args);
    }

    public String getMessage()
    {
        return _message;
    }

    @Override
    public void setMessage(String msg, Object... args)
    {
        _message = Kmu.format(msg, args);
    }

    public void setDialogStyleHorizontal()
    {
        _dialogStyle = ProgressDialog.STYLE_HORIZONTAL;
    }

    public void setDialogStyleSpinner()
    {
        _dialogStyle = ProgressDialog.STYLE_SPINNER;
    }

    private int getDialogStyle()
    {
        return _dialogStyle == null
            ? ProgressDialog.STYLE_HORIZONTAL
            : _dialogStyle;
    }

    protected void installDialog()
    {
        _dialog = new ProgressDialog(getContext());
        _dialog.setProgressStyle(getDialogStyle());
        _dialog.setCancelable(false);
        _dialog.setTitle(getTitle());
        _dialog.setMessage(getMessage());
    }

    //##################################################
    //# attach / detach
    //##################################################

    public void onResume(Context e)
    {
        setContext(e);
        installDialog();

        if ( isRunning() )
            handleProgress();
    }

    public void onPause()
    {
        _context = null;

        if ( _dialog != null )
            _dialog.dismiss();

        _dialog = null;
    }

    public boolean isAttached()
    {
        return hasContext() && hasDialog();
    }

    public boolean isDetached()
    {
        return !isAttached();
    }

    //##################################################
    //# run
    //##################################################

    public void run()
    {
        if ( isRunning() )
        {
            handleProgress();
            return;
        }

        start();
    }

    private void start()
    {
        _max = null;
        _progress = null;

        _timer = KmTimer.run();

        _inner = new InnerTask();
        _inner.execute();
    }

    public boolean isRunning()
    {
        if ( _inner == null )
            return false;

        return _inner.getStatus() == AsyncTask.Status.RUNNING;
    }

    public boolean isNotRunning()
    {
        return !isRunning();
    }

    //##################################################
    //# max
    //##################################################

    public Integer getMax()
    {
        return _max;
    }

    @Override
    public void setMax(Integer e)
    {
        _max = e;
    }

    //##################################################
    //# progress
    //##################################################

    protected Integer getProgress()
    {
        return _progress;
    }

    @Override
    public void publishProgress(Integer i)
    {
        _progress = i;
        _inner.publish();
    }

    //##################################################
    //# handle
    //##################################################

    /**
     * Subclass must override this.  Typical implementations
     * generally follow the pattern of...
     * 
     *      setDialogTitle(...);
     *      setDialogMessage(...);
     *      publishProgress();
     *
     *      setMax(...);
     *      publishProgress();
     *      
     *      while ( true )
     *      {
     *          // do some work
     *          publishProgress(i);
     *      }
     */
    @Override
    public abstract void handleBackground();

    /**
     * Update the dialog, its messages and progress bar.
     */
    private void handleProgress()
    {
        if ( isDetached() )
            return;

        Integer max = getMax();
        Integer progress = getProgress();

        _dialog.setTitle(getTitle());
        _dialog.setMessage(getMessage());
        _dialog.show();

        if ( max == null || progress == null )
        {
            _dialog.setIndeterminate(true);
            return;
        }

        _dialog.setIndeterminate(false);
        _dialog.setMax(max);
        _dialog.setProgress(progress);
    }

    /**
     * Hook for subclass to perform ui work after all background
     * processing is complete.
     */
    protected void handleDone()
    {
        // subclass
    }

    //##################################################
    //# local task
    //##################################################

    private class InnerTask
        extends AsyncTask<Void,Void,Void>
    {
        protected void publish()
        {
            publishProgress();
        }

        @Override
        protected Void doInBackground(Void... ignored)
        {
            handleBackground();
            delayAtEnd();
            return null;
        }

        private void delayAtEnd()
        {
            int minMs = 1000;
            int runMs = (int)_timer.getMilliseconds();
            int remainingMs = minMs - runMs;

            Kmu.sleepMs(remainingMs);
        }

        @Override
        protected final void onProgressUpdate(Void... ignored)
        {
            handleProgress();
        }

        @Override
        protected void onPostExecute(Void ignored)
        {
            if ( hasDialog() )
                getDialog().dismiss();

            _inner = null;

            handleDone();
        }
    }
}
