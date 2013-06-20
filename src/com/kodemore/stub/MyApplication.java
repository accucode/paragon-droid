package com.kodemore.stub;

import android.app.Application;

public class MyApplication
    extends Application
{
    //##################################################
    //# create
    //##################################################//

    @Override
    public void onCreate()
    {
        super.onCreate();

        MyGlobals.setApplicationContext(getApplicationContext());
        MyBridge.install();
    }

}
