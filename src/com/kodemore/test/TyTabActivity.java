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

import com.kodemore.view.KmTab;
import com.kodemore.view.KmTabActivity;
import com.kodemore.view.KmTabHost;
import com.kodemore.view.RR;

/**
 * Demonstrate the a tabbed activity using Km tooling.
 * 
 * Note the alternate super class.
 */
public class TyTabActivity
    extends KmTabActivity
{
    //##################################################
    //# layout
    //##################################################

    @Override
    protected void installTabsOn(KmTabHost host)
    {
        // Just text
        KmTab tab;
        tab = host.addTab();
        tab.setIndicator("Hello 22");
        tab.setContent(TyHelloActivity.class);
        tab.setup();

        // Just a large icon
        tab = host.addTab();
        tab.setIndicator(RR.drawable.assignIcon);
        tab.setContent(TyMenuActivity.class);
        tab.setup();

        // Both text and a small icon
        tab = host.addTab();
        tab.setIndicator("View", RR.drawable.viewIcon);
        tab.setContent("content for tab three");
        tab.setup();
    }

    //##################################################
    //# override
    //##################################################

    /**
     * this method gives us a handle to do stuff 
     * each time the tab is changed from one activity
     * to another.
     * THIS IS NOT REQUIRED TO MAKE A TABBED ACTIVITY WORK!
     */
    @Override
    protected void handleOnTabChanged()
    {
        super.handleOnTabChanged();

        //do stuff...
    }
}
