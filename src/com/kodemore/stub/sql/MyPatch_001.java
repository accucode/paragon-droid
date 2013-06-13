package com.kodemore.stub.sql;

public class MyPatch_001
    extends MyPatch
{
    //##################################################
    //# overrides
    //##################################################

    @Override
    public int getVersion()
    {
        return 1;
    }

    @Override
    public String getName()
    {
        return "Initialize sample database.";
    }

    @Override
    public void upgradeInBackground()
    {
        runScriptFile();
    }

}
