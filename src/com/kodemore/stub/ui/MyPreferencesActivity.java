package com.kodemore.stub.ui;

import com.kodemore.preference.activity.KmPreferenceActivity;
import com.kodemore.preference.activity.KmPreferenceActivityHelper;
import com.kodemore.preference.builder.KmBooleanPreferenceBuilder;
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

    private KmBooleanPreferenceBuilder newSplashPreference()
    {
        KmBooleanPreferenceBuilder e;
        e = new KmBooleanPreferenceBuilder();
        e.setKey(MyPreferenceWrapper.SPLASH_KEY);
        e.setDefaultValue(MyPreferenceWrapper.SPLASH_DEFAULT);
        e.setTitle("Enable splash screen?");
        e.setTrueSummary("Splash screen is enabled");
        e.setFalseSummary("Splash screen is disabled");
        return e;
    }
}
