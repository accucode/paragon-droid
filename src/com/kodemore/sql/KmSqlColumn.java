package com.kodemore.sql;

import com.kodemore.string.KmStringBuilder;

public class KmSqlColumn
{
    //##################################################
    //# enum
    //##################################################//

    private enum ColumnType
    {
        Integer,
        Text,
        Real
    }

    private enum OnConflict
    {
        Rollback,
        Abort,
        Fail,
        Ignore,
        Replace
    }

    //##################################################
    //# variables
    //##################################################//

    private String     _name;
    private ColumnType _type;
    private boolean    _primaryKey;
    private boolean    _unique;
    private boolean    _notNull;
    private boolean    _autoIncrement;
    private OnConflict _onConflict;

    //##################################################
    //# constructor
    //##################################################//

    public KmSqlColumn(String name)
    {
        _name = name;
    }

    //##################################################
    //# accessing
    //##################################################//

    public String getName()
    {
        return _name;
    }

    public void setPrimaryKey()
    {
        _primaryKey = true;
    }

    public void setUnique()
    {
        _unique = true;
    }

    public void setNotNull()
    {
        _notNull = true;
    }

    public void setAutoIncrement()
    {
        _autoIncrement = true;
    }

    //##################################################
    //# on conflict
    //##################################################//

    public void setOnConflictRollback()
    {
        setOnConflict(OnConflict.Rollback);
    }

    public void setOnConflictAbort()
    {
        setOnConflict(OnConflict.Abort);
    }

    public void setOnConflictFail()
    {
        setOnConflict(OnConflict.Fail);
    }

    public void setOnConflictIgnore()
    {
        setOnConflict(OnConflict.Ignore);
    }

    public void setOnConflictReplace()
    {
        setOnConflict(OnConflict.Replace);
    }

    private void setOnConflict(OnConflict e)
    {
        _onConflict = e;
    }

    //##################################################
    //# types
    //##################################################//

    public void setTypeText()
    {
        _type = ColumnType.Text;
    }

    public void setTypeInteger()
    {
        _type = ColumnType.Integer;
    }

    public void setTypeReal()
    {
        _type = ColumnType.Real;
    }

    //##################################################
    //# format
    //##################################################//

    public String formatCreateTable()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.print(getName());
        out.space();
        out.print(_type.name().toLowerCase());

        if ( _primaryKey )
            out.print(" primary key");

        if ( _unique )
            out.print(" unique");

        if ( _notNull )
            out.print(" not null");

        if ( _onConflict != null )
            out.println(" on conflict " + _onConflict.name().toLowerCase());

        if ( _autoIncrement )
            out.print(" autoincrement");

        return out.toString();
    }

}
