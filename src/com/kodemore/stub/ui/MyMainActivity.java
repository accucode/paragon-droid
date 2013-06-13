package com.kodemore.stub.ui;

import android.view.View;

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
        String title = "Welcome to Kodemore";

        KmColumnLayout root;
        root = ui().newColumn();
        root.addText(title);
        root.addFiller();
        root.addButton("Preferences", MyPreferencesActivity.class);
        root.addButton("Tests", TyMainActivity.class);
        return root;
    }
}
