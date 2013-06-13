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
import android.webkit.WebView;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;

/**
 * Display html.
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.INTERNET" />
 */
public class TyWebActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private WebView _webView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _webView = new WebView(getContext());
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
        row = root.addRow();
        row.addButton("Html", newHtmlAction());
        row.addButton("Url", newUrlAction());

        root.addView(_webView);

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newHtmlAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleHtml();
            }
        };
    }

    private KmAction newUrlAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleUrl();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleHtml()
    {
        String html = "<html><body>A <i>sample</i> message with styled <b>html</b>.</body></html>";
        _webView.loadData(html, "text/html", null);
    }

    private void handleUrl()
    {
        _webView.loadUrl("http://slashdot.org/");
    }

}
