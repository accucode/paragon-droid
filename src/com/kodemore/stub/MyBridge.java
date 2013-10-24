package com.kodemore.stub;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Environment;

import com.kodemore.acra.KmAcra;
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
        Kmu.toast(Kmu.formatMessage(ex));
        KmAcra.handleSilentException(ex);
    }

    @Override
    public String getActiveSqlPackageName()
    {
        return null;
    }

    @Override
    public String getSharedApplicationFolder()
    {
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        String sharedPath = "Android/Shared";
        String appPath = MyConstantsIF.SHARED_APPLICATION_FOLDER;

        return Kmu.joinPath(externalPath, sharedPath, appPath);
    }
}
