package com.kodemore.sql;

import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.utility.Kmu;

/**
 * I represent a simple atomic condition.
 * For example where... [name = 'bob']
 */
public class KmSqlSimpleCondition
    extends KmSqlCondition
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The left-hand value of the expression; typically a column name.
     * E.g.: where [value] = 'hello'
     */
    private String _left;

    /**
     * A preformatted clause that can be used as part of a larger
     * compound condition.
     */
    private String _condition;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlSimpleCondition(String left)
    {
        _left = left;
    }

    //##################################################
    //# null
    //##################################################

    public void isNull()
    {
        set("%s is null", _left);
    }

    public void isNotNull()
    {
        set("%s is not null", _left);
    }

    //##################################################
    //# column
    //##################################################

    public void is(String table, KmSqlColumnIF col)
    {
        String right = formatColumn(table, col);
        set("%s is %s", _left, right);
    }

    public void isNot(String table, KmSqlColumnIF col)
    {
        String right = formatColumn(table, col);
        set("%s is not %s", _left, right);
    }

    //##################################################
    //# string 
    //##################################################

    public void is(String value)
    {
        if ( value == null )
            isNull();
        else
            set("%s is %s", _left, quote(value));
    }

    public void isNot(String value)
    {
        if ( value == null )
            isNotNull();
        else
            set("%s is not %s", _left, quote(value));
    }

    //##################################################
    //# string (like)
    //##################################################

    /**
     * Simple pattern matching.
     * Use % for 0..many characters.
     * Use _ for single character.
     * Use \ to escape wildcards.
     */
    public void like(String value)
    {
        set("%s like %s escape %s", _left, quote(value), quote(LIKE_ESCAPE));
    }

    public void notLike(String value)
    {
        set("%s not like %s escape %s", _left, quote(value), quote(LIKE_ESCAPE));
    }

    public void contains(CharSequence substring)
    {
        String s = LIKE_MANY + escapeLike(substring) + LIKE_MANY;
        like(s);
    }

    public void startsWith(CharSequence prefix)
    {
        String s = escapeLike(prefix) + LIKE_MANY;
        like(s);
    }

    public void endsWith(CharSequence suffix)
    {
        String s = LIKE_MANY + escapeLike(suffix);
        like(s);
    }

    public void isLessThanOrEqualTo(String value)
    {
        set("%s <= %s", _left, quote(value));
    }

    public void isGreaterThanOrEqualTo(String value)
    {
        set("%s >= %s", _left, quote(value));
    }

    //##################################################
    //# integer 
    //##################################################

    public void is(Integer value)
    {
        set("%s is %s", _left, value);
    }

    public void isNot(Integer value)
    {
        set("%s is not %s", _left, value);
    }

    //##################################################
    //# long 
    //##################################################

    public void is(Long value)
    {
        set("%s is %s", _left, value);
    }

    public void isNot(Long value)
    {
        set("%s is not %s", _left, value);
    }

    public void isLessThan(Long value)
    {
        set("%s < %s", _left, value);
    }

    public void isLessThanOrEqualTo(Long value)
    {
        set("%s <= %s", _left, value);
    }

    public void isGreaterThan(Long value)
    {
        set("%s > %s", _left, value);
    }

    public void isGreaterThanOrEqualTo(Long value)
    {
        set("%s >= %s", _left, value);
    }

    //##################################################
    //# double 
    //##################################################

    public void is(Double value)
    {
        set("%s is %s", _left, value);
    }

    public void isNot(Double value)
    {
        set("%s is not %s", _left, value);
    }

    //##################################################
    //# boolean 
    //##################################################

    public void is(Boolean value)
    {
        set("%s is %s", _left, formatBoolean(value));
    }

    public void isNot(Boolean value)
    {
        set("%s is not %s", _left, formatBoolean(value));
    }

    public void isTrue()
    {
        is(true);
    }

    public void isFalse()
    {
        is(false);
    }

    private String formatBoolean(Boolean value)
    {
        if ( value == null )
            return "null";

        return value
            ? "1"
            : "0";
    }

    //##################################################
    //# date 
    //##################################################

    public void is(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        is(ms);
    }

    public void isNot(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        isNot(ms);
    }

    public void isLessThan(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        isLessThan(ms);
    }

    public void isLessThanOrEqualTo(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        isLessThanOrEqualTo(ms);
    }

    public void isGreaterThan(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        isGreaterThan(ms);
    }

    public void isGreaterThanOrEqualTo(KmDate value)
    {
        long ms = KmSqlUtility.dateToMs(value);
        isGreaterThanOrEqualTo(ms);
    }

    //##################################################
    //# timestamp 
    //##################################################

    public void is(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        is(ms);
    }

    public void isNot(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        isNot(ms);
    }

    public void isLessThan(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        isLessThan(ms);
    }

    public void isLessThanOrEqualTo(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        isLessThanOrEqualTo(ms);
    }

    public void isGreaterThan(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        isGreaterThan(ms);
    }

    public void isGreaterThanOrEqualTo(KmTimestamp value)
    {
        long ms = KmSqlUtility.timestampToMs(value);
        isGreaterThanOrEqualTo(ms);
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return _condition;
    }

    //##################################################
    //# support
    //##################################################

    private void set(String msg, Object... args)
    {
        _condition = Kmu.format(msg, args);
    }

    private String quote(char c)
    {
        return KmSqlUtility.quote(c + "");
    }

    private String quote(String s)
    {
        return KmSqlUtility.quote(s);
    }

    @SuppressWarnings("unused")
    private String escape(String s)
    {
        return KmSqlUtility.escape(s);
    }

    @SuppressWarnings("unused")
    private String quoteLike(String s)
    {
        return KmSqlUtility.quoteLike(s);
    }

    private String escapeLike(CharSequence s)
    {
        return KmSqlUtility.escapeLike(s);
    }

}
