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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextSwitcher;

/**
 * Some some basic service interactions.
 */
public class TyIntegerServiceActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextSwitcher             _text;

    private TyIntegerServiceListener   _serviceListener;
    private TyIntegerServiceConnection _serviceConnection;

    //##################################################
    //# create
    //##################################################

    @Override
    protected void init()
    {
        _text = new KmTextSwitcher(ui());
        _text.setTextFast("<none>");
        _text.getHelper().setBold();
        _text.getHelper().setFontSize(36);

        _serviceListener = newListener();
        _serviceConnection = new TyIntegerServiceConnection();
    }

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Start", newStartServiceAction());
        row.addButton("Stop", newStopServiceAction());
        row.addButton("Check", newTestServiceAction());

        row = root.addEvenRow();
        row.addButton("Bind", newBindServiceAction());
        row.addButton("Unbind", newUnbindServiceAction());

        row = root.addEvenRow();
        row.addButton("Add Listener", newAddListenerAction());
        row.addButton("Remove Listener", newRemoveListenerAction());

        root.addFiller();
        root.addView(_text);

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newStartServiceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleStartService();
            }
        };
    }

    private KmAction newStopServiceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleStopService();
            }
        };
    }

    private KmAction newTestServiceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTestService();
            }
        };
    }

    private KmAction newBindServiceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleBindService();
            }
        };
    }

    private KmAction newUnbindServiceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleUnbindService();
            }
        };
    }

    private KmAction newAddListenerAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAddListener();
            }
        };
    }

    private KmAction newRemoveListenerAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRemoveListener();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleStartService()
    {
        Intent i = new Intent(getContext(), TyIntegerService.class);
        ComponentName name = startService(i);

        if ( name == null )
            alert("started service, (null)");
        else
            alert("started service, " + name.getShortClassName());
    }

    private void handleStopService()
    {
        Intent i = new Intent(getContext(), TyIntegerService.class);
        boolean stopped = stopService(i);
        alert("stopped service, " + stopped);
    }

    private void handleTestService()
    {
        boolean running = Kmu.isServiceRunning(TyIntegerService.class);
        alert("is running = " + running);
    }

    private void handleBindService()
    {
        Intent i = new Intent(getContext(), TyIntegerService.class);
        boolean ok = bindService(i, _serviceConnection, Context.BIND_AUTO_CREATE);
        alert("bound service, " + ok);
    }

    private void handleUnbindService()
    {
        unbindService(_serviceConnection);
        alert("unbound service");
    }

    private void handleAddListener()
    {
        TyIntegerService svc;
        svc = _serviceConnection.getBinder().getService();
        svc.addListener(_serviceListener);
    }

    private void handleRemoveListener()
    {
        TyIntegerService svc;
        svc = _serviceConnection.getBinder().getService();
        svc.removeListener(_serviceListener);
    }

    //##################################################
    //# support
    //##################################################

    private TyIntegerServiceListener newListener()
    {
        return new TyIntegerServiceListener()
        {
            @Override
            public void onIntegerChanged(final int value)
            {
                _text.setTextAsyncSafe(value + "");
            }
        };
    }

}
