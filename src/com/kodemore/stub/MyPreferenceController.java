package com.kodemore.stub;

import com.kodemore.preference.KmAbstractPreferenceController;

public class MyPreferenceController
    extends KmAbstractPreferenceController
{
    //##################################################
    //# constants
    //##################################################

    /**
     * todo_wyatt: move to constants
     */
    public static final String NAME                     = "kodemore.stub.preferences";

    public static final String DEVICE_UID_KEY           = "deviceUid";
    public static final String DEVICE_UID_DEFAULT       = null;

    public static final String SQL_PACKAGE_NAME_KEY     = "sqlPackageName";
    public static final String SQL_PACKAGE_NAME_DEFAULT = "default";

    //##################################################
    //# constructor
    //##################################################

    public MyPreferenceController()
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
}
