package com.kodemore.stub;

import com.kodemore.preference.wrapper.KmAbstractPreferenceWrapper;

public class MyPreferenceWrapper
    extends KmAbstractPreferenceWrapper
{
    //##################################################
    //# constants
    //##################################################

    public static final String  NAME                     = MyConstantsIF.APPLICATION_PACKAGE
                                                             + ".preferences";

    public static final String  DEVICE_UID_KEY           = "deviceUid";
    public static final String  DEVICE_UID_DEFAULT       = null;

    public static final String  SQL_PACKAGE_NAME_KEY     = "sqlPackageName";
    public static final String  SQL_PACKAGE_NAME_DEFAULT = "default";

    /**
     * Control whether the splash page is displayed.
     */
    public static final String  SPLASH_KEY               = "splash";
    public static final Boolean SPLASH_DEFAULT           = true;

    //##################################################
    //# constructor
    //##################################################

    public MyPreferenceWrapper()
    {
        super(NAME);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getDeviceUid()
    {
        return getString(DEVICE_UID_KEY, DEVICE_UID_DEFAULT);
    }

    public void setDeviceUid(String s)
    {
        setString(DEVICE_UID_KEY, s);
    }

    public String getSqlPackageName()
    {
        return getString(SQL_PACKAGE_NAME_KEY, SQL_PACKAGE_NAME_DEFAULT);
    }

    public void setSqlPackageName(String e)
    {
        setString(SQL_PACKAGE_NAME_KEY, e);
    }

    public boolean getShowsSplash()
    {
        return getBoolean(SPLASH_KEY, SPLASH_DEFAULT);
    }

    public void setShowsSplash(boolean e)
    {
        setBoolean(SPLASH_KEY, e);
    }

}
