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

package com.kodemore.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmLoopRunnable;
import com.kodemore.utility.Kmu;

/**
 * A simple service that publishes animals in alphabetical 
 * order to anyone that cares to listen.
 */
public class TyIntegerService
    extends Service
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The background process that iterates through a positive integer
     * values, and notifies any registered listeners when the value changes.
     */
    private KmLoopRunnable                   _runnable;

    /**
     * The list of registered listeners.
     */
    private KmList<TyIntegerServiceListener> _listeners;

    //##################################################
    //# constructor
    //##################################################

    public TyIntegerService()
    {
        // none
    }

    //##################################################
    //# create / destroy
    //##################################################

    @Override
    public void onCreate()
    {
        _runnable = newBackgroundRunnable();
        _runnable.startNewThread();

        _listeners = new KmList<TyIntegerServiceListener>();
    }

    @Override
    public void onDestroy()
    {
        _runnable.cancel();
        _runnable = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return new TyIntegerServiceBinder(this);
    }

    //##################################################
    //# listeners
    //##################################################

    public void addListener(TyIntegerServiceListener e)
    {
        _listeners.add(e);
    }

    public void removeListener(TyIntegerServiceListener e)
    {
        _listeners.remove(e);
    }

    private void fireListeners(int value)
    {
        KmList<TyIntegerServiceListener> v = _listeners;
        for ( TyIntegerServiceListener e : v )
            e.onIntegerChanged(value);
    }

    //##################################################
    //# private
    //##################################################

    private KmLoopRunnable newBackgroundRunnable()
    {
        return new KmLoopRunnable()
        {
            private int _index = 0;

            @Override
            public void runOnce()
            {
                fireListeners(_index++);
                Kmu.sleepSeconds(1);
            }
        };
    }
}
