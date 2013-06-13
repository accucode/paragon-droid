package com.kodemore.sql;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;

public class KmSqlCreateTableBuilder
{
    //##################################################
    //# variables
    //##################################################//

    private KmList<KmSqlTable> _tables;

    //##################################################
    //# constructor
    //##################################################//

    public KmSqlCreateTableBuilder()
    {
        _tables = new KmList<KmSqlTable>();
    }

    //##################################################
    //# accessing
    //##################################################//

    public KmSqlTable addTable(String name)
    {
        KmSqlTable e = new KmSqlTable(name);
        _tables.add(e);
        return e;
    }

    //##################################################
    //# formatting
    //##################################################//

    public String getCreateTableScript()
    {
        KmStringBuilder out = new KmStringBuilder();

        for ( KmSqlTable e : _tables )
            out.println(e.formatCreateTable());

        return out.toString();
    }

}
