package com.kodemore.stub;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

public abstract class MyGlobals
{
    //##################################################
    //# variables
    //##################################################//

    /**
     * The global application context;
     */
    private static Context          _applicationContext;
    private static BluetoothAdapter _bluetoothAdapter;

    //##################################################
    //# accessing
    //##################################################//

    public static Context getApplicationContext()
    {
        return _applicationContext;
    }

    public static void setApplicationContext(Context e)
    {
        _applicationContext = e;
    }

    public static BluetoothAdapter getBluetoothAdapter()
    {
        return _bluetoothAdapter;
    }

    public static void setBluetoothAdapter(BluetoothAdapter e)
    {
        _bluetoothAdapter = e;
    }
}
