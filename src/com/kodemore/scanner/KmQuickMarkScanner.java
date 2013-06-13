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

package com.kodemore.scanner;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;

import com.kodemore.collection.KmList;
import com.kodemore.intent.KmIntentUtility;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.KmPackageManager;
import com.kodemore.utility.KmResolveInfo;
import com.kodemore.view.KmAction;

/**
 * I wrap access to the free QuickMark application to 
 * scan barcodes.
 * 
 * QuickMark is the best solution so far, and the ONLY
 * tool with a (free) developer API that correctly
 * scans Data Matrix 2D barcodes.
 * 
 * Information from: 
 * http://qrdroid.com/services/android-developers
 * 
 * FYI... I tested the SCAN_FORMAT and SCAN_MODE parameters
 * described for zxing, just in case quickMark happened to 
 * support them.  But any attempt to use them seems to just
 * cause the app to fatally crash.
 */
public abstract class KmQuickMarkScanner
    extends KmSimpleIntentCallback
{
    //##################################################
    //# constants
    //##################################################

    /**
     * The action used to launch the intent.
     * 
     * Although QuickMark uses zxing's action, it does not 
     * appear to use the zxing engine.  Which is good since
     * the zxing engine results in garbled reads.
     */
    private static String INTENT_ACTION  = "com.google.zxing.client.android.SCAN";

    /**
     * The activity package for the intent.
     * We want to ~force the system to specifically use the 
     * quickmark activity; not just any activity that responds
     * to the SCAN action.
     */
    private static String INTENT_PACKAGE = "tw.com.quickmark";

    /**
     * The key used to retrieve the data.
     */
    private static String RESULT_DATA    = "SCAN_RESULT";

    //##################################################
    //# constructor
    //##################################################

    public KmQuickMarkScanner()
    {
        // none
    }

    //##################################################
    //# scan
    //##################################################

    @Override
    public final Intent getRequest()
    {
        if ( !checkInstallation() )
            return null;

        Intent e;
        e = new Intent(INTENT_ACTION);
        e.setPackage(INTENT_PACKAGE);
        return e;
    }

    @Override
    public final void handleOk(Intent data)
    {
        String value = data.getStringExtra(RESULT_DATA);
        handleScan(value);
    }

    public abstract void handleScan(String value);

    //##################################################
    //# installation
    //##################################################

    public boolean isInstalled()
    {
        KmPackageManager pm = new KmPackageManager();
        KmList<KmResolveInfo> v = pm.findPackage(INTENT_PACKAGE);
        return v.isNotEmpty();
    }

    public boolean checkInstallation()
    {
        if ( isInstalled() )
            return true;

        String msg = ""
            + "The QuickMark Scanner is required to scan barcodes. "
            + "This application is available for free, and can be "
            + "downloaded from the Android Market.  Would you like "
            + "to open the Market now?";

        Builder b;
        b = new AlertDialog.Builder(getContext());
        b.setTitle("QuickMark Scanner Required");
        b.setMessage(msg);
        b.setPositiveButton("Open Market", newOpenMarketAction());
        b.setNegativeButton("Not Now", KmAction.NOOP);
        b.show();

        return false;
    }

    private KmAction newOpenMarketAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                KmIntentUtility.viewMarketDetails(getContext(), INTENT_PACKAGE);
            }
        };
    }
}
