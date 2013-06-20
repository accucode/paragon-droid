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

import android.net.Uri;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.kodemore.file.KmAssetFilePath;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmWebView;

/**
 * I provide a simple model for testing.
 */
public class TyUriActivity
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
        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.setWebChromeClient(new WebChromeClient());
        _webView.setWebViewClient(new WebViewClient());
        _webView.setAutoSave();
        _webView.loadUrl(getUri());
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addViewFullWeight(_webView);

        KmRowLayout row;
        row = root.addEvenRow();
        row.addBackButton();

        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private Uri getUri()
    {
        return getStuffs();
    }

    private Uri getStuffs()
    {
        String topic = "stub";
        return getFilePathUri(topic);
    }

    private Uri getFilePathUri(String resource)
    {
        String name = resource + ".html";
        KmAssetFilePath folder = new KmAssetFilePath("help");

        KmAssetFilePath file = (KmAssetFilePath)folder.getChild(name);

        return file.toUri();
    }
}
