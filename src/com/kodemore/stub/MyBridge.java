package com.kodemore.stub;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Environment;

import com.kodemore.utility.KmBridge;
import com.kodemore.utility.Kmu;

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

    // review_steve: for external files - replaced the other getSharedApplicationFolder() 
    @Override
    public String getSharedApplicationFolder()
    {
        String external = Environment.getExternalStorageDirectory().getPath();
        return Kmu.joinPath(external, "Android/Shared", MyConstantsIF.SHARED_APPLICATION_FOLDER);
    }
}
