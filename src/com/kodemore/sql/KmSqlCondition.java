package com.kodemore.sql;

public class KmSqlCondition
    implements KmSqlConstantsIF
{
    //##################################################
    //# constructor
    //##################################################

    public KmSqlCondition()
    {
        // none
    }

    //##################################################
    //# utility
    //##################################################

    protected String formatColumn(String table, KmSqlColumnIF col)
    {
        return KmSqlUtility.formatColumn(table, col);
    }
}
