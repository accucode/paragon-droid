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

import com.kodemore.intent.KmIntentUtility;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmWebView;

/** 
 * Display html.
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.INTERNET" />
 */
public class TyHtmlActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmWebView _webView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _webView = new KmWebView(ui());
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Internal reset", newInternalResetAction());
        row.addButton("Internal html", newInternalHtmlAction());
        row.addButton("Internal url", newInternalUrlAction());
        row.addButton("External url", newExternalUrlAction());

        root.addView(_webView);

        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newInternalResetAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleInternalReset();
            }
        };
    }

    private KmAction newInternalHtmlAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleInternalHtml();
            }
        };
    }

    private KmAction newInternalUrlAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleInternalUrl();
            }
        };
    }

    private KmAction newExternalUrlAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleExternalUrl();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleInternalReset()
    {
        _webView.clearHtml();
    }

    private void handleInternalHtml()
    {
        String html = "<html><body>A <i>sample</i> message with styled <b>html</b>.</body></html>";
        _webView.setHtml(html);
    }

    private void handleInternalUrl()
    {
        String url = "http://slashdot.org/";
        _webView.loadUrl(url);
    }

    private void handleExternalUrl()
    {
        String url = "http://slashdot.org/";
        KmIntentUtility.openUrl(getContext(), url);
    }
}
