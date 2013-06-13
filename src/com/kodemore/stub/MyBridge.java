package com.kodemore.stub;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.kodemore.utility.KmBridge;

public class MyBridge
    extends KmBridge
{
    //##################################################
    //# install
    //##################################################//

    public static void install()
    {
        install(new MyBridge());
    }

    private MyBridge()
    {
        // private
    }

    //##################################################
    //# accessing
    //##################################################//

    @Override
    public Context getApplicationContext()
    {
        return MyGlobals.getApplicationContext();
    }

    @Override
    public BluetoothAdapter getBluetoothAdapter()
    {
        return MyGlobals.getBluetoothAdapter();
    }

    @Override
    public void handleException(Exception ex)
    {
        //none

    }

    @Override
    public String getActiveSqlPackageName()
    {
        return null;
    }

    @Override
    public String getSharedApplicationFolder()
    {
        return null;
    }

}
