package com.kodemore.stub;

import android.content.Context;

import com.kodemore.preference.KmAbstractPreferences;

public class MyPreferences
    extends KmAbstractPreferences
{
    //##################################################
    //# constants
    //##################################################//

    /**
     * FIXME stub
     * The name should be set to a name that is unique to this application.
     * Typically, the application's package, plus a .preferences suffix.
     */
    public static final String  NAME           = "com.kodemore.stub.preferences";

    /**
     * Control whether the splash page is displayed.
     */
    public static final String  SPLASH_KEY     = "splash";
    public static final Boolean SPLASH_DEFAULT = true;

    //##################################################
    //# constructor
    //##################################################//

    public MyPreferences(Context context)
    {
        super(context, NAME);
    }

    //##################################################
    //# accessing
    //##################################################//

    public boolean getShowsSplash()
    {
        return getBoolean(SPLASH_KEY, SPLASH_DEFAULT);
    }

    public void setShowsSplash(boolean e)
    {
        setBoolean(SPLASH_KEY, e);
    }
}
