package com.kodemore.stub.ui;

import android.view.View;

import com.kodemore.cs3070.KmCs3070BarcodeService;
import com.kodemore.test.TyMainActivity;
import com.kodemore.view.KmColumnLayout;

public class MyMainActivity
    extends MyActivity
{
    //##################################################
    //# init
    //##################################################//

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# life cycle
    //##################################################

    @Override
    protected void onStart()
    {
        super.onStart();

        startService(KmCs3070BarcodeService.class);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if ( isFinishing() )
            stopService(KmCs3070BarcodeService.class);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        /**
         * FIXME: welcome message
         * Change this to a short app specific message.
         * Long term, you will likely redesign this page completely.
         */
        String title = "Welcome to Paragon";

        KmColumnLayout root;
        root = ui().newColumn();
        root.addText(title);
        root.addFiller();
        root.addButton("Preferences", MyPreferencesActivity.class);
        root.addButton("Tests", TyMainActivity.class);
        return root;
    }
}
