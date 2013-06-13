package com.kodemore.stub;

import com.kodemore.preference.KmAbstractPreferenceController;

public class MyPreferenceController
    extends KmAbstractPreferenceController
{
    //##################################################
    //# constants
    //##################################################

    public static final String NAME                     = "kodemore.stub.preferences";

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

    public String getSqlPackageName()
    {
        return getString(SQL_PACKAGE_NAME_KEY, SQL_PACKAGE_NAME_DEFAULT);
    }

    public void setSqlPackageName(String e)
    {
        setString(SQL_PACKAGE_NAME_KEY, e);
    }
}
