package com.kodemore.stub.ui;

import com.kodemore.preference.KmBooleanComponentBuilder;
import com.kodemore.preference.KmPreferenceActivity;
import com.kodemore.preference.KmPreferenceActivityHelper;
import com.kodemore.stub.MyPreferenceWrapper;

/**
 * A starting point for application wide preferences.
 */
public class MyPreferencesActivity
    extends KmPreferenceActivity
{
    //##################################################
    //# create
    //#################################################//

    @Override
    protected void installPreferenceScreen()
    {
        KmPreferenceActivityHelper scr;
        scr = newHelper(MyPreferenceWrapper.NAME);

        scr.add(newSplashPreference());

        scr.finish();
    }

    //##################################################
    //# preferences
    //##################################################//

    private KmBooleanComponentBuilder newSplashPreference()
    {
        KmBooleanComponentBuilder e;
        e = new KmBooleanComponentBuilder();
        e.setKey(MyPreferenceWrapper.SPLASH_KEY);
        e.setDefaultValue(MyPreferenceWrapper.SPLASH_DEFAULT);
        e.setTitle("Enable splash screen?");
        e.setTrueSummary("Splash screen is enabled");
        e.setFalseSummary("Splash screen is disabled");
        return e;
    }
}
