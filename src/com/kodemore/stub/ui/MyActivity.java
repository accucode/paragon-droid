package com.kodemore.stub.ui;

import com.kodemore.stub.MyPreferences;
import com.kodemore.view.KmActivity;

public abstract class MyActivity
    extends KmActivity
{
    protected MyPreferences getPreferences()
    {
        return new MyPreferences(getContext());
    }
}
