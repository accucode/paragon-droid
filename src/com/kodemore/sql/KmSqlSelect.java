package com.kodemore.sql;

import com.kodemore.database.KmSqlCursor;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;

/**
 * I provide abstract functionality for raw select statements.
 */
public class KmSqlSelect
    implements KmSqlConstantsIF
{
    //##################################################
    //# variables
    //##################################################

    private KmSqlDatabase     _database;

    private boolean           _distinct;
    private String            _columns;
    private String            _from;
    private KmSqlAndCondition _where;
    private String            _groupBy;
    private KmSqlAndCondition _having;
    private String            _orderBy;
    private Integer           _limit;
    private Integer           _offset;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlSelect(KmSqlDatabase db)
    {
        _database = db;
        _distinct = false;
        _where = new KmSqlAndCondition();
        _having = new KmSqlAndCondition();
    }

    //##################################################
    //# select
    //##################################################

    public void selectAll()
    {
        select("*");
    }

    public void selectAllFrom(String table)
    {
        select(table + ".*");
    }

    public void select(KmSqlColumnIF e)
    {
        select(e.getName());
    }

    private void select(String name)
    {
        _columns = join(_columns, name);
    }

    public void selectCount()
    {
        select("count(*)");
    }

    public void selectCount(KmSqlColumnIF col)
    {
        String s = Kmu.format("count(%s)", col.getName());
        select(s);
    }

    public void selectCountDistinct(KmSqlColumnIF col)
    {
        String s = Kmu.format("count(distinct %s)", col.getName());
        select(s);
    }

    public void selectSum(KmSqlColumnIF col)
    {
        String s = Kmu.format("sum(%s)", col.getName());
        select(s);
    }

    public void selectSumOfProducts(KmSqlColumnIF col1, KmSqlColumnIF col2)
    {
        String s = Kmu.format("sum(%s * %s)", col1.getName(), col2.getName());
        select(s);
    }

    public void selectProductOf(KmSqlColumnIF col1, KmSqlColumnIF col2)
    {
        String s = Kmu.format("%s * %s", col1.getName(), col2.getName());
        select(s);
    }

    //##################################################
    //# from
    //##################################################

    public void from(String table)
    {
        _from = join(_from, table);
    }

    //##################################################
    //# where
    //##################################################

    public KmSqlAndCondition where()
    {
        return _where;
    }

    //##################################################
    //# group by
    //##################################################

    public void groupBy(String name)
    {
        _groupBy = join(_groupBy, name);
    }

    public void groupBy(KmSqlColumnIF e)
    {
        groupBy(null, e);
    }

    public void groupBy(String table, KmSqlColumnIF col)
    {
        String s = formatColumn(table, col);
        groupBy(s);
    }

    //##################################################
    //# having
    //##################################################

    public KmSqlAndCondition having()
    {
        return _having;
    }

    //##################################################
    //# order by
    //##################################################

    public void orderBy(KmSqlColumnIF e)
    {
        orderBy(e.getName());
    }

    public void orderBy(KmSqlColumnIF e, boolean asc)
    {
        if ( asc )
            orderBy(e);
        else
            orderByDescending(e);
    }

    public void orderBy(String name)
    {
        _orderBy = join(_orderBy, name);
    }

    public void orderByDescending(KmSqlColumnIF e)
    {
        orderByDescending(e.getName());
    }

    public void orderByDescending(String name)
    {
        _orderBy = join(_orderBy, name + " desc");
    }

    //##################################################
    //# limit
    //##################################################

    /**
     * A postive number that limits the number of records returned.
     * May be null (the default), in which case no limit is used.
     */
    public void limit(Integer i)
    {
        _limit = i;
    }

    /**
     * A postive number that skips the first i records in the result set.
     * Can be useful with returning paginated results in combination with limit.
     * May be null (the default), in which case no offset is used.
     */
    public void offset(Integer i)
    {
        _offset = i;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public final String toString()
    {
        return formatSql();
    }

    //##################################################
    //# format
    //##################################################

    public String formatSql()
    {
        KmStringBuilder out = new KmStringBuilder();

        formatSelect(out);
        formatDistinct(out);
        formatColumns(out);
        formatFrom(out);
        formatWhere(out);
        formatHaving(out);
        formatOrderBy(out);
        formatLimit(out);
        formatOffset(out);

        return out.toString();
    }

    private void formatSelect(KmStringBuilder out)
    {
        out.print("select");
    }

    private void formatDistinct(KmStringBuilder out)
    {
        if ( _distinct )
            out.print(" distinct");
    }

    private void formatColumns(KmStringBuilder out)
    {
        out.space();

        if ( Kmu.hasValue(_columns) )
            out.print(_columns);
        else
            out.print("*");
    }

    private void formatFrom(KmStringBuilder out)
    {
        out.print(" from ");
        out.print(_from);
    }

    private void formatWhere(KmStringBuilder out)
    {
        if ( _where.isEmpty() )
            return;

        out.print(" where ");
        out.print(_where);
    }

    private void formatHaving(KmStringBuilder out)
    {
        if ( _having.isEmpty() )
            return;

        out.print(" having ");
        out.print(_having);
    }

    private void formatOrderBy(KmStringBuilder out)
    {
        if ( Kmu.isEmpty(_orderBy) )
            return;

        out.print(" order by ");
        out.print(_orderBy);
    }

    private void formatLimit(KmStringBuilder out)
    {
        if ( _limit == null )
            return;

        out.print(" limit ");
        out.print(_limit);
    }

    private void formatOffset(KmStringBuilder out)
    {
        if ( _offset == null )
            return;

        out.print(" offset ");
        out.print(_offset);
    }

    //##################################################
    //# query
    //##################################################

    protected KmSqlDatabase getDatabase()
    {
        return _database;
    }

    protected KmSqlCursor query()
    {
        String sql = formatSql();
        return _database.query(sql);
    }

    /**
     * Run the query for a single 1x1 value.
     * That is, a single row containing a single column.
     * Throw an exception if the result is not an exact 1x1.
     * 
     * Note: this overrides the limit.
     */
    public Integer querySimpleInteger()
    {
        limit(2);

        KmSqlCursor c = null;
        try
        {
            c = query();
            c.checkSimpleResultSet();
            c.next();
            return c.getIntegerAt(0);
        }
        finally
        {
            closeSafely(c);
        }
    }

    public Long querySimpleLong()
    {
        limit(2);

        KmSqlCursor c = null;
        try
        {
            c = query();
            c.checkSimpleResultSet();
            c.next();
            return c.getLongAt(0);
        }
        finally
        {
            closeSafely(c);
        }
    }

    protected void closeSafely(KmSqlCursor c)
    {
        if ( c != null )
            c.closeSafely();
    }

    //##################################################
    //# support
    //##################################################

    private String join(String a, String b)
    {
        return Kmu.join(a, b, COMMA);
    }

    private String formatColumn(String table, KmSqlColumnIF col)
    {
        return KmSqlUtility.formatColumn(table, col);
    }
}
