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

import com.kodemore.html.KmHtmlBuilder;
import com.kodemore.utility.KmLoremIpsum;
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
public class TyHtmlZoomActivity
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
        _webView.setHtml(formatHtml());

        /*
         * This includes support for pinch controls, although
         * they can be a bit flakey.
         */
        _webView.getSettings().setBuiltInZoomControls(true);
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
        row.addButton("In", newInAction());
        row.addButton("Out", newOutAction());
        row.addButton("Reset", newResetAction());

        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newInAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleIn();
            }
        };
    }

    private KmAction newOutAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleOut();
            }
        };
    }

    private KmAction newResetAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleReset();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleIn()
    {
        _webView.zoomIn();
    }

    private void handleOut()
    {
        _webView.zoomOut();
    }

    private void handleReset()
    {
        /*
         * ugh.
         * There doesn't seem to be any way to re/set the zoom on demand.
         * 
         * E.g.: this only works before the view is initially displayed.
         *      _webView.setInitialScale(100);
         *      
         * The following does work, but is pretty heavy handed.  Although
         * not elegant, this could also be used to programmatically set
         * the zoom/scale to any desired value upon demand.
         */

        finish();
        startActivity(getIntent());
    }

    //##################################################
    //# utility
    //##################################################

    private String formatHtml()
    {
        String s = KmLoremIpsum.getParagraph();

        KmHtmlBuilder out;
        out = new KmHtmlBuilder();

        out.printDocType401Strict();
        out.beginHtml();
        out.beginBody();

        int n = 5;
        for ( int i = 0; i < n; i++ )
        {
            out.println(s);
            out.println();
        }

        out.endBody();
        out.endHtml();

        return out.toString();
    }

}
