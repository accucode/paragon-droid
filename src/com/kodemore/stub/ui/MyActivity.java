package com.kodemore.stub.ui;

import com.kodemore.stub.MyPreferenceController;
import com.kodemore.view.KmActivity;

public abstract class MyActivity
    extends KmActivity
{
    protected MyPreferenceController getPreferences()
    {
        return new MyPreferenceController();
    }
}
