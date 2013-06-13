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

package com.kodemore.test;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmStringListView;

/**
 * Display a list of currently running apps.
 */
public class TyRunningAppsActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmStringListView _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _listView = new KmStringListView(ui());
        _listView.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addViewFullWeight(_listView);
        root.addButton("Refresh", newRefreshAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newRefreshAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRefresh();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRefresh()
    {
        _listView.setItems(getRunningAppNames());
    }

    //##################################################
    //# utility
    //##################################################

    private KmList<String> getRunningAppNames()
    {
        KmList<String> v = new KmList<String>();

        ActivityManager mgr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = mgr.getRunningAppProcesses();

        for ( RunningAppProcessInfo info : infos )
            v.add(info.processName);

        v.sort();
        return v;
    }
}
