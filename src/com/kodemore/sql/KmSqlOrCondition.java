package com.kodemore.sql;

public class KmSqlOrCondition
    extends KmSqlCompositeCondition
{
    @Override
    protected String getJoin()
    {
        return OR;
    }
}
