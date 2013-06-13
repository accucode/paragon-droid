package com.kodemore.sql;

public class KmSqlAndCondition
    extends KmSqlCompositeCondition
{
    @Override
    protected String getJoin()
    {
        return AND;
    }
}
