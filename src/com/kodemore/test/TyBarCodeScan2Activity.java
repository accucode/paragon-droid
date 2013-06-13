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

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Use the BarCode Scanner application to scan a barcode.
 * 
 * Based on zxing.
 * This works, and the api is convenience.
 * But the scanned results are unreliable.
 */
public class TyBarCodeScan2Activity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        //none
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
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newScanAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                IntentIntegrator integrator = new IntentIntegrator(TyBarCodeScan2Activity.this);
                integrator.initiateScan();
            }
        };
    }

    //##################################################
    //# result
    //##################################################

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(
            requestCode,
            resultCode,
            data);

        if ( scanResult != null )
        {
            alert("Result OK", scanResult.toString());
            return;
        }

        alert("Error");
    }
}
