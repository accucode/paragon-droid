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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;

import com.kodemore.cs3070.KmCs3070BarcodeListenerIF;
import com.kodemore.cs3070.KmCs3070BarcodeScanner;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmBridge;
import com.kodemore.utility.KmRunnable;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextSwitcher;
import com.kodemore.view.KmTextView;

/**
 * Test access to the bluetooth CS3070 barcode scanner.
 */
public class TyBluetoothScannerActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmSimpleIntentCallback _requestEnableCallbackIntent;
    private KmCs3070BarcodeScanner _scanner;
    private KmTextView             _deviceText;
    private KmTextSwitcher         _barcodeText;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _scanner = new KmCs3070BarcodeScanner();
        _scanner.setListener(newBarcodeListener());
        _scanner.setDeviceAuto();

        _requestEnableCallbackIntent = newRequestEnableCallbackIntent();
        _requestEnableCallbackIntent.register(ui());

        _deviceText = new KmTextView(ui());
        _deviceText.setAutoSave();

        _barcodeText = new KmTextSwitcher(ui());
        _barcodeText.setAutoSave();
        _barcodeText.getHelper().setFontSize(36);
        _barcodeText.getHelper().setBold();
        _barcodeText.setTextFast("barcode...");

        refreshSelectedDevice();
    }

    //==================================================
    //= init :: callback
    //==================================================

    private KmSimpleIntentCallback newRequestEnableCallbackIntent()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public Intent getRequest()
            {
                return new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }

            @Override
            public void handleOk(Intent data)
            {
                toast("Bluetooth is now enabled.");
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
        root.setPadding(5);

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Status", newCheckStatusAction());
        row.addButton("Enable", newRequestEnableAction());

        root.addSpace();
        root.addButton("Auto Select Device", newAutoSelectDeviceAction());
        root.addViewFullWeight(_deviceText);
        root.addSpace();

        row = root.addEvenRow();
        row.addButton("Start Client", newStartClientAction());
        row.addButton("Stop Client", newStopClientAction());

        root.addView(_barcodeText);
        return root;
    }

    //##################################################
    //# listener
    //##################################################

    private KmCs3070BarcodeListenerIF newBarcodeListener()
    {
        return new KmCs3070BarcodeListenerIF()
        {
            @Override
            public void onBarcodeScan(final String e)
            {
                new KmRunnable()
                {
                    @Override
                    public void run()
                    {
                        _barcodeText.setText(e);
                    }
                }.runOnUiThread(ui());
            }
        };
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newCheckStatusAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleCheckStatus();
            }
        };
    }

    private KmAction newRequestEnableAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRequestEnable();
            }
        };
    }

    private KmAction newAutoSelectDeviceAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAutoSelectDevice();
            }
        };
    }

    private KmAction newStartClientAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleStartClient();
            }
        };
    }

    private KmAction newStopClientAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleStopClient();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleCheckStatus()
    {
        BluetoothAdapter a = getAdapter();

        if ( a == null )
        {
            alert("Bluetooth NOT supported");
            return;
        }

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.printfln("Enabled: %s", a.isEnabled());
        out.printfln("Discovering: %s", a.isDiscovering());

        alert(out.toString());
    }

    private void handleRequestEnable()
    {
        BluetoothAdapter a = getAdapter();

        if ( a.isEnabled() )
            alert("Already enabled.");
        else
            _requestEnableCallbackIntent.run();
    }

    private void handleAutoSelectDevice()
    {
        _scanner.setDeviceAuto();
        refreshSelectedDevice();
    }

    private void handleStartClient()
    {
        if ( _scanner.isRunning() )
        {
            alert("Already Running");
            return;
        }

        if ( _scanner.start() )
        {
            alert("Scanner Started");
            return;
        }

        alert("Unable to Start");
    }

    private void handleStopClient()
    {
        if ( _scanner.isNotRunning() )
        {
            alert("Not Running");
            return;
        }

        if ( _scanner.stop() )
        {
            alert("Scanner Stopped");
            return;
        }

        alert("Unable to Stop");
    }

    //##################################################
    //# utility
    //##################################################

    private BluetoothAdapter getAdapter()
    {
        return KmBridge.getInstance().getBluetoothAdapter();
    }

    private String formatBondedState(BluetoothDevice e)
    {
        int i = e.getBondState();

        if ( i == e.BOND_BONDED )
            return "Bonded";

        if ( i == e.BOND_BONDING )
            return "Bonding";

        if ( i == e.BOND_NONE )
            return "No Bond";

        return "Unknown";
    }

    private void refreshSelectedDevice()
    {
        _deviceText.setText(formatSelectedDevice());
    }

    private CharSequence formatSelectedDevice()
    {
        BluetoothDevice e = _scanner.getDevice();
        if ( e == null )
            return "<no device>";

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println(e.getName());
        out.println(e.getAddress());
        out.println(formatBondedState(e));
        out.println(e.getBluetoothClass());
        return out;
    }
}
