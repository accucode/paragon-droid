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

import com.kodemore.scanner.KmQrDroidScanner;
import com.kodemore.utility.KmString;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Use the QR Droid application to scan a barcode.
 */
public class TyQrDroidScanActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmQrDroidScanner _scanner;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _scanner = newScanner();
        _scanner.register(ui());
    }

    private KmQrDroidScanner newScanner()
    {
        return new KmQrDroidScanner()
        {
            @Override
            public void handleScan(String s)
            {
                handleScanResult(s);
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
        root.addButton("Scan", newScanAction());
        root.addButton("Scan Data Matrix", newScanDataMatrixAction());
        root.addButton("Scan QR Code", newScanQrCodeAction());
        root.addButton("Scan 1D", newScanOneDAction());
        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newScanAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleScan();
            }
        };
    }

    private KmAction newScanDataMatrixAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleScanDataMatrix();
            }
        };
    }

    private KmAction newScanQrCodeAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleScanQrCode();
            }
        };
    }

    private KmAction newScanOneDAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleScanOneDCode();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleScan()
    {
        _scanner.run();
    }

    private void handleScanDataMatrix()
    {
        _scanner.setMode_DATA_MATRIX();
        _scanner.run();
    }

    private void handleScanQrCode()
    {
        _scanner.setMode_QR_CODE();
        _scanner.run();
    }

    private void handleScanOneDCode()
    {
        _scanner.setMode_ONE_D();
        _scanner.run();
    }

    private void handleScanResult(String s)
    {
        String ss;
        ss = KmString.create(s).toDisplay().stripNonPrintable().trim().toString();
        alert("QR Droid Result", ss);
    }

}
