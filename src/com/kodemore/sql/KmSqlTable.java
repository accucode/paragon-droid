package com.kodemore.sql;

import java.util.Iterator;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;

public class KmSqlTable
{
    //##################################################
    //# variables
    //##################################################//

    private String              _name;
    private KmList<KmSqlColumn> _columns;

    //##################################################
    //# constructor
    //##################################################//

    public KmSqlTable(String name)
    {
        _name = name;
        _columns = new KmList<KmSqlColumn>();
    }

    //##################################################
    //# accessing
    //##################################################//

    public String getName()
    {
        return _name;
    }

    //##################################################
    //# columns
    //##################################################//

    private KmSqlColumn addColumn(String name)
    {
        KmSqlColumn c = new KmSqlColumn(name);
        _columns.add(c);
        return c;
    }

    public KmSqlColumn addIntegerColumn(String name)
    {
        KmSqlColumn c;
        c = addColumn(name);
        c.setTypeInteger();
        return c;
    }

    public KmSqlColumn addIntegerPrimaryKeyColumn(String name)
    {
        KmSqlColumn e;
        e = addIntegerColumn(name);
        e.setPrimaryKey();
        e.setAutoIncrement();
        return e;
    }

    public KmSqlColumn addStringColumn(String name)
    {
        KmSqlColumn e;
        e = addColumn(name);
        e.setTypeText();
        return e;
    }

    public KmSqlColumn addStringPrimaryKeyColumn(String name)
    {
        KmSqlColumn e;
        e = addStringColumn(name);
        e.setPrimaryKey();
        e.setNotNull();
        return e;
    }

    public KmSqlColumn addDoubleColumn(String name)
    {
        KmSqlColumn e;
        e = addColumn(name);
        e.setTypeReal();
        return e;
    }

    //##################################################
    //# format
    //##################################################//

    public String formatCreateTable()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.print("create table ");
        out.print(getName());
        out.print("(");

        Iterator<KmSqlColumn> i = _columns.iterator();
        while ( i.hasNext() )
        {
            KmSqlColumn col = i.next();
            out.print(col.formatCreateTable());
            if ( i.hasNext() )
                out.print(", ");
        }

        out.print(");");
        return out.toString();
    }

}
