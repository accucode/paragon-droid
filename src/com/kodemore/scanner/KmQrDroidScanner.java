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
 * I wrap access to the QR Droid application to scan barcodes.
 * The api is convenient and is seems to work faily well for 
 * 1D and QR Codes tested so far.
 * 
 * PROBLEM: It does not reliably scan the Data Matrix format.
 * Attempts to do so generate recognizable, but garbled, results.
 * This problem exists even when you explicity tell the scanner
 * that it should be looking for ONLY data matrix barcodes.
 * 
 * As of March 2011, the underlying engine is zxing.
 * http://code.google.com/p/zxing/source/browse/trunk/core/src/com/google/zxing/BarcodeFormat.java  
 * 
 * Information from: 
 * http://qrdroid.com/services/android-developers
 */
public abstract class KmQrDroidScanner
    extends KmSimpleIntentCallback
{
    //##################################################
    //# constants
    //##################################################

    /**
     * The intent name used to scan a qr code and return the result.
     * 
     * Alternate intents are available for encoding and decoding
     * qr codes, but these are not currently supported by this tool.
     */
    private static String       SCAN_INTENT       = "la.droid.qr.scan";

    /**
     * The intent package.
     */
    private static String       INTENT_PACKAGE    = "la.droid.qr";

    /**
     * The key used to tell qr droid to return the complete scan.
     * Without this, the default behavior leaves out certain information.
     * 
     * Original data:   tel:+1234567
     * Complete result: tel:+1234567
     * Default result:  +1234567
     */
    private static String       COMPLETE_DATA_KEY = "la.droid.qr.complete";

    /**
     * The key used to retrieve the results.
     */
    private static final String RESULT_KEY        = "la.droid.qr.result";

    //##################################################
    //# variables
    //##################################################

    /**
     * Set a general mode, indicating barcodes should be
     * recognized.  By default, no mode is selected; all
     * bar code types are accepted.  The modes are mutually 
     * exclusive.
     * You should NOT specify both a mode and a format.
     */
    private String              _mode;

    /**
     * Set the specific list of bar code symbologies to be
     * recognized.  This is more detailed than setting the
     * mode.  You can specify any combination of formats.
     * You should NOT specify both a mode and a format.
     */
    private KmList<String>      _formats;

    /**
     * We normally want the complete data, rather than having
     * zxing (or qrDroid) ignore parts of the scan data.
     * Defaults to true.
     */
    private boolean             _completeData;

    //##################################################
    //# constructor
    //##################################################

    public KmQrDroidScanner()
    {
        _mode = null;
        _formats = new KmList<String>();
        _completeData = true;
    }

    //##################################################
    //# scan formats
    //##################################################

    public KmList<String> getFormats()
    {
        return _formats;
    }

    public void addFormat_PDF417()
    {
        addFormat("PDF417");
    }

    public void addFormat_ITF()
    {
        addFormat("ITF");
    }

    public void addFormat_CODE_39()
    {
        addFormat("CODE_39");
    }

    public void addFormat_CODE_128()
    {
        addFormat("CODE_128");
    }

    public void addFormat_EAN_13()
    {
        addFormat("EAN_13");
    }

    public void addFormat_EAN_8()
    {
        addFormat("EAN_8");
    }

    public void addFormat_UPC_A()
    {
        addFormat("UPC_A");
    }

    public void addFormat_UPC_E()
    {
        addFormat("UPC_E");
    }

    public void addFormat_DATAMATRIX()
    {
        addFormat("DATAMATRIX");
    }

    public void addFormat_QR_CODE()
    {
        addFormat("QR_CODE");
    }

    private void addFormat(String mode)
    {
        _formats.addDistinct(mode);
    }

    //##################################################
    //# scan modes
    //##################################################

    public String getMode()
    {
        return _mode;
    }

    public void setMode_ONE_D()
    {
        _mode = "ONE_D_MODE";
    }

    public void setMode_PRODUCT()
    {
        _mode = "PRODUCT_MODE";
    }

    public void setMode_QR_CODE()
    {
        _mode = "QR_CODE_MODE";
    }

    public void setMode_DATA_MATRIX()
    {
        _mode = "DATA_MATRIX_MODE";
    }

    //##################################################
    //# complete data
    //##################################################

    public boolean getCompleteData()
    {
        return _completeData;
    }

    public void setCompleteData(boolean e)
    {
        _completeData = e;
    }

    //##################################################
    //# scan
    //##################################################

    @Override
    public Intent getRequest()
    {
        if ( !checkInstallation() )
            return null;

        Intent intent;
        intent = new Intent(SCAN_INTENT);

        if ( getCompleteData() )
            intent.putExtra(COMPLETE_DATA_KEY, true);

        if ( getFormats().isNotEmpty() )
            intent.putExtra("SCAN_FORMAT", getFormats().format(","));

        if ( getMode() != null )
            intent.putExtra("SCAN_MODE", getMode());

        return intent;
    }

    @Override
    public final void handleOk(Intent data)
    {
        String value = data.getExtras().getString(RESULT_KEY);
        handleScan(value);
    }

    protected abstract void handleScan(String value);

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
            + "The QR Droid tool is required to scan barcodes. "
            + "This application is available for free, and can be "
            + "downloaded from the Android Market.  Would you like "
            + "to open the Market now?";

        Builder b;
        b = new AlertDialog.Builder(getContext());
        b.setTitle("QR Droid Required");
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
