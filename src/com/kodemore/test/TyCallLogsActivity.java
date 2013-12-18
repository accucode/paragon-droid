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

import android.view.View;

import com.kodemore.callLog.KmCallLog;
import com.kodemore.callLog.KmCallLogManager;
import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTwoLineListView;

/**
 * Retreive the call log information.
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.READ_LOGS" />
 */
public class TyCallLogsActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmCallLogManager  _manager;

    private KmTwoLineListView _list;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _manager = new KmCallLogManager(getContext());

        _list = new KmTwoLineListView(ui());
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Fetch Call Logs", newGetCallsAction());
        root.addViewFullWeight(_list);

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newGetCallsAction()
    {
        return new KmAction()
        {

            @Override
            protected void handle()
            {
                handleGetCalls();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleGetCalls()
    {
        KmList<KmCallLog> calls;
        calls = _manager.getCalls();

        for ( KmCallLog call : calls )
            _list.addItem(call.getNumber(), formatCallDetails(call));
    }

    //##################################################
    //# utility
    //##################################################

    private String formatCallDetails(KmCallLog e)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.printfln("Cached Name: %s", e.getCachedName());
        out.printfln("Cached Number Label: %s", e.getCachedNumberLabel());
        out.printfln("Cached Number Type: %s", e.getCachedNumberType());
        out.printfln("Call Type: %s", e.getCallTypeName());
        out.printfln("Duration: %s", e.getDuration());
        out.printfln("New: %s", e.getNew());
        out.printfln("Number: %s", e.getNumber());
        out.printfln("Timestamp: %s", e.getTimestamp().format_m_d_yyyy_hh_mm_ss());
        return out.toString();
    }
}
