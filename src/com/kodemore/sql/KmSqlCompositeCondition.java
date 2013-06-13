package com.kodemore.sql;

import java.util.Iterator;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;

/**
 * I represent a composite condition, combining multiple
 * other conditions in an AND or OR expression.
 * For example where... [name = 'bob' AND age = 21] 
 */
public abstract class KmSqlCompositeCondition
    extends KmSqlCondition
{
    //##################################################
    //# variables
    //##################################################

    private KmList<KmSqlCondition> _values;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlCompositeCondition()
    {
        _values = new KmList<KmSqlCondition>();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmSqlSimpleCondition value(KmSqlColumnIF col)
    {
        return value(null, col);
    }

    public KmSqlSimpleCondition value(String table, KmSqlColumnIF col)
    {
        String s = formatColumn(table, col);
        return add(new KmSqlSimpleCondition(s));
    }

    public KmSqlCompositeCondition and()
    {
        return add(new KmSqlAndCondition());
    }

    public KmSqlCompositeCondition or()
    {
        return add(new KmSqlOrCondition());
    }

    public boolean isEmpty()
    {
        return _values.isEmpty();
    }

    public boolean isNotEmpty()
    {
        return _values.isNotEmpty();
    }

    public int size()
    {
        return _values.size();
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        String delim = SPACE + getJoin() + SPACE;
        KmStringBuilder out = new KmStringBuilder();

        Iterator<KmSqlCondition> i = _values.iterator();
        while ( i.hasNext() )
        {
            KmSqlCondition e = i.next();

            printOn(out, e);

            if ( i.hasNext() )
                out.print(delim);
        }

        return out.toString();
    }

    private void printOn(KmStringBuilder out, KmSqlCondition e)
    {
        boolean parens = requiresParens(e);

        if ( parens )
            out.print(OPEN_PAREN);

        out.print(e);

        if ( parens )
            out.print(CLOSE_PAREN);
    }

    private boolean requiresParens(KmSqlCondition e)
    {
        if ( e instanceof KmSqlCompositeCondition )
            return ((KmSqlCompositeCondition)e).size() > 1;

        return false;
    }

    //##################################################
    //# support
    //##################################################

    private <E extends KmSqlCondition> E add(E e)
    {
        _values.add(e);
        return e;
    }

    protected abstract String getJoin();

}
