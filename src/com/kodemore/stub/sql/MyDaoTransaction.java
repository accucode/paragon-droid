package com.kodemore.stub.sql;

import com.kodemore.sql.KmSqlCommand;
import com.kodemore.sql.KmSqlDatabase;

public abstract class MyDaoTransaction
    extends KmSqlCommand
{
    //##################################################
    //# override
    //##################################################

    @Override
    protected KmSqlDatabase getDatabase()
    {
        return MyDatabaseManager.getInstance().getDatabase();
    }

    //##################################################
    //# accessing
    //##################################################

    protected MyDaoAccess getAccess()
    {
        return MyDaoAccess.getInstance();
    }
}
