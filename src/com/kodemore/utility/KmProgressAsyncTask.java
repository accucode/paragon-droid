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

import android.os.AsyncTask;

import com.kodemore.view.KmAction;

public abstract class KmProgressAsyncTask
    extends AsyncTask<Void,Void,Void>
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The anticipated maximum progress.  We expect that
     * we will be done once progress >= max.  Although I
     * typically don't need the max for myself, allowing
     * clients to record it here is often very convenient.
     */
    private int      _max;

    /**
     * The current progress.
     */
    private int      _progress;

    /**
     * The optional action to run when I publish my progress.
     */
    private KmAction _onProgressAction;

    /**
     * The optional action to run when I am done.
     */
    private KmAction _onPostExecuteAction;

    //##################################################
    //# max
    //##################################################

    public int getMax()
    {
        return _max;
    }

    public void setMax(int e)
    {
        _max = e;
    }

    //##################################################
    //# progress
    //##################################################

    public int getProgress()
    {
        return _progress;
    }

    public void setProgress(int e)
    {
        _progress = e;
    }

    public void addProgress(int e)
    {
        _progress += e;
    }

    public void incrementProgress()
    {
        _progress++;
    }

    public void publishProgress()
    {
        super.publishProgress();
    }

    //##################################################
    //# on progress
    //##################################################

    /**
     * This is our hook to update the ui.  I ignore the
     * progress parameter, and simply fire the onProgress
     * action if available.  The ui client action can get
     * the current status via getProgress().
     */
    @Override
    protected final void onProgressUpdate(Void... ignored)
    {
        if ( hasOnProgressAction() )
            getOnProgressAction().fire();
    }

    public KmAction getOnProgressAction()
    {
        return _onProgressAction;
    }

    public void setOnProgressAction(KmAction e)
    {
        _onProgressAction = e;
    }

    public boolean hasOnProgressAction()
    {
        return getOnProgressAction() != null;
    }

    //##################################################
    //# on post execute
    //##################################################

    @Override
    protected void onPostExecute(Void ignored)
    {
        if ( hasOnPostExecuteAction() )
            getOnPostExecuteAction().fire();
    }

    public KmAction getOnPostExecuteAction()
    {
        return _onPostExecuteAction;
    }

    public void setOnPostExecuteAction(KmAction e)
    {
        _onPostExecuteAction = e;
    }

    public boolean hasOnPostExecuteAction()
    {
        return getOnPostExecuteAction() != null;
    }

    //##################################################
    //# background
    //##################################################

    @Override
    protected final Void doInBackground(Void... ignored)
    {
        handleBackground();
        return null;
    }

    protected abstract void handleBackground();

    //##################################################
    //# threading
    //##################################################

    /**
     * Remove any associations that are invalid when the ui
     * client stops.  Client activities should generally call
     * this in their onPause method. 
     */
    public void detach()
    {
        _onProgressAction = null;
        _onPostExecuteAction = null;
    }
}
