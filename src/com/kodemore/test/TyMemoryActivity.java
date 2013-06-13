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

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.view.View;

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmTwoLineListView;

/**
 * Display various memory attributes.
 */
public class TyMemoryActivity
    extends KmActivity
{
    //##################################################
    //# create
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        Runtime rt;
        rt = Runtime.getRuntime();
        rt.gc();

        long rtFree = rt.freeMemory();
        long rtMax = rt.maxMemory();
        long rtTotal = rt.totalMemory();
        long rtUsed = rtTotal - rtFree;

        MemoryInfo info;
        info = getMemoryInfo();

        long mmAvail = info.availMem;
        long mmThreshold = info.threshold;
        boolean mmIsLow = info.lowMemory;

        KmTwoLineListView root;
        root = new KmTwoLineListView(ui());
        root.addItem("Runtime, Free", formatMemory(rtFree));
        root.addItem("Runtime, Max", formatMemory(rtMax));
        root.addItem("Runtime, Total", formatMemory(rtTotal));
        root.addItem("Runtime, Used", formatMemory(rtUsed));
        root.addItem("Manager, Available", formatMemory(mmAvail));
        root.addItem("Manager, Threshold", formatMemory(mmThreshold));
        root.addItem("Manager, Low", Kmu.formatYesNo(mmIsLow));
        return root;
    }

    //##################################################
    //# support
    //##################################################

    private String formatMemory(long bytes)
    {
        // bytes per megabyte
        double bpm = (long)Math.pow(2, 20);
        double megs = bytes / bpm;

        return Kmu.formatDouble(megs, 1);
    }

    private MemoryInfo getMemoryInfo()
    {
        MemoryInfo info;
        info = new MemoryInfo();

        ActivityManager mgr;
        mgr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        mgr.getMemoryInfo(info);
        return info;
    }
}
