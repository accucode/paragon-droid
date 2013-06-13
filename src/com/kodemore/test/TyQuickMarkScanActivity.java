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

import com.kodemore.scanner.KmQuickMarkScanner;
import com.kodemore.utility.KmString;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Use the QuickMark application to scan a barcode.
 */
public class TyQuickMarkScanActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmQuickMarkScanner _scannerCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _scannerCallback = newScannerCallback();
        _scannerCallback.register(ui());
    }

    private KmQuickMarkScanner newScannerCallback()
    {
        return new KmQuickMarkScanner()
        {
            @Override
            public void handleScan(String s)
            {
                KmString ss;
                ss = KmString.create(s);
                ss = ss.toDisplay().stripNonPrintable().trim();

                alert("QuickMark Result: %s.", ss);
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Scan", _scannerCallback);
        return root;
    }

}
