package com.kodemore.stub;

import android.app.Application;

import com.kodemore.file.KmApplicationFilePath;
import com.kodemore.file.KmSharedFilePath;

public class MyApplication
    extends Application
{
    //##################################################
    //# create
    //##################################################

    @Override
    public void onCreate()
    {
        super.onCreate();

        MyGlobals.setApplicationContext(getApplicationContext());
        MyBridge.install();

        // review_steve: for external files - inserted these 
        new KmApplicationFilePath().createFolder();
        new KmSharedFilePath().createFolder();
    }

}
