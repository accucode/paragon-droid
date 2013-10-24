package com.kodemore.stub.ui;

import com.kodemore.stub.MyPreferenceWrapper;
import com.kodemore.view.KmActivity;

public abstract class MyActivity
    extends KmActivity
{
    protected MyPreferenceWrapper getPreferences()
    {
        return new MyPreferenceWrapper();
    }
}
