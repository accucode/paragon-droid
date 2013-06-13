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

import com.kodemore.utility.KmTimer;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;

/**
 * Test an activity that requires a lot of processing time
 * to refresh.  A few points to note...
 * 
 * The work is done during onResume rather than onCreate.
 * 
 * We override onResumeAsync (rather than onResume).  This runs
 * in a background thread rather than the application ui thread.
 * Any changes to the view must ultimately be run via runOnUiThread.
 */
public class TySlowLoadActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextView _onResumeLabel;

    //##################################################
    //# create
    //##################################################

    /**
     * Perform non-conditional initializations.
     * This is where we generally set up all the ivars.
     */
    @Override
    protected void init()
    {
        _onResumeLabel = ui().newLabel();
    }

    //##################################################
    //# layout
    //##################################################

    /**
     * This is where we create the view hierarchy, and support
     * conditional logic such as different layouts for portrait
     * and landscape.
     */
    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = new KmColumnLayout(ui());

        KmRowLayout row;
        row = root.addRow();
        row.addLabel("onResume...");
        row.addSpace();
        row.addView(_onResumeLabel);

        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################

    /**
     * Although onResume is supported normally, we will likely
     * use onResumeAsync (below) instead for most situations.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    /**
     * This method is automatically called in a background process
     * via an asycTask at the end of super.onResume().  Activities
     * should call super.
     * 
     * Any updates to the ui must ultimately be routed through the
     * activity.runOnUiThread() method.  We typically use convenience
     * methods that hide the hassle of doing this.  For example, see
     * setValueAsync() below.
     * 
     * If there are multiple lengthy steps, you can check to see if the
     * activity has been paused again via isPaused().  If the activity
     * has been paused, then you can safely exit early, since onResume
     * will be called again if needed. 
     */
    @Override
    protected void onResumeAsync()
    {
        super.onResumeAsync();

        _onResumeLabel.clearValueAsync();

        doSlowWork(1);
        _onResumeLabel.setValueAsyncSafe("1...");
        if ( isPaused() )
            return;

        doSlowWork(2);
        _onResumeLabel.setValueAsyncSafe("2...");
        if ( isPaused() )
            return;

        doSlowWork(3);
        _onResumeLabel.setValueAsyncSafe("3...");
        if ( isPaused() )
            return;

        doSlowWork(4);
        _onResumeLabel.setValueAsyncSafe("4...");
        if ( isPaused() )
            return;

        doSlowWork(5);
        _onResumeLabel.setValueAsyncSafe("5...");
        if ( isPaused() )
            return;

        doSlowWork(6);
        _onResumeLabel.setValueAsyncSafe("done.");
    }

    //##################################################
    //# utility
    //##################################################

    /**
     * Just a bogus example of doing some work that takes a long time.
     */
    private void doSlowWork(int id)
    {
        KmTimer t = KmTimer.run("doSlowWork-" + id);

        double pi = Math.PI;
        double d = pi;

        int n = 1000000;
        for ( int i = 0; i < n; i++ )
            d = Math.sqrt(Math.pow(d, d));

        t.print();
    }
}
